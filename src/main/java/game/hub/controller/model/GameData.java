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
    private Set<GenreData> genres;

    // Constructor: builds GameData from Game entity
    public GameData(Game game) {
        this.gameId = game.getGameId();
        this.gameTitle = game.getGameTitle();
        this.gameDescription = game.getGameDescription();
        this.gameReleaseDate = game.getGameReleaseDate();

        this.genres = new HashSet<>();

        if (game.getGenres() != null) {
            for (Genre genre : game.getGenres()) {
                this.genres.add(new GenreData(genre));
            }
        }
    }
}