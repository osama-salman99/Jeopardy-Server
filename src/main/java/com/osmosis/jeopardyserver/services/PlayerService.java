package com.osmosis.jeopardyserver.services;

import com.osmosis.jeopardyserver.entities.Game;
import com.osmosis.jeopardyserver.entities.Player;
import com.osmosis.jeopardyserver.exceptions.*;
import com.osmosis.jeopardyserver.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
	private PlayerRepository repository;
	private GameService gameService;

	@Autowired
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	@Autowired
	public void setRepository(PlayerRepository repository) {
		this.repository = repository;
	}

	public boolean containsNickname(String nickname) {
		return !repository.findPlayerByNickname(nickname).isEmpty();
	}

	public boolean containsId(String id) {
		return repository.findById(id).isPresent();
	}

	public void signUp(String id, String nickname) {
		if (containsId(id)) {
			throw new PlayerAlreadySignedUpException("Player on this browser is already signed up." +
					"\nPlease sign out from the lobby or game section");
		}
		if (nickname.isEmpty()) {
			throw new IllegalNicknameException("Nickname cannot be empty");
		}
		if (containsNickname(nickname)) {
			throw new IllegalNicknameException("Nickname is already taken by another player");
		}
		Player player = new Player(id, nickname);
		repository.save(player);
		System.out.println("Signed up new player: " + player);
	}

	public Player getPlayer(String id) {
		return repository.findById(id).orElse(null);
	}

	public List<Player> getAllPlayers() {
		return repository.findAll();
	}

	public void logOut(String id) {
		// TODO: Do all the logic required
		if (!containsId(id)) {
			throw new PlayerNotRegisteredException("Cannot log out: Player is not registered");
		}
		Player player = getPlayer(id);
		if (player.inGame()) {
			player.getCurrentGame().removePlayer(id);
		}
		repository.delete(player);
		System.out.println("Logged out player: " + player);
	}

	public void joinGame(String playerId, String gameId) {
		if (!containsId(playerId)) {
			throw new PlayerNotRegisteredException("Cannot join game: Player is not registered");
		}
		Player player = getPlayer(playerId);
		if (player.inGame()) {
			throw new PlayerAlreadyInGameException("Cannot join game: Player is already in a game");
		}
		if (!gameService.containsId(gameId)) {
			throw new GameNotFoundException("Cannot join game: There is no game with the id: " + gameId);
		}
		Game game = gameService.getGame(gameId);
		player.joinGame(game);
	}

	public void createGame(String playerId, String gameId) {
		if (!containsId(playerId)) {
			throw new PlayerNotRegisteredException("Cannot create game: Player is not registered");
		}
		Player player = getPlayer(playerId);
		if (player.inGame()) {
			throw new PlayerAlreadyInGameException("Cannot create game: Player is already in a game");
		}
		gameService.createGame(gameId, player);
	}
}
