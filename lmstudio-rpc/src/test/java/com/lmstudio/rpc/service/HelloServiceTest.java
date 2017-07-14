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

import com.lmstudio.rpc.client.AsyncRpcCallback;
import com.lmstudio.rpc.client.IAsyncObjectProxy;
import com.lmstudio.rpc.client.RpcClientProxyBuilder;
import com.lmstudio.rpc.client.RpcResult;

/**
* TODO
* @ClassName: HelloServiceTest
* @author jason
*/
public class HelloServiceTest {

	@Test
	public void testSayHello(){
		System.out.println("------------开始远程调用测试---------------");
//		HelloService helloService = RpcClientProxyBuilder.buildProxy(HelloService.class);
		//String result = helloService.sayHello("中国");
//		HelloObj result = helloService.transferObj(new HelloObj("老王","你好"));
//		System.out.println(result);
		
		
		IAsyncObjectProxy proxy = RpcClientProxyBuilder.buildAsyncProxy(HelloService.class);
		
		RpcResult rpcResult = proxy.call("transferObj",new HelloObj("老王","你好"));
		rpcResult.addCallback(new AsyncRpcCallback() {
			
			@Override
			public void success(Object obj) {
				// TODO Auto-generated method stub
				System.out.println("异步调用成功，"+obj);
			}
			
			@Override
			public void fail(Exception e) {
				// TODO Auto-generated method stub
				System.out.println("异步调用失败，"+e.getMessage());
			}
		});
		
		
		try {
			Thread.sleep(3600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
