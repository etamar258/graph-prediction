package core.entities;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="SPACIEL")
public class Spaciel extends Entity
{
	private float prob;
	
	@StartNode
    private MovieSpacielConnection currMovie;
	
    @EndNode
    private MovieSpacielConnection connectedMovie;
    
    public void setCurrMovie(MovieSpacielConnection currMovie)
    {
    	this.currMovie = currMovie;
    }
    
    public void setConnectedMovie(MovieSpacielConnection connectedMovie)
    {
    	this.connectedMovie = connectedMovie;
    }
    
    public void setProb(float prob)
    {
    	this.prob = prob;
    }
    
    public MovieSpacielConnection getCurrMovie()
    {
    	return currMovie;
    }
    
    public MovieSpacielConnection getConnectedMovie()
    {
    	return connectedMovie;
    }
    
    public float getProbability()
    {
    	return prob;
    }
}
