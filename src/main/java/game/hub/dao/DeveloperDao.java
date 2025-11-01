package game.hub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import game.hub.entity.Developer;

/*
 * 🔹 DeveloperDao
 * ----------------------
 * DAO for Developer entity.
 * Provides CRUD operations like save(), findById(), findAll(), delete(), etc.
 */
public interface DeveloperDao extends JpaRepository<Developer, Integer> {
    // Add custom queries if needed
}