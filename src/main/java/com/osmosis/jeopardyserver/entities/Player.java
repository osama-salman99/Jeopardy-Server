package com.osmosis.jeopardyserver.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Player {
	@Id
	@Setter(AccessLevel.NONE)
	private String id;
	private String nickname;
	@ManyToOne(cascade = CascadeType.ALL)
	private Game currentGame;

	public Player() {

	}

	public Player(String id, String nickname) {
		this.id = id;
		this.nickname = nickname;
		this.currentGame = null;
	}

	public boolean inGame() {
		return currentGame != null;
	}

	public void joinGame(Game game) {
		game.addPlayer(this);
	}

	public void freeGame() {
		setCurrentGame(null);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Player)) {
			return false;
		}
		return this.id.equals(((Player) object).getId());
	}

	@Override
	public String toString() {
		return "Player{" +
				"nickname='" + nickname + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
