/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcResponse.java
* @Package com.lmstudio.rpc.model
* @author jason
* @Date 2017年6月22日 下午4:28:13
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.model;

import java.io.Serializable;

/**
 * TODO
 * 
 * @ClassName: RpcResponse
 * @author jason
 */
public class RpcResponse implements Serializable {

	/**
	 * serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	// 请求的Id
	private String requestId;
	// 异常
	private String error;
	// 响应
	private Object result;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "rpcResponse [requestId=" + requestId + ", error=" + error + ", result=" + result + "]";
	}
}
