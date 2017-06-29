/**
* TODO
* @Project: lmstudio-rpc
* @Title: RpcServerBootstrap.java
* @Package com.lmstudio.rpc.service
* @author jason
* @Date 2017年6月28日 上午9:20:18
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* TODO
* @ClassName: RpcServerBootstrap
* @author jason
*/
public class RpcServerBootstrap {

	/**
	* TODO
	* @Title: main
	* @param args
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:applicationContext-rpc-server.xml"); 

	}

}
