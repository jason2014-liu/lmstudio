/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcConnection.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月26日 下午5:03:39
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import com.lmstudio.rpc.model.RpcRequest;

/**
* TODO 与rpc server的连接
* @ClassName: RpcConnection
* @author jason
*/
public interface RpcConnection {

	void init();
	
	Object send(RpcRequest request,boolean async);
	
}
