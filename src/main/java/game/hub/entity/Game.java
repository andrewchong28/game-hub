package game.hub.entity;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity// JPA Annotation that tells Hibernate that this class represents a table in the database(map java objects to database tables)
//contains all the fields, ids, and relationships like the database objects
//Hibernate - Java framework that helps you interact with a relational database using Java objects instead of writing SQL
@Data // Lombok generates getters and Setters
public class Game {
	
	//Annotations: gameId auto increment
	@Id //marks this field as the primary key.
	@GeneratedValue//tells JPA/Hibernate to automatically generate a value when you save a new entity.
	private Integer gameId;
	private String  gameTitle;
	private String  gameDescription;
	private Date    gameReleaseDate;
	
	
	/* 
	 - Annotation: instructions for the compiler to tell it how to handle the code
	 - @ManyToOne - Many Games to one Developer 
	 	-cascade = CascadeType.ALL - changes can cascade to associated Developer when saving/updating/deleting a Game
	 - @JoinColumn(name = "developer_id") - defines the foreign key column in Game table that links to Developer primarykey
	 - private Developer developer - creating variable developer to point to a new instance of Developer
	 
	 - @EqualsAndHashCode.Exclude &  @ToString.Exclude from LomBok are added to recursive relationship variables
	 	- Prevents recursion when .toString(), .equals(), or .hashCode() methods are called. 
	 		- Recursive relationship = two entities each hold a reference to the other.This Game class has Developer developer
	 		- If two objects point to each other, the generated method keeps trying to print or compare forever
	 		- Lombok’s @Data generates toString(), equals(), and hashCode() for all fields by default which will cause recursion
	 			- @EqualsAndHashCode.Exclude and @ToString.Exclude prevent infinite recursion and StackOverflowErrors.
	  		- Need exclusions so Lombok will skip the recursive variables when generating the methods
	*/
   @EqualsAndHashCode.Exclude
   @ToString.Exclude
   @ManyToOne(cascade = CascadeType.PERSIST)// When saving the parent also save new child. Does not delete ore update
   @JoinColumn(name = "developer_id") 
   private Developer developer;
	
	
	
	/*
	 - Annotation: instructions for the compiler to tell it how to handle the code
	 - @ManyToMany is the Annotation name
	   - Tells JPA(Java Persistance API) this field represents a many to many relationship between two entities(e.g.,Genre and Game)
	   				- One genre to many Games
	   - mappedBy = "game_id" - Class genre has a field called "Game_Id" that manages the join to class Game
	   				- without mappedBy JPA will try and create two join tables.JPA will use the join defined in Game
	   - cascade = CascadeType.PERSIST - Tells JPA to save save any new genre when saving a game in Game
	   
	 - Private Set<Genre> genres - set that holds all the genres a Game belongs to
	   - Set instead of List to avoid duplicates
	   - JPA will populate this set when fetching a customer from the database
	   
	 - @EqualsAndHashCode.Exclude &  @ToString.Exclude from LomBok are added to recursive relationship variables
	 	- Prevents recursion when .toString(), .equals(), or .hashCode() methods are called. 
	 		- Recursive relationship = two entities each hold a reference to the other.This Game class has Set<Genre>
	 		- If two objects point to each other, the generated method keeps trying to print or compare forever
	 		    - Lombok’s @Data generates toString(), equals(), and hashCode() for all fields by default which will cause recursion
	 			- @EqualsAndHashCode.Exclude and @ToString.Exclude prevent infinite recursion and StackOverflowErrors.
	  		- Need exclusions so Lombok will skip the recursive variables when generating the methods
	 */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST) 
	@JoinTable(
		    name = "game_genre",
		    joinColumns = @JoinColumn(name = "game_id")
		    ,inverseJoinColumns = @JoinColumn(name = "genre_id")
		     )
	private Set<Genre> genres = new HashSet<>();
	
	
	
	
}
