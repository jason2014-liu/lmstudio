/**
* TODO
* @Project: lmstudio-rpc
* @Title: HelloObj.java
* @Package com.lmstudio.rpc.service
* @author jason
* @Date 2017年6月29日 下午5:45:00
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.service;

/**
* TODO
* @ClassName: HelloObj
* @author jason
*/
public class HelloObj {

	private String name;
	private String echoStr;
	
	
	public HelloObj(String name, String echoStr) {
		super();
		this.name = name;
		this.echoStr = echoStr;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEchoStr() {
		return echoStr;
	}
	public void setEchoStr(String echoStr) {
		this.echoStr = echoStr;
	}
	
	@Override
	public String toString() {
		return "HelloObj [name=" + name + ", echoStr=" + echoStr + "]";
	}
	
	
}
