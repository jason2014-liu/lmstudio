/**
* TODO
* @Project: lmstudio-common
* @Title: MessageSendServiceTest.java
* @Package com.lmstudio.common.mq
* @author jason
* @Date 2017年6月16日 上午11:53:33
* @Copyright
* @Version 
*/
package com.lmstudio.common.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* TODO
* @ClassName: MessageSendServiceTest
* @author jason
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-mq.xml")   
public class MessageSendServiceTest {

	@Autowired
	private MessageSendService sendService;
	
	@Test
	public void sendTextMessageTest(){
		sendService.sendTextMessage("测试 textMessage");
	}
}
