package game.hub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import game.hub.entity.Game;

/*
 * 🔹 GameDao
 * ----------------------
 * DAO for Game entity.
 * Provides CRUD operations like save(), findById(), findAll(), delete(), etc.
 */
public interface GameDao extends JpaRepository<Game, Integer> {
    // Add custom queries if needed
}
