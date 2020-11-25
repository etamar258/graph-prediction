package core.entities;

public class EntityQueryProbability 
{
	private String entityQuery;
	private double probability;
	private String[] allGenre;
	
	public EntityQueryProbability(String entityQuery, double probability, String[] allGenre) 
	{
		this.entityQuery = entityQuery;
		this.probability = probability;
		this.allGenre = allGenre;
	}
	
	public String[] getAllGenere()
	{
		return allGenre;
	}
	
	public String getEntityQuery()
	{
		return entityQuery;
	}
	
	public double getProbability()
	{
		return probability;
	}
}
