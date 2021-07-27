package com.osmosis.jeopardyserver.repositories;

import com.osmosis.jeopardyserver.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {
	Optional<Player> findPlayerByNickname(String nickname);
}
