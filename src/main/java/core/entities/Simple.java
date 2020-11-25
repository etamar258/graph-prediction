package core.entities;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="SIMPLE_CONNECTION")
public class Simple extends Entity implements Comparable<Simple>
{
	private String weight;
	
	@StartNode
	private MovieSpacielConnection currMovie;
	
	@EndNode
	private MovieSpacielConnection connectedMovie;
	
	public void setWeight(String weight)
	{
		this.weight = weight;
	}
	
	public String getWeight()
	{
		return weight;
	}
	
	public MovieSpacielConnection getCurrentMovie()
	{
		return currMovie;
	}
	
	public MovieSpacielConnection getConnectedMovies()
	{
		return connectedMovie;
	}

	public void setCurrMovie(MovieSpacielConnection currMovie)
	{
		this.currMovie = currMovie;
	}
	
	public void setConnectedMovie(MovieSpacielConnection connectedMovie)
	{
		this.connectedMovie = connectedMovie;
	}
	
	@Override
	public int compareTo(Simple simpleConnectionTOCompare) 
	{
		return (int) (Float.valueOf(this.weight) - Float.valueOf(simpleConnectionTOCompare.getWeight()));
	}
	
	@Override
	public boolean equals(Object simpleConnection) 
	{
		if (simpleConnection == null) 
    	{
    		return false;
    	}
    	
        if (simpleConnection == this)
        {
        	return true;
        }
        
        if (!(simpleConnection instanceof Simple))
        {
        	return false;
        }
        
        Simple castSimpleClass = (Simple)simpleConnection;
        
    	return this.getConnectedMovies().getTitle().equals(castSimpleClass.getConnectedMovies().getTitle());
	}
	
	@Override
	public int hashCode() 
	{
		return this.getConnectedMovies().getTitle().hashCode();
	}
}
