package core.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "User")
public class UserSimilarty extends Entity 
{
	@Relationship(type = "SIMILARITY", direction = Relationship.INCOMING)
	private Set<UserSimilarty> similarity = new HashSet<UserSimilarty>();
	private DbType type;
	private long dbId;

	public Set<UserSimilarty> getSimilarity() 
	{
		return similarity;
	}

	public DbType getUserType() 
	{
		return type;
	}

	public Long getUserDbId() 
	{
		return dbId;
	}
}
