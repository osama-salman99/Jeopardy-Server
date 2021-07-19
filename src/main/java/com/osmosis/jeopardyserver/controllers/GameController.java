package com.osmosis.jeopardyserver.controllers;

import com.osmosis.jeopardyserver.services.GameService;
import com.osmosis.jeopardyserver.services.PlayerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@GetMapping("host/is-host")
	public boolean isHost(HttpServletRequest request) {
		return gameService.isHost(request.getSession().getId());
	}

	@GetMapping("/host/info")
	public String info(HttpServletRequest request) {
		return playerService.getHostedGameInfo(request.getSession().getId());
	}

	@GetMapping("/host/is-ready")
	public boolean isReady(HttpServletRequest request) {
		return gameService.isReady(request.getSession().getId());
	}

	@SneakyThrows
	@PostMapping(
			path = "/host/upload-file",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		gameService.setBoards(request.getSession().getId(), file);
		return "accepted";
	}
}
