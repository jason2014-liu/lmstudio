/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcRequest.java
* @Package com.lmstudio.rpc.model
* @author jason
* @Date 2017年6月22日 下午4:25:23
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO
 * 
 * @ClassName: RpcRequest
 * @author jason
 */
public class RpcRequest implements Serializable {

	/**
	 * serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	// 请求Id
	private String requestId;
	// 远程调用接口名称
	private String interfaceName;
	// 远程调用方法名称
	private String methodName;
	// 参数类型
	private Class<?>[] parameterTypes;
	// 参数值
	private Object[] parameters;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "rpcRequest [requestId=" + requestId + ", interfaceName=" + interfaceName + ", methodName=" + methodName
				+ ", parameterTypes=" + Arrays.toString(parameterTypes) + ", parameters=" + Arrays.toString(parameters)
				+ "]";
	}

}
