package com.bridgelabz.fundoonotes.config;

//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.response.MailObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Slf4j
@Service
public class RabbitMQSender {
	
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//	
//	@Value("${bridgelabz.rabbitmq.exchange}")
//	private String exchange;
//	
//	@Value("${bridgelabz.rabbitmq.routingkey}")
//	private String routingkey;	
//	String kafkaTopic = "java_in_use_topic";
//	
//	public void send(MailObject message) {
//		amqpTemplate.convertAndSend(exchange, routingkey, message);
//	    log.info("Send msg = " + message);
//	}
}