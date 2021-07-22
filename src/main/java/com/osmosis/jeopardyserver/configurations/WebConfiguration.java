package com.osmosis.jeopardyserver.configurations;

import com.osmosis.jeopardyserver.entities.Game;
import com.osmosis.jeopardyserver.entities.Player;
import com.osmosis.jeopardyserver.services.GameService;
import com.osmosis.jeopardyserver.services.PlayerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration {

	@Bean
	public CommandLineRunner initialWeb(PlayerService playerService, GameService gameService) {
		return args -> {
			System.out.println("{");

			List<Player> players = playerService.getAllPlayers();
			System.out.println("Players:");
			if (!players.isEmpty()) {
				for (Player player : players) {
					System.out.println(player);
				}
			} else {
				System.out.println("There are currently no players registered");
			}

			List<Game> games = gameService.getAllGames();
			System.out.println("Games:");
			if (!games.isEmpty()) {
				for (Game game : games) {
					System.out.println(game);
				}
			} else {
				System.out.println("There are currently no ongoing games");
			}

			System.out.println("}");
		};
	}
}
