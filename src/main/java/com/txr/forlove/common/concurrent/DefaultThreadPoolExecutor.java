package com.txr.forlove.common.concurrent;

import com.jd.b2b.restrictsale.cbi.common.logs.B2bLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月23日
 * @since 2018年8月23日
 * 
 */
public class DefaultThreadPoolExecutor extends ThreadPoolExecutor implements ExecutorSerivce, InitializingBean, ApplicationContextAware {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final static int CORE_POOL_SIZE = 30;	// 线程池大小
	private final static int MAX_MUM_POOL_SIZE = 300;	// 最大线程池大小//

	private final static long KEEP_ALIVE_TIME = 60;	// 线程空闲时间

	private final static int QUEUE_SIZE = 3;		// 等待任务大小
	private final static long TIME_OUT = 15;		// 线程池关闭前等待未完成任务的执行时间，如果超时就中断

	public DefaultThreadPoolExecutor() {
		this(CORE_POOL_SIZE, MAX_MUM_POOL_SIZE, KEEP_ALIVE_TIME);

	}

	public DefaultThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime) {
		
		super(corePoolSize, maximumPoolSize, keepAliveTime, 
				TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(QUEUE_SIZE), 
				new DefaultThreadFactory(),
				new CallerRunsPolicy());
		
		logger.info("corePoolSize={}, maximumPoolSize={}, keepAliveTime={}", corePoolSize, maximumPoolSize, keepAliveTime);

	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	@Override
	public void execute(Runnable command) {
		super.execute(new Command(command));
	}

	@Override
	public Future<?> submit(Runnable task) {
		return super.submit(new Command(task));
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return super.submit(new Command(task), result);
	}

	static class Command implements Runnable {
		private final String logId;
		private final Runnable command;

		public Command(Runnable command) {
			super();
			this.command = command;
			this.logId = B2bLogger.getLogId();
		}

		@Override
		public void run() {
			B2bLogger.newLogId(logId);
			command.run();
		}
 
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {

	}

	@Override
	protected void terminated() {
		super.terminated();
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {

	}

	public void destroy() {
		logger.warn("尝试关闭异步处理线程池!");
		this.shutdown();

		try {
			boolean flag = this.awaitTermination(TIME_OUT, TimeUnit.SECONDS);
			if (!flag) {
				logger.error("异步处理线程池关闭完成,等待线程池结束超时！");

				final int size = this.getQueue().size();

				// 不准确，若检查到丢失报警
				if (size > 0) {
					logger.error("异步处理线程池关闭完成,等待线程池结束超时,请求可能丢失[size = " + size + "]!");
					// UmpAlarm.logLossAlarm("SDK线程池关闭异常，等待线程池完成超时，日志信息丢失[size = " +size + "]!");
				}
			}
		} catch (InterruptedException e) {
			logger.error("异步处理线程池关闭时被中断!", e);
			this.shutdownNow();
			Thread.currentThread().interrupt();

		}

		logger.warn("异步处理线程池关闭完成!");
	}

	static class DefaultThreadFactory implements ThreadFactory {
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		DefaultThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "bware-lib-syn";
		}
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}

			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}

			return t;
		}
	}

}
