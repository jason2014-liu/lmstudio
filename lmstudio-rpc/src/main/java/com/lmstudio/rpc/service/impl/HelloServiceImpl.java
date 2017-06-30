/**
* TODO
* @Project: lmstudio-rpc
* @Title: HelloServiceImpl.java
* @Package com.lmstudio.rpc.service.impl
* @author jason
* @Date 2017年6月27日 下午4:13:46
* @Copyright
* @Version 
*/
package com.lmstudio.rpc.service.impl;

import com.lmstudio.rpc.server.RpcService;
import com.lmstudio.rpc.service.HelloObj;
import com.lmstudio.rpc.service.HelloService;

/**
* TODO
* @ClassName: HelloServiceImpl
* @author jason
*/
@RpcService(value=HelloService.class)
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "你好，"+name;
	}

	@Override
	public HelloObj transferObj(HelloObj obj) {
		// TODO Auto-generated method stub
		obj.setName(obj.getName()+"response");
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return obj;
	}

}
