
/*
DTO - Data Transfer Object:
    - Used to transfer Developer data between layers.
    - Accepts and returns Developer data as JSON.
    - Converts Developer entities to DTOs.
*/
package game.hub.controller.model;

import java.util.HashSet;
import java.util.Set;

import game.hub.entity.Developer;
import game.hub.entity.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeveloperData {

    private Integer developerId;
    private String developerName;
    private String developerCountry;

    // A Developer can have many Games
    private Set<GameData> games;

    // Constructor: builds DeveloperData from Developer entity
    public DeveloperData(Developer developer) {
        this.developerId = developer.getDeveloperId();
        this.developerName = developer.getDeveloperName();
        this.developerCountry = developer.getDeveloperCountry();

        // Initialize the games set
        this.games = new HashSet<>();

        // Convert each Game entity into a GameData DTO
        if (developer.getGames() != null) {
            for (Game game : developer.getGames()) {
                this.games.add(new GameData(game));
            }
        }
    }
}