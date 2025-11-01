/*
DTO - Data Transfer Object:

    - Used to transfer Genre data between layers.
    - Accepts and returns Genre data as JSON.
    - Converts Genre entities to DTOs.
*/
package game.hub.controller.model;

import java.util.HashSet;

import java.util.Set;

import game.hub.entity.Genre;
import game.hub.entity.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreData {

    private Integer genreId;
    private String genreName;

    // A genre can have multiple games
    private Set<GameData> games;

    // Constructor: builds GenreData from Genre entity
    public GenreData(Genre genre) {
        this.genreId = genre.getGenreId();
        this.genreName = genre.getGenreName();

        this.games = new HashSet<>();

        if (genre.getGames() != null) {
            for (Game game : genre.getGames()) {
                this.games.add(new GameData(game));
            }
        }
    }
}


