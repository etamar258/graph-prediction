package core.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import core.entities.MovieSpacielConnection;
import core.entities.Spaciel;

public class GetReleventMoviesAlgo 
{
	private PriorityQueue<MovieSpacielConnection> priorityMovieQueue;
	private Map<MovieSpacielConnection, MovieSpacielConnection> allRealatedMovies;
	
	public GetReleventMoviesAlgo() 
	{
		priorityMovieQueue = new PriorityQueue<>();
		allRealatedMovies = new HashMap<>();
	}
	
	public void insertAllConnections(Set<Spaciel> allSpacielConnections)
	{
		insertAllMovies(allSpacielConnections);
		priorityMovieQueue.addAll(allRealatedMovies.values());
	}
 
	public Set<MovieSpacielConnection> getRealatedMovies(int numOfMovies)
	{
		Set<MovieSpacielConnection> resultMovies = new HashSet<>();
		
		int maxNumOfMovies = numOfMovies > priorityMovieQueue.size() ? priorityMovieQueue.size() : numOfMovies;
		
		for (int lCurrMovie = 0; lCurrMovie < maxNumOfMovies; lCurrMovie++) 
		{
			resultMovies.add(priorityMovieQueue.poll());
		}
		
		return resultMovies;
	}
	
	private void insertAllMovies(Set<Spaciel> allSpacielConnections) 
	{
		for (Spaciel currSpacielConnection : allSpacielConnections) 
		{
			MovieSpacielConnection currMovie = currSpacielConnection.getConnectedMovie();
			
			if(allRealatedMovies.containsValue(currMovie))
			{
				MovieSpacielConnection movieToUpdate = allRealatedMovies.get(currMovie);
				movieToUpdate.setProb(movieToUpdate.getProb() + currSpacielConnection.getProbability());
			}
			else
			{
				currMovie.setProb(currSpacielConnection.getProbability());
				allRealatedMovies.put(currMovie, currMovie);
			}
		}
	}
	
	public void cleanAll()
	{
		priorityMovieQueue.clear();
		allRealatedMovies.clear();
	}
}
