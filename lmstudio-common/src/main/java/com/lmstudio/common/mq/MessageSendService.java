/**
* TODO
* @Project: lmstudio-common
* @Title: MessageSendService.java
* @Package com.lmstudio.common.mq
* @author jason
* @Date 2017年6月16日 上午11:34:47
* @Copyright
* @Version 
*/
package com.lmstudio.common.mq;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 
 * @ClassName: MessageSendService
 * @author jason
 */
@Component
public class MessageSendService {

	@Resource(name = "jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;

	@Resource(name = "activeMQTestQueue")
	private Destination destination;

	public void sendTextMessage(final String message) {
		
		jmsQueueTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});

	}
}
