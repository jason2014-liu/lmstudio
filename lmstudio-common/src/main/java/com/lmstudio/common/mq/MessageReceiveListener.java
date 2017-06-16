/**
* TODO
* @Project: lmstudio-common
* @Title: MessageReceiveListener.java
* @Package com.lmstudio.common.mq
* @author jason
* @Date 2017年6月16日 上午11:44:34
* @Copyright
* @Version 
*/
package com.lmstudio.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * TODO
 * 
 * @ClassName: MessageReceiveListener
 * @author jason
 */
public class MessageReceiveListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		if (message instanceof TextMessage) {
			// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
			TextMessage textMsg = (TextMessage) message;
			try {
				System.out.println("收到的消息内容是：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
