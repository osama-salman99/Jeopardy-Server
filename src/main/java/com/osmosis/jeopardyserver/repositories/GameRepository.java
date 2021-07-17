package com.osmosis.jeopardyserver.repositories;

import com.osmosis.jeopardyserver.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
