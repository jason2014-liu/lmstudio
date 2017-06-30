/**
* TODO
* @Project: lmstudio-rpc
* @Title: HelloServiceTest.java
* @Package com.lmstudio.rpc.service
* @author jason
* @Date 2017年6月27日 下午4:15:30
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.service;

import org.junit.Test;

import com.lmstudio.rpc.client.RpcClientProxyBuilder;

/**
* TODO
* @ClassName: HelloServiceTest
* @author jason
*/
public class HelloServiceTest {

	@Test
	public void testSayHello(){
		System.out.println("------------开始远程调用测试---------------");
		HelloService helloService = RpcClientProxyBuilder.buildProxy(HelloService.class);
		//String result = helloService.sayHello("中国");
		HelloObj result = helloService.transferObj(new HelloObj("老王","你好"));
		System.out.println(result);
		try {
			Thread.sleep(3600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
