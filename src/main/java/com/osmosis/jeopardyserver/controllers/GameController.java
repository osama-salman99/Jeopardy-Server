package com.osmosis.jeopardyserver.controllers;

import com.osmosis.jeopardyserver.exceptions.AccessorException;
import com.osmosis.jeopardyserver.services.GameService;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/game")
public class GameController {
	private final PlayerService playerService;
	private final GameService gameService;
	private final SimpMessagingTemplate websocket;

	public GameController(PlayerService playerService, GameService gameService, SimpMessagingTemplate websocket) {
		this.playerService = playerService;
		this.gameService = gameService;
		this.websocket = websocket;
	}

	@MessageMapping("/game/event")
	public void event(SimpMessageHeaderAccessor accessor, String event) {
		Principal user = accessor.getUser();
		if (user == null) {
			throw new AccessorException("Accessor does not contain a user");
		}
		String nickname = user.getName();
		System.out.println("Received '" + event + "' from " + nickname);
		playerService.perform(playerService.getPlayerId(nickname), event);
	}
}
