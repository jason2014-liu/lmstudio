/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcInvocationHandler.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月26日 上午11:12:33
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;

/**
 * TODO
 * 
 * @ClassName: RpcInvocationHandler
 * @author jason
 */
public class RpcInvocationHandler<T> implements InvocationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RpcInvocationHandler.class);
	
	private Class<T> clazz;
	
	private RpcConnection connection;
	/**
	 * 可用连接工场，此处简易用个列表轮询实现
	 */
	private List<RpcConnection> connection_list;
	
	public RpcInvocationHandler(Class<T> clazz) {
		super();
		this.clazz = clazz;
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
		
		return null;
	}

}
