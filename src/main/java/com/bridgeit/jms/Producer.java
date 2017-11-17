package com.bridgeit.jms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	@Autowired
	private JmsTemplate jmsTemplate;

	public void sender() {
		System.out.println("jms template: "+jmsTemplate);
		Map<String, String> message = new HashMap<>();
		message.put("Hello", "World");
		jmsTemplate.convertAndSend(message);
	}

}
