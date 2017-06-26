/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcClientTemplate.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月26日 下午5:44:36
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmstudio.rpc.model.RpcRequest;

/**
* TODO
* @ClassName: RpcClientTemplate
* @author jason
*/
public class RpcClientTemplate<T> implements InvocationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RpcClientTemplate.class);
	
	/**
	 * 默认同步调用
	 */
	private static Boolean DEFAULT_IS_ASYNC = Boolean.FALSE;
	
	/**
	 * 默认调用超时时间
	 */
	private static long DEFAULT_TIMEOUT = 3000L;
			
	
	/**
	 * 代理的接口
	 */
	private Class<T> interfaceClass;
	
	/**
	 * 是否异步调用
	 */
	private Boolean async = DEFAULT_IS_ASYNC;
	
	/**
	 * 调用超时时间
	 */
	private long timeout = DEFAULT_TIMEOUT;
	
	
	public RpcClientTemplate(Class<T> interfaceClass) {
		super();
		this.interfaceClass = interfaceClass;
	}

	public RpcClientTemplate(Class<T> interfaceClass, Boolean async, long timeout) {
		super();
		this.interfaceClass = interfaceClass;
		this.async = async;
		this.timeout = timeout;
	}
	
	

	/**
	 * 暂且用连接列表，后期改为连接工场
	 */
	private List<RpcConnection> connectionList;
	
	@SuppressWarnings("unchecked")
	public <T> T proxy(Class<T> interfaceClass) throws Throwable {
		if (!interfaceClass.isInterface()) {
			throw new IllegalArgumentException(interfaceClass.getName()
					+ " is not an interface");
		}
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, this);
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
	
	/**
	* TODO 获取连接
	* @Title: getConnection
	* @return
	 */
	private RpcConnection getConnection(){
		return new RpcNettyConnection();
	}
	
	
	
	

}
