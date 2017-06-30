/**
* TODO
* @Project: lmstudio-rpc
* @Title: IAsyncRpcClientProxy.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月30日 下午3:28:30
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

/**
 * TODO 异步代理
 * 
 * @ClassName: IAsyncRpcClientProxy
 * @author jason
 */
public interface IAsyncObjectProxy {

	RpcResult call(String methodName, Object... args);
}
