package game.hub.entity;

import java.util.HashSet;



import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity// JPA Annotation that tells Hibernate that this class represents a table in the database(map java objects to database tables)
//contains all the fields, ids, and relationships like the database objects
//Hibernate - Java framework that helps you interact with a relational database using Java objects instead of writing SQL
@Data // Lombok generates getters and Setters
public class Genre {
	//Annotations: genreId auto increment
	@Id //marks this field as the primary key.
	@GeneratedValue//tells JPA/Hibernate to automatically generate a value when you save a new entity.
	private Integer genreId;
	private String genreName;

	
	/*
	 - Annotation: instructions for JPA and Lombok on how to handle this field
	 - @ManyToMany is the JPA annotation that defines a many-to-many relationship between two entities (e.g., Game and Genre)
	    - In this case, one Genre can be associated with many Games, and one Game can have many Genres
	    - mappedBy = "genres" indicates that this is the inverse side of the relationship
	        - The owning side is Game.genres, which defines the join table (game_genre)
	        - Without mappedBy, JPA would try to create a second join table, which we don’t want
	    - Cascade is not needed on the inverse side; the owning side handles saving related entities

	 - private Set<Game> games = new HashSet<>() 
	    - Stores all the Games associated with this Genre
	    - Set is used instead of List to avoid duplicates
	    - Initializing with HashSet avoids null pointer exceptions when adding games

	 - @EqualsAndHashCode.Exclude & @ToString.Exclude from Lombok
	    - Prevents infinite recursion when calling .toString(), .equals(), or .hashCode()
	    - Recursive relationship occurs because Game also holds a reference to Genre
	    - Lombok’s @Data generates these methods for all fields by default, so exclusions are necessary to avoid StackOverflowErrors
	*/
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	@ManyToMany(mappedBy = "genres") 
	private Set<Game> games = new HashSet<>();
	
}
