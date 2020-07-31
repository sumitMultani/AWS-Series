package com.example.simplenotifiacationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
public class SNSController {
	
	@Autowired
	private AmazonSNSClient snsClient;

	@Value("${cloud.aws.topic.arn}")
	private String topic;
	
	
	@GetMapping("/subscriber/{email}")
	public String addSubscriber(@PathVariable String email) {
		SubscribeRequest request = new SubscribeRequest(topic, "email", email);
		snsClient.subscribe(request);
		return "Subscription request sent to your email : " + email;
	}
	
	@GetMapping("/publishMessage")
	public String publishMessage() {
		PublishRequest publishRequest = new PublishRequest(topic, "Stay Home \n Stay Safe \n Save Lives",
				"C-19 Warning");
		snsClient.publish(publishRequest);
		return "message was published.";
	}

}
