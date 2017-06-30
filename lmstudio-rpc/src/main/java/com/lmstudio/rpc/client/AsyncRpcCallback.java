/**
* TODO
* @Project: lmstudio-rpc
* @Title: AsyncRpcCallback.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月30日 下午3:23:02
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

/**
* TODO 异步调用，回调接口，需调用方按业务实现
* @ClassName: AsyncRpcCallback
* @author jason
*/
public interface AsyncRpcCallback {

	void success(Object obj);
	void fail(Exception e);
}
