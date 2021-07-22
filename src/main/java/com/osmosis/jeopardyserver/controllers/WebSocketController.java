package com.osmosis.jeopardyserver.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

	@MessageMapping("/sub")
	@SendTo("/topic/test")
	public String subscribe() {
		return "subscribed";
	}
}
