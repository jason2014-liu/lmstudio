/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcClientProxy.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月27日 下午2:46:31
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;

/**
 * TODO
 * 
 * @ClassName: RpcClientProxy
 * @author jason
 */
public class RpcClientProxy<T> implements InvocationHandler, IAsyncObjectProxy {

	private static Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

	private Class<T> interfaceClass;

	public RpcClientProxy(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub

		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setInterfaceName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParameters(args);

		logger.debug("客户端发起请求，参数为 interfaceName={},methodName={}", request.getInterfaceName(), request.getMethodName());

		InetSocketAddress node = new InetSocketAddress("127.0.0.1", 18888);

		RpcClientHandler hander = ChannelFactory.getInstance().getClientHandler(node);

		RpcResult result = hander.sendRequest(request);
		return result.get(3000, TimeUnit.MILLISECONDS);
	}

	@Override
	public RpcResult call(String methodName, Object... args) {
		// TODO Auto-generated method stub
		InetSocketAddress node = new InetSocketAddress("127.0.0.1", 18888);
		RpcClientHandler hander = ChannelFactory.getInstance().getClientHandler(node);
		RpcRequest request = createRequest(this.interfaceClass.getName(), methodName, args);
		RpcResult rpcResult = hander.sendRequest(request);
		return rpcResult;
	}

	private RpcRequest createRequest(String interfaceName, String methodName, Object[] args) {
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setInterfaceName(interfaceName);
		request.setMethodName(methodName);
		request.setParameters(args);

		Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = getClassType(args[i]);
		}
		request.setParameterTypes(parameterTypes);
		return request;
	}

	private Class<?> getClassType(Object obj) {
		Class<?> classType = obj.getClass();
		String typeName = classType.getName();
		switch (typeName) {
		case "java.lang.Integer":
			return Integer.TYPE;
		case "java.lang.Long":
			return Long.TYPE;
		case "java.lang.Float":
			return Float.TYPE;
		case "java.lang.Double":
			return Double.TYPE;
		case "java.lang.Character":
			return Character.TYPE;
		case "java.lang.Boolean":
			return Boolean.TYPE;
		case "java.lang.Short":
			return Short.TYPE;
		case "java.lang.Byte":
			return Byte.TYPE;
		}

		return classType;
	}

}
