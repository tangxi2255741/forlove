package com.txr.forlove.common.aop;

import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.txr.forlove.common.utils.JsonUtil;
import com.txr.forlove.common.utils.UUIDUtils;
import com.txr.forlove.domain.rpc.RPCResult;
import org.aopalliance.intercept.Invocation;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RPCComsumerResultFilter extends AbstractFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RPCComsumerResultFilter.class);
    private static final long serialVersionUID = 1L;

//    @Override
//    public ResponseMessage invoke(RequestMessage requestMessage) {
//        Invocation invocationBody = requestMessage.getInvocationBody();
//        boolean hasReset = UUIDUtils.resetAndGetUUID();
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        ResponseMessage invoke = getNext().invoke(requestMessage);
//        stopWatch.stop();
//        if (invoke != null && invoke.getResponse() != null) {
//            Object response = invoke.getResponse();
//            if (response instanceof RPCResult && !((RPCResult) response).isSuccess()) {
//                UUIDUtils.clearUuid(hasReset);
//                if (LOGGER.isInfoEnabled()) {
//                    LOGGER.info("{}#{},cost:{}ms 入参:{} 出参:{}", requestMessage.getClassName(), requestMessage.getMethodName(), stopWatch.getTime(), JsonUtil.toJSONString(invocationBody.getArgs()), JsonUtil.toJSONString(response));
//                }
//                throw new RestrictException(ErrorCode.RPC_ERROR,((RPCResult) response).getErrorMsg());
//            }
//        } else { //理论上invoke不会为null
//            if (invoke !=null)
//            {
//                LOGGER.error(requestMessage.getClassName()+"#"+requestMessage.getMethodName()+", 入参:"+  JsonUtil.toJSONString(invocationBody.getArgs())+"调用失败",invoke);
//                Throwable exception = invoke.getException();
//                if (exception instanceof NoAliveProviderException)
//                {
//                    LOGGER.error("调用失败"+ exception.getMessage());
//                }
//                else
//                {
//                    LOGGER.error("调用失败",exception);
//                }
//            }
//            UUIDUtils.clearUuid(hasReset);
//            throw new RestrictException(ErrorCode.RPC_ERROR,"远程接口调用失败");
//        }
//        UUIDUtils.clearUuid(hasReset);
//        return invoke;
//    }
}
