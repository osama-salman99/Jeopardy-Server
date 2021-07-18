package com.osmosis.jeopardyserver.controllers;

import com.osmosis.jeopardyserver.services.GameService;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://94.249.55.31:3000/"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/game")
public class GameController {
	private final PlayerService playerService;
	private final GameService gameService;

	@Autowired
	public GameController(PlayerService playerService, GameService gameService) {
		this.playerService = playerService;
		this.gameService = gameService;
	}

	@GetMapping("/is-host")
	public boolean isHost(HttpServletRequest request) {
		return gameService.isHost(request.getSession().getId());
	}

	@GetMapping("/host/info")
	public String info(HttpServletRequest request) {
		return playerService.getHostedGameInfo(request.getSession().getId());
	}
}
