/**
* TODO
* @Project: lmstudio-rpc
* @Title: HelloService.java
* @Package com.lmstudio.rpc.service
* @author jason
* @Date 2017年6月27日 下午4:12:30
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.service;

/**
* TODO
* @ClassName: HelloService
* @author jason
*/
public interface HelloService {

	String sayHello(String name);
	
	HelloObj transferObj(HelloObj obj);
}
