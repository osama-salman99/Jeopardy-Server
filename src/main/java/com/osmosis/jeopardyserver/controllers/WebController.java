package com.osmosis.jeopardyserver.controllers;

import com.osmosis.jeopardyserver.entities.Player;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:3000", "http://94.249.55.31:3000/"}, allowedHeaders = "*", allowCredentials = "true")
public class WebController {
	private final PlayerService playerService;

	@Autowired
	public WebController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping("/route")
	public String route(HttpServletRequest request) {
		String id = request.getSession().getId();
		if (!playerService.containsId(id)) {
			return "sign-up";
		}
		Player player = playerService.getPlayer(id);
		if (player.inGame()) {
			return "game";
		}
		return "lobby";
	}

	@GetMapping("/player-in-sign-up")
	public boolean inSignUp(HttpServletRequest request) {
		return route(request).equals("sign-up");
	}

	@GetMapping("/player-in-lobby")
	public boolean inLobby(HttpServletRequest request) {
		return route(request).equals("lobby");
	}

	@GetMapping("/player-in-game")
	public boolean inGame(HttpServletRequest request) {
		return route(request).equals("game");
	}

	@PostMapping("/sign-up")
	public String signUp(@RequestParam("nickname") String nickname, HttpServletRequest request) {
		String id = request.getSession().getId();
		playerService.signUp(id, nickname);
		return "accepted";
	}

	@DeleteMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		String id = request.getSession().getId();
		playerService.logOut(id);
		return "successful";
	}

	@PostMapping("create-game")
	public String createGame(@RequestParam("game-id") String gameId, HttpServletRequest request) {
		String playerId = request.getSession().getId();
		playerService.createGame(playerId, gameId);
		return "successful";
	}

	@PostMapping("join-game")
	public String joinGame(@RequestParam("game-id") String gameId, HttpServletRequest request) {
		String playerId = request.getSession().getId();
		playerService.joinGame(playerId, gameId);
		return "successful";
	}
}
