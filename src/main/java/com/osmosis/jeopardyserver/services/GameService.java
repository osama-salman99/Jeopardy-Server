package com.osmosis.jeopardyserver.services;

import com.osmosis.jeopardyserver.entities.Board;
import com.osmosis.jeopardyserver.entities.Game;
import com.osmosis.jeopardyserver.entities.Player;
import com.osmosis.jeopardyserver.exceptions.GameIdTakenException;
import com.osmosis.jeopardyserver.exceptions.GameNotFoundException;
import com.osmosis.jeopardyserver.exceptions.InvalidGameIdException;
import com.osmosis.jeopardyserver.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
	private GameRepository repository;
	private PlayerService playerService;

	@Autowired
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	@Autowired
	public void setRepository(GameRepository repository) {
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
			throw new GameIdTakenException("Cannot create game: Another game already exists with the id: " + gameId);
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

	public boolean isHost(String playerId) {
		Player player = playerService.getPlayer(playerId);
		return player != null && player.inGame() && player.getCurrentGame().isHost(player);
	}

	public void setFirstBoard(String gameId, Board board) {
		if (!containsId(gameId)) {
			throw new GameNotFoundException("Cannot set first board: There is no game with the id: " + gameId);
		}
		Game game = getGame(gameId);
		game.setFirstBoard(board);
		save(game);
	}

	public void setSecondBoard(String gameId, Board board) {
		if (!containsId(gameId)) {
			throw new GameNotFoundException("Cannot set second board: There is no game with the id: " + gameId);
		}
		Game game = getGame(gameId);
		game.setSecondBoard(board);
		save(game);
	}
}
