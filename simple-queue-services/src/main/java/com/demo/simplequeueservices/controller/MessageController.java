package com.demo.simplequeueservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.simplequeueservices.config.SqsConfig;

@RestController
public class MessageController {

	Logger logger = LoggerFactory.getLogger(SqsConfig.class);
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@GetMapping("/producer/{message}")
	public void producer(@PathVariable String message) {
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
	}
	
	@SqsListener("demo-queue")
	public void consumer(String message) {
		logger.info("message from sqs queue : {} ", message);
	}
	
}
