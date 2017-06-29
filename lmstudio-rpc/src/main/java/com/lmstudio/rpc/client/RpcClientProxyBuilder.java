/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcClientProxyBuilder.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月27日 下午2:41:32
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import java.lang.reflect.Proxy;

/**
* TODO
* @ClassName: RpcClientProxyBuilder
* @author jason
*/
public class RpcClientProxyBuilder {

	
	public static <T> T buildProxy(Class<T> interfaceClass){
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new RpcClientProxy(interfaceClass));
	}
	
	
}
