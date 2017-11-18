package com.bridgeit.jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.emailUtility.EmailUtility;
import com.bridgeit.entity.Email;

public class Consumer implements MessageListener {

	@Autowired
	EmailUtility emailUtility;

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		System.out.println("Message is: " + message.toString());
		Email email = new Email();
		try {
			email = (Email) objectMessage.getObject();
			EmailUtility.sendMail(email.getTo(), email.getSubject(), email.getMessage());
		} catch (JMSException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
