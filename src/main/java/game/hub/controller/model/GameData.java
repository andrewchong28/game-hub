/*
DTO - Data Transfer Object:
    - Used to transfer Game data between layers.
    - Accepts and returns Game data as JSON.
    - Converts Game entities to DTOs.
*/
package game.hub.controller.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import game.hub.entity.Game;
import game.hub.entity.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameData {

    private Integer gameId;
    private String gameTitle;
    private String gameDescription;
    private Date gameReleaseDate;

    // A game can have multiple genres
    private Set<GenreData> genres = new HashSet<>();

    // Constructor: builds GameData from Game entity
    public GameData(Game game) {
        this(game, true);
    }

    // Overloaded constructor to prevent infinite recursion
    public GameData(Game game, boolean includeGenres) {
        this.gameId = game.getGameId();
        this.gameTitle = game.getGameTitle();
        this.gameDescription = game.getGameDescription();
        this.gameReleaseDate = game.getGameReleaseDate();

        if (includeGenres && game.getGenres() != null) {
            for (Genre genre : game.getGenres()) {
                // call shallow GenreData (no games inside)
                this.genres.add(new GenreData(genre, false));
            }
        }
    }
}