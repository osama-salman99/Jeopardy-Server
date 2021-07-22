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
@RequestMapping("/game/host")
public class HostController {
	private final PlayerService playerService;
	private final GameService gameService;

	@Autowired
	public HostController(PlayerService playerService, GameService gameService) {
		this.playerService = playerService;
		this.gameService = gameService;
	}

	@GetMapping("/is-host")
	public boolean isHost(HttpServletRequest request) {
		return gameService.isHost(request.getSession().getId());
	}

	@GetMapping("/info")
	public String info(HttpServletRequest request) {
		return playerService.getHostedGameInfo(request.getSession().getId());
	}

	@GetMapping("/is-ready")
	public boolean isReady(HttpServletRequest request) {
		return gameService.isReady(request.getSession().getId());
	}

	@SneakyThrows
	@PostMapping(
			path = "/upload-file",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		gameService.setBoards(request.getSession().getId(), file);
		return "accepted";
	}
}
