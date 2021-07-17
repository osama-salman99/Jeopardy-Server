package com.osmosis.jeopardyserver.repositories;

import com.osmosis.jeopardyserver.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {
	List<Player> findPlayerByNickname(String nickname);
}
