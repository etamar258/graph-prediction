package core.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import core.entities.MovieSpacielConnection;
import core.entities.Simple;

public class SimpleConnectionGetBestAlgo 
{
	private PriorityQueue<Simple> priorityConnectionQueue;
	private Map<MovieSpacielConnection, MovieSpacielConnection> allRealatedMovies;
	private Set<MovieSpacielConnection> existsMovies;
	
	public SimpleConnectionGetBestAlgo() 
	{
		priorityConnectionQueue = new PriorityQueue<>();
		allRealatedMovies = new HashMap<>();
		existsMovies = new HashSet<>();
	}
	
	public void insertAllConnections(Set<Simple> allSimpleConnections)
	{
		priorityConnectionQueue.addAll(allSimpleConnections);
	}
 
	public List<MovieSpacielConnection> getRealatedMovies(int numOfMovies)
	{
		List<MovieSpacielConnection> resultMovies = new ArrayList<>();
		
		int maxNumOfMovies = numOfMovies > priorityConnectionQueue.size() ? priorityConnectionQueue.size() : numOfMovies;
		
		for (int lCurrMovie = 0; lCurrMovie < maxNumOfMovies; lCurrMovie++) 
		{
			MovieSpacielConnection currentMovie = priorityConnectionQueue.poll().getCurrentMovie();
			if(!existsMovies.contains(currentMovie))
			{
				resultMovies.add(currentMovie);
			}
			else
			{
				lCurrMovie--;
			}
				
		}
		
		return resultMovies;
	}
	
	public void cleanAll()
	{
		allRealatedMovies.clear();
		existsMovies.clear();
	}
}
