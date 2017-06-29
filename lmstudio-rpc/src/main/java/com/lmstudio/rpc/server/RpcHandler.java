/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcHandler.java
* @Package com.lmstudio.rpc.serialize
* @author jason
* @Date 2017年6月23日 下午2:47:22
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.server;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.model.RpcResponse;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * TODO
 * 
 * @ClassName: RpcHandler
 * @author jason
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

	private static Logger logger = LoggerFactory.getLogger(RpcHandler.class);
	private final Map<String, Object> handlerMap;

	public RpcHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, final RpcRequest request) throws Exception {
		// TODO Auto-generated method stub

		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		try {
			Object result = handle(request);
			response.setResult(result);
		} catch (Throwable t) {
			response.setError(t.toString());
			logger.error("RPC Server handle request error", t);
		}
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				logger.debug("Send response for request " + request.getRequestId());
			}
		});
	}

	private Object handle(RpcRequest request) throws Throwable {
		String className = request.getInterfaceName();
		Object serviceBean = handlerMap.get(className);

		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();

		logger.debug("服务端接收到的请求参数：interfaceName={},methodName={}", className, methodName);

		// JDK reflect
		Method method = serviceClass.getMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return method.invoke(serviceBean, parameters);

		// Cglib reflect
		// FastClass serviceFastClass = FastClass.create(serviceClass);
		// FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName,
		// parameterTypes);
		// return serviceFastMethod.invoke(serviceBean, parameters);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("server caught exception", cause);
		ctx.close();
	}

}
