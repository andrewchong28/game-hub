package game.hub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import game.hub.controller.model.DeveloperData;
import game.hub.controller.model.GameData;
import game.hub.controller.model.GenreData;
import game.hub.service.GameHubService;

/*
 * GameHubController
 * ----------------------
 * This class is the REST controller for GameHub.
 * It handles HTTP requests for Developer, Game, and Genre entities.
 * 
 * Responsibilities:
 * - Map endpoints (POST, GET, PUT, DELETE)
 * - Call the Service layer to handle business logic
 * - Return DTOs to the client
 * - Log requests for debugging
 */
@RestController // Marks this class as a REST controller
@RequestMapping("/gamehub") // Base URL for all endpoints
@Slf4j // Lombok annotation to provide a logger named 'log'
public class GameHubController {

    @Autowired
    private GameHubService gameHubService; // Inject the service layer

    // =============================
    // DEVELOPER ENDPOINTS
    // =============================

    /*
     * POST /gamehub/developer
     * -------------------------
     * Accepts JSON body of DeveloperData
     * Calls service to save or update developer
     * Returns saved DeveloperData
     */
    @PostMapping("/developer")
    @ResponseStatus(HttpStatus.CREATED) // Returns HTTP 201 Created
    public DeveloperData saveDeveloper(@RequestBody DeveloperData developerData) {
        log.info("Saving developer: {}", developerData);
        return gameHubService.saveDeveloper(developerData);
    }

    /*
     * PUT /gamehub/developer/{developerId}
     * -------------------------------------
     * Updates an existing developer by ID
     */
    @PutMapping("/developer/{developerId}")
    public DeveloperData updateDeveloper(
            @PathVariable Integer developerId,
            @RequestBody DeveloperData developerData) {
        log.info("Updating developer with ID={}", developerId);
        developerData.setDeveloperId(developerId); // ensure ID is set
        return gameHubService.saveDeveloper(developerData);
    }

    /*
     * GET /gamehub/developer/{developerId}
     * -------------------------------------
     * Retrieves a single developer by ID
     */
    @GetMapping("/developer/{developerId}")
    public DeveloperData getDeveloperById(@PathVariable Integer developerId) {
        log.info("Retrieving developer with ID={}", developerId);
        return gameHubService.getDeveloperById(developerId);
    }

    /*
     * GET /gamehub/developers
     * -------------------------
     * Retrieves a list of all developers
     */
    @GetMapping("/developers")
    public List<DeveloperData> getAllDevelopers() {
        log.info("Retrieving all developers");
        return gameHubService.getAllDevelopers();
    }

    /*
     * DELETE /gamehub/developer/{developerId}
     * ----------------------------------------
     * Deletes a developer by ID
     * Returns a JSON message confirming deletion
     */
    @DeleteMapping("/developer/{developerId}")
    public Map<String, String> deleteDeveloper(@PathVariable Integer developerId) {
        log.info("Deleting developer with ID={}", developerId);
        gameHubService.deleteDeveloperById(developerId);
        return Map.of("message", "Developer with ID=" + developerId + " deleted successfully.");
    }

    // =============================
    // GAME ENDPOINTS
    // =============================

    /*
     * POST /gamehub/game
     * -------------------
     * Save or update a game
     */
    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    public GameData saveGame(@RequestBody GameData gameData) {
        log.info("Saving game: {}", gameData);
        return gameHubService.saveGame(gameData);
    }

    /*
     * GET /gamehub/game/{gameId}
     * ----------------------------
     * Retrieve a single game by ID
     */
    @GetMapping("/game/{gameId}")
    public GameData getGameById(@PathVariable Integer gameId) {
        log.info("Retrieving game with ID={}", gameId);
        return gameHubService.getGameById(gameId);
    }

    /*
     * GET /gamehub/games
     * -------------------
     * Retrieve all games
     */
    @GetMapping("/games")
    public List<GameData> getAllGames() {
        log.info("Retrieving all games");
        return gameHubService.getAllGames();
    }

    /*
     * DELETE /gamehub/game/{gameId}
     * ------------------------------
     * Deletes a game by ID
     * Returns a confirmation message
     */
    @DeleteMapping("/game/{gameId}")
    public Map<String, String> deleteGame(@PathVariable Integer gameId) {
        log.info("Deleting game with ID={}", gameId);
        gameHubService.deleteGameById(gameId);
        return Map.of("message", "Game with ID=" + gameId + " deleted successfully.");
    }

    // =============================
    // GENRE ENDPOINTS
    // =============================

    /*
     * POST /gamehub/genre
     * -------------------
     * Save or update a genre
     */
    @PostMapping("/genre")
    @ResponseStatus(HttpStatus.CREATED)
    public GenreData saveGenre(@RequestBody GenreData genreData) {
        log.info("Saving genre: {}", genreData);
        return gameHubService.saveGenre(genreData);
    }

    /*
     * GET /gamehub/genre/{genreId}
     * ----------------------------
     * Retrieve a genre by ID
     */
    @GetMapping("/genre/{genreId}")
    public GenreData getGenreById(@PathVariable Integer genreId) {
        log.info("Retrieving genre with ID={}", genreId);
        return gameHubService.getGenreById(genreId);
    }

    /*
     * GET /gamehub/genres
     * --------------------
     * Retrieve all genres
     */
    @GetMapping("/genres")
    public List<GenreData> getAllGenres() {
        log.info("Retrieving all genres");
        return gameHubService.getAllGenres();
    }

    /*
     * DELETE /gamehub/genre/{genreId}
     * --------------------------------
     * Deletes a genre by ID
     */
    @DeleteMapping("/genre/{genreId}")
    public Map<String, String> deleteGenre(@PathVariable Integer genreId) {
        log.info("Deleting genre with ID={}", genreId);
        gameHubService.deleteGenreById(genreId);
        return Map.of("message", "Genre with ID=" + genreId + " deleted successfully.");
    }
}