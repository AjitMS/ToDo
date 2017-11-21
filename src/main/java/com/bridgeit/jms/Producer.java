package com.bridgeit.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.bridgeit.entity.Email;

@Component
public class Producer {
	@Autowired
	private JmsTemplate jmsTemplate;

	public void sender(Email email) {
		System.out.println("jms template: " + jmsTemplate);
		jmsTemplate.send(new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createObjectMessage(email);
				return message;
			}
		});
	}

}
