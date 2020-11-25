package core.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

public class SpecialConnectionElement implements JsonEntity
{
	@SerializedName("ActorsList")
	private Set<String> actorsList;
	
	@SerializedName("DirectorList")
	private Set<String> directorList;
	
	@SerializedName("WriterList")
	private Set<String> writerList;
	
	@SerializedName("GenList")
	private Set<String> genList;
	
	@SerializedName("Probability")
	private double probability;
	
	@JsonIgnore
	private transient String hashString;
	
	public SpecialConnectionElement(Set<String> actorsList, Set<String> directorList,
									Set<String> writerList, Set<String> genList, double probability) 
	{
		this.actorsList = actorsList;
		this.directorList = directorList;
		this.writerList = writerList;
		this.probability = probability;
		this.genList = genList;
		
		this.hashString = createHashString();
	}
	
	private String createHashString() 
	{
		String resultString = "actor:";
		
		for (String currActor : actorsList) 
		{
			resultString += " " + currActor;
		}
		
		resultString += " director:";
		
		for (String currDirector : directorList) 
		{
			resultString += " " + currDirector;
		}
		
		resultString += " writer:";
		
		for (String currDirector : writerList) 
		{
			resultString += " " + currDirector;
		}
		
		resultString += " genere:";
		
		for (String currGen : genList) 
		{
			resultString += " " + currGen;
		}
		
		return resultString;
	}

	public Set<String> getActorsList()
	{
		return actorsList;
	}
	
	public Set<String> getDirectorsList()
	{
		return directorList;
	}
	
	public Set<String> getWriterList()
	{
		return writerList;
	}
	
	public Set<String> getGenList()
	{
		return genList;
	}
	
	public double getProbability()
	{
		return probability;
	}
	
	public void setProbability(double probability)
	{
		this.probability = probability;
	}
	
	@Override
	public int hashCode() 
	{
		return hashString.hashCode();
	}
	
	@Override
	public boolean equals(Object other) 
	{
		if (other == null) 
		{
			return false;
		}
		
	    if (other == this)
	    {
	    	return true;
	    }
	    
	    if (!(other instanceof SpecialConnectionElement))
	    {
	    	return false;
	    }
	    
	    SpecialConnectionElement otherConnection = (SpecialConnectionElement)other;
	    
	    return hashCode() == otherConnection.hashCode();
	}
}
