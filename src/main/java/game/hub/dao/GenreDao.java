package game.hub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import game.hub.entity.Genre;

/*
 * 🔹 GenreDao
 * ----------------------
 * DAO for Genre entity.
 * Provides CRUD operations like save(), findById(), findAll(), delete(), etc.
 */
public interface GenreDao extends JpaRepository<Genre, Integer> {
    // Add custom queries if needed
}