package com.osmosis.jeopardyserver.configurations;

import com.osmosis.jeopardyserver.services.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Scanner;

@Configuration
public class GameConfiguration {
	private final SimpMessagingTemplate websocket;

	public GameConfiguration(SimpMessagingTemplate websocket) {
		this.websocket = websocket;
	}

	@Bean
	public CommandLineRunner initialGame(GameService gameService) {
		return args -> {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Scanner scanner = new Scanner(System.in);
					while (true) {
						if (scanner.nextLine().equals("send")) {
							// send
//							websocket.convertAndSendToUser(, "/topics/test", "hi");
						}
					}
				}
			}).start();
		};
	}
}
