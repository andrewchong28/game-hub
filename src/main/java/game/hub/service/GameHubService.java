/*
 * Service:


 * Calls DAO
 * - fetches entities
 * - handles business logic
 * - converts entities to DTOs(Data Transfer OBjects)
 */
package game.hub.service;

import java.util.HashSet;
import java.util.List;

import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import game.hub.controller.model.DeveloperData;
import game.hub.controller.model.GameData;
import game.hub.controller.model.GenreData;
import game.hub.dao.DeveloperDao;
import game.hub.dao.GameDao;
import game.hub.dao.GenreDao;
import game.hub.entity.Developer;
import game.hub.entity.Game;
import game.hub.entity.Genre;

@Service
public class GameHubService {

    @Autowired
    private DeveloperDao developerDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GenreDao genreDao;

    // =============================
    // DEVELOPER METHODS
    // =============================

    // Save or update a developer
    @Transactional
    //when saveDeveloper() is called with no developerID then helper method findOrCreateDeveloper creates new developer. Called with a developerId then update.
    public DeveloperData saveDeveloper(DeveloperData developerData) {
        Developer developer = findOrCreateDeveloper(developerData.getDeveloperId());
        copyDeveloperFields(developer, developerData);
        Developer savedDeveloper = developerDao.save(developer);
        return new DeveloperData(savedDeveloper);
    }

    // Retrieve single developer by ID
    @Transactional(readOnly = true)
    public DeveloperData getDeveloperById(Integer developerId) {
        Developer developer = developerDao.findById(developerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Developer with ID=" + developerId + " was not found."
                ));
        return new DeveloperData(developer);
    }

    // Retrieve all developers
    @Transactional(readOnly = true)
    public List<DeveloperData> getAllDevelopers() {
        return developerDao.findAll().stream()
                .map(DeveloperData::new)
                .toList();
    }

    // Helper: find existing or create new developer
    private Developer findOrCreateDeveloper(Integer developerId) {
        if (developerId == null) {
            return new Developer();
        }
        return developerDao.findById(developerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Developer with ID=" + developerId + " was not found."
                ));
    }

    // Copy fields from DTO to entity
    private void copyDeveloperFields(Developer developer, DeveloperData developerData) {
        developer.setDeveloperName(developerData.getDeveloperName());
        developer.setDeveloperCountry(developerData.getDeveloperCountry());
        // games and genres are handled separately if needed
    }

    // =============================
    // GAME METHODS
    // =============================

    @Transactional
    public GameData saveGame(GameData gameData) {
        Game game = findOrCreateGame(gameData.getGameId());
        copyGameFields(game, gameData);
        Game savedGame = gameDao.save(game);
        return new GameData(savedGame);
    }

    @Transactional(readOnly = true)
    public GameData getGameById(Integer gameId) {
        Game game = gameDao.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Game with ID=" + gameId + " was not found."
                ));
        return new GameData(game);
    }

    @Transactional(readOnly = true)
    public List<GameData> getAllGames() {
        return gameDao.findAll().stream()
                .map(GameData::new)
                .toList();
    }

    @Transactional
    public void deleteGameById(Integer gameId) {
        Game game = gameDao.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Game with ID=" + gameId + " was not found."
                ));
        gameDao.delete(game);
    }

    private Game findOrCreateGame(Integer gameId) {
        if (gameId == null) return new Game();
        return gameDao.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Game with ID=" + gameId + " was not found."
                ));
    }

    private void copyGameFields(Game game, GameData gameData) {
        game.setGameTitle(gameData.getGameTitle());
        game.setGameDescription(gameData.getGameDescription());
        game.setGameReleaseDate(gameData.getGameReleaseDate());
        
        // Handle Many-to-Many relationship between Game and Genre
        if (gameData.getGenres() != null) {
            Set<Genre> genres = new HashSet<>();
            for (GenreData g : gameData.getGenres()) {
                Genre genre = genreDao.findById(g.getGenreId())
                        .orElseThrow(() -> new NoSuchElementException("Genre with ID=" + g.getGenreId() + " not found"));
                genres.add(genre);
            }
            game.setGenres(genres);
        }
    }
    

    // =============================
    // GENRE METHODS
    // =============================

    @Transactional
    public GenreData saveGenre(GenreData genreData) {
        Genre genre = findOrCreateGenre(genreData.getGenreId());
        copyGenreFields(genre, genreData);
        Genre savedGenre = genreDao.save(genre);
        return new GenreData(savedGenre);
    }

    @Transactional(readOnly = true)
    public GenreData getGenreById(Integer genreId) {
        Genre genre = genreDao.findById(genreId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Genre with ID=" + genreId + " was not found."
                ));
        return new GenreData(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreData> getAllGenres() {
        return genreDao.findAll().stream()
                .map(GenreData::new)
                .toList();
    }

    @Transactional
    public void deleteGenreById(Integer genreId) {
        Genre genre = genreDao.findById(genreId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Genre with ID=" + genreId + " was not found."
                ));
        genreDao.delete(genre);
    }

    private Genre findOrCreateGenre(Integer genreId) {
        if (genreId == null) return new Genre();
        return genreDao.findById(genreId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Genre with ID=" + genreId + " was not found."
                ));
    }

    private void copyGenreFields(Genre genre, GenreData genreData) {
        genre.setGenreName(genreData.getGenreName());
        // games can be handled here if needed
    }
    /*
     * Deletes a Developer by ID
     * -------------------------
     * - Calls the DeveloperDao to fetch the entity by ID
     * - Throws NoSuchElementException if not found
     * - Deletes the developer from the database
     * - Cascading will handle any associated Games if cascade is configured
     */
    @Transactional
    public void deleteDeveloperById(Integer developerId) {
        Developer developer = developerDao.findById(developerId)
            .orElseThrow(() -> new NoSuchElementException(
                "Developer with ID=" + developerId + " was not found."
            ));
        developerDao.delete(developer);
    }
}