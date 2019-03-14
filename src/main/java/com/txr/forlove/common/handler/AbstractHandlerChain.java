package com.txr.forlove.common.handler;

import com.google.common.collect.Sets;
import com.jd.b2b.restrictsale.cbi.common.concurrent.ExecutorSerivce;
import com.jd.b2b.restrictsale.exception.AppException;
import com.jd.b2b.restrictsale.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.2, 2018年8月27日; 增加并行执行调度机制
 * @version 1.0.1, 2017年10月16日
 * @since 2017年10月16日
 * 
 * @param <T>
 * @param <C>
 */
public abstract class AbstractHandlerChain<T, C extends Context, H extends Handler<T, C>> implements HandlerChain<T>, ConcurrentableChain {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final Object object = new Object();

	protected final C context;

	private ExecutorSerivce executorSerivce;
	private Set<Future<Object>> futureSet = Sets.newConcurrentHashSet();
	@Override
	public Set<Future<Object>> getFutureSet() {
		return futureSet;
	}
	@Override
	public ExecutorSerivce getExecutorService() {
		return executorSerivce;
	}

	public AbstractHandlerChain(C context) {
		this.context = context;
	}

	@Override
	public final void doHandler(T t) throws AppException {
		Handler<T, C> next = getNext(t);
		if (next != null) {
			logger.info("Handler[{}]>>>", next.getClass().getName());

			if (fork(next)) {
				logger.info("Handler.fork[{}]>>>", next.getClass().getName());

				doFork(t, next);
				doHandler(t);
			} else if (join(next)) {
				logger.info("Before join>>>>>>>>>");
				doJoin();
				logger.info("After join<<<<<<<<<");
				next.handle(t, context, this);
			} else {
				next.handle(t, context, this);
			}

			logger.info("Handler[{}]<<<", next.getClass().getName());
		} else {

			logger.info("do last join>>>>>>");
			doJoin();
			logger.info("do last join<<<<<<");
		}
	}

	private void doFork(T t, Handler<T, C> next) {
		getFutureSet().add(getExecutorService().submit(new SynTask(next, t, context, new SynChain(next, this)), object));
	}

	private void doJoin() throws AppException {
		logger.info("do join>>>>>>>");
		Set<Future<Object>> futureSet = getFutureSet();
		if (!CollectionUtils.isEmpty(futureSet)) {
			AppException first = null;
			for (Future<Object> i : futureSet) {
				try {
					i.get();
				} catch (InterruptedException e) {
					logger.error("异步任务处理失败!-InterruptedException", e);
				} catch (ExecutionException e) {
					logger.error("异步任务处理失败!-ExecutionException", e);

					Throwable t = null;
					if (first == null && (t = e.getCause()) != null) {
						if (t instanceof AppException) {
							first = (AppException) t;
						} else {
							first = new SystemException("异步执行异常", t);
						}
					}

				}
			}

			futureSet.clear();

			if (first != null) {
				throw first;
			}
		}
		logger.info("do join<<<<<<");
	}

	protected abstract H getNext(T t);

	protected boolean fork(Handler<T, C> handler) {
		return getExecutorService() != null && handler instanceof Forkable;
	}

	protected boolean join(Handler<T, C> handler) {
		return getExecutorService() != null && handler instanceof Joinable;
	}

	public void setExecutorSerivce(ExecutorSerivce executorSerivce) {
		this.executorSerivce = executorSerivce;
	}

	class SynChain implements HandlerChain<T>, ConcurrentableChain {
		private final Handler<T, C> next;
		private final AbstractHandlerChain<T, C, H> chain;

		public SynChain(Handler<T, C> next, AbstractHandlerChain<T, C, H> chain) {
			super();
			this.next = next;
			this.chain = chain;
		}

		@Override
		public void doHandler(T t) throws AppException {
			logger.info("Handler.fork[{}]<<<", next.getClass().getName());
		}

		@Override
		public Set<Future<Object>> getFutureSet() {
			return chain.getFutureSet();
		}

		@Override
		public ExecutorSerivce getExecutorService() {
			return chain.getExecutorService();
		}

	}

	class SynTask implements Runnable {
		private final Handler<T, C> next;
		private final T t;
		private final C context;
		private final HandlerChain<T> chain;

		public SynTask(Handler<T, C> next, T t, C context, HandlerChain<T> chain) {
			this.next = next;
			this.t = t;
			this.context = context;
			this.chain = chain;
		}

		@Override
		public void run() {
			next.handle(t, context, chain);
		}

	}
}
