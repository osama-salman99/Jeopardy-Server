package com.osmosis.jeopardyserver.controllers;

import com.osmosis.jeopardyserver.services.GameService;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
	private final PlayerService playerService;
	private final GameService gameService;

	@Autowired
	public GameController(PlayerService playerService, GameService gameService) {
		this.playerService = playerService;
		this.gameService = gameService;
	}
}
