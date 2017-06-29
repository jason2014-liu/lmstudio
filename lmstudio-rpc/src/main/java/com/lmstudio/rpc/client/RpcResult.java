/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcResult.java
* @Package com.lmstudio.rpc.client
* @author jason
* @Date 2017年6月29日 下午3:42:57
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.client;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.model.RpcResponse;

/**
* TODO
* @ClassName: RpcResult
* @author jason
*/
public class RpcResult {

	private RpcRequest request;
	private RpcResponse response;
	
	private long startTime;
	
	public RpcResult(RpcRequest request, long startTime) {
		super();
		this.request = request;
		this.startTime = startTime;
	}
	
	public Object getResult(){
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getResult();
	}

	public RpcRequest getRequest() {
		return request;
	}

	public void setRequest(RpcRequest request) {
		this.request = request;
	}

	public RpcResponse getResponse() {
		return response;
	}

	public void setResponse(RpcResponse response) {
		this.response = response;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
}
