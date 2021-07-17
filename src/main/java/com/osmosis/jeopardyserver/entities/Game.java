package com.osmosis.jeopardyserver.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table
@Getter
@Setter
public class Game {
	@Id
	@Setter(AccessLevel.NONE)
	private String id;
	@OneToMany(mappedBy = "currentGame", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Player> players;
	@OneToOne(cascade = CascadeType.ALL)
	private Player host;

	public Game() {

	}

	public Game(String id, Player host) {
		this.id = id;
		this.host = host;
		this.players = new HashSet<>();
		this.host.setCurrentGame(this);
	}

	public boolean isHost(Player player) {
		return host.equals(player);
	}

	public void addPlayer(Player player) {
		if (!containsPlayerId(player.getId())) {
			players.add(player);
			player.setCurrentGame(this);
		}
	}

	public boolean containsPlayerId(String playerId) {
		return players.stream().anyMatch(player -> playerId.equals(player.getId()));
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		String prefix = "";
		for (Player player : players) {
			stringBuilder.append(prefix);
			prefix = ", ";
			stringBuilder.append('\'').append(player.getNickname()).append('\'');
		}
		stringBuilder.append("}");
		return "Game{" +
				"id='" + id + '\'' +
				", host='" + host.getNickname() + '\'' +
				", players=" + stringBuilder +
				'}';
	}

	@PostLoad
	private void cleanUp() {
		players.remove(players.stream().filter(this::isHost).findFirst().orElse(null));
	}

	public void removePlayer(String id) {
		Player player = getPlayer(id);
		if (player == null) {
			System.out.println("Warning: Could not remove player from game: " +
					"Player with id '" + id + "' is not in game '" + this.id + "'");
			return;
		}
		player.freeGame();
		players.remove(player);
		// TODO: Remove player
	}

	private Player getPlayer(String id) {
		return players.stream().filter(player -> player.getId().equals(id)).findFirst().orElse(null);
	}
}
