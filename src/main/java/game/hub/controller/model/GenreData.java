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
    private Set<GameData> games = new HashSet<>();

    // Main constructor
    public GenreData(Genre genre) {
        this(genre, true);
    }

    // Overloaded constructor to prevent infinite recursion
    public GenreData(Genre genre, boolean includeGames) {
        this.genreId = genre.getGenreId();
        this.genreName = genre.getGenreName();

        if (includeGames && genre.getGames() != null) {
            for (Game game : genre.getGames()) {
                // Prevent circular loop by using shallow GameData
                this.games.add(new GameData(game, false));
            }
        }
    }
}