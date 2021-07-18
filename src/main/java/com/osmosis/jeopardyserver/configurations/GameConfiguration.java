package com.osmosis.jeopardyserver.configurations;

import com.osmosis.jeopardyserver.services.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

	@Bean
	public CommandLineRunner initialGame(GameService gameService) {
		return args -> {
			// Load file then add it to database
		};
	}
}
