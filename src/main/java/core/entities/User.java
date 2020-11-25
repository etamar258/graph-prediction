package core.entities;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "User")
public class User extends Entity {
    @Relationship(type = "RATED", direction = Relationship.OUTGOING)
    Set<Rating> ratings = new HashSet();
    @Relationship(type = "SIMILARITY", direction = Relationship.OUTGOING)
	Set<User> similarity = new HashSet<User>();
    DbType type;
    long dbId;

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public DbType getUserType() {
        return type;
    }

    public void setUserType(DbType userType) {
        this.type = userType;
    }

    public Long getUserDbId() {
        return dbId;
    }

    public void setUserDbId(Long userDbId) {
        this.dbId = userDbId;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }
    
    public Set<User> getSimilatryUsers()
    {
    	return similarity;
    }
}
