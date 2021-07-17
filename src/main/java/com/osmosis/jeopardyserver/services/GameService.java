package com.osmosis.jeopardyserver.services;

import com.osmosis.jeopardyserver.entities.Game;
import com.osmosis.jeopardyserver.entities.Player;
import com.osmosis.jeopardyserver.exceptions.GameNotFoundException;
import com.osmosis.jeopardyserver.exceptions.InvalidGameIdException;
import com.osmosis.jeopardyserver.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
	private final GameRepository repository;

	@Autowired
	public GameService(GameRepository repository) {
		this.repository = repository;
	}

	public boolean containsId(String id) {
		return repository.findById(id).isPresent();
	}

	public Game getGame(String id) {
		return repository.findById(id).orElse(null);
	}

	public void save(Game game) {
		repository.save(game);
	}

	public List<Game> getAllGames() {
		return repository.findAll();
	}

	public void createGame(String gameId, Player host) {
		if (containsId(gameId)) {
			throw new GameNotFoundException("Cannot create game: Another game already exists with the id: " + gameId);
		}
		if (!validGameId(gameId)) {
			throw new InvalidGameIdException("Cannot create game: Invalid game id");
		}
		Game game = new Game(gameId, host);
		save(game);
		System.out.println("Created new game: " + game);
	}

	private boolean validGameId(String gameId) {
		return gameId.matches("^[a-zA-Z0-9]*$");
	}
}
