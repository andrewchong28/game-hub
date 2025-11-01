package game.hub.entity;

import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity// JPA Annotation that tells Hibernate that this class represents a table in the database(map java objects to database tables)
            //contains all the fields, ids, and relationships like the database objects
			//Hibernate - Java framework that helps you interact with a relational database using Java objects instead of writing SQL
@Data // Lombok generates getters and Setters
public class Developer {
	
	//Annotations: developerId auto increment
	@Id //marks this field as the primary key.
	@GeneratedValue//tells JPA/Hibernate to automatically generate a value when you save a new entity.
	private Integer developerId;
	private String  developerName;
	private String  developerCountry;
		
	/*
	  - @OneToMany -  One Developer → Many Games
	  - mappedBy = "developer" - tells JPA that the owning side of the relationship is the developer field in games
	  - cascade = CascadeType.ALL  - any operation (save, update, delete) on developer will cascade to its Game
	  - orphanRemoval = true -  if a Game is removed from the games set, it will also be deleted from the database
	  
	  - @EqualsAndHashCode.Exclude &  @ToString.Exclude from LomBok are added to recursive relationship variables
	  	-Prevents recursion when .toString(), .equals(), or .hashCode() methods are called. 
	  		- Recursive relationship = two entities each hold a reference to the other.This Developer class has Set<Game> 
	  		- If two objects point to each other, the generated method keeps trying to print or compare forever
	  		- Lombok’s @Data generates toString(), equals(), and hashCode() for all fields by default which will cause recursion
	  			- @EqualsAndHashCode.Exclude and @ToString.Exclude prevent infinite recursion and StackOverflowErrors.
	  		- Need exclusions so Lombok will skip the recursive variables when generating the methods
	 */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true) 
    private Set<Game> games;
	
	
}