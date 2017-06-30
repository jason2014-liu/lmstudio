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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.lmstudio.rpc.model.RpcRequest;
import com.lmstudio.rpc.model.RpcResponse;

/**
* TODO
* @ClassName: RpcResult
* @author jason
*/
public class RpcResult implements Future<Object>{

	/**
	 * 用来控制待设置返回结果后，才能获取结果
	 */
	Semaphore semaphore = new Semaphore(0);
	
	private RpcRequest request;
	private RpcResponse response;
	
	
	private long startTime;
	
	public RpcResult(RpcRequest request, long startTime) {
		super();
		this.request = request;
		this.startTime = startTime;
	}
	
//	public Object getResult(){
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return response.getResult();
//	}

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
		semaphore.release(Integer.MAX_VALUE-semaphore.availablePermits());
	}


	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		semaphore.acquire();
		
		return response.getResult();
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		if(semaphore.tryAcquire(timeout, unit)){
			return response.getResult();
		}else{
			throw new TimeoutException("获取结果超时");
		}
	}
	
	
}
