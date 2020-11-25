package core.entities;

import java.util.HashSet;
import java.util.Set;
import imdb.api.jsonobjects.IMDBMovieObject;

public class GroupMoviesElement 
{
	private int groupId;
	private Set<IMDBMovieObject> moviesList;
	
	public GroupMoviesElement() 
	{
		setMoviesList(new HashSet<>());
	}

	public int getGroupId() 
	{
		return groupId;
	}

	public void setGroupId(int groupId) 
	{
		this.groupId = groupId;
	}

	public Set<IMDBMovieObject> getMoviesList() 
	{
		return moviesList;
	}

	public void setMoviesList(Set<IMDBMovieObject> moviesList) 
	{
		this.moviesList = moviesList;
	}
}
