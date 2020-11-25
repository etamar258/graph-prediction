package core.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import core.algorithms.GetReleventMoviesAlgo;
import core.entities.MovieSpacielConnection;
import core.exceptions.DBQueryCreationException;

public class MovieSpacielConnectionRepository extends Repository<MovieSpacielConnection>
{

	@Override
	public Class<MovieSpacielConnection> getEntityType() 
	{
		return MovieSpacielConnection.class;
	}

	@Override
	public String getObjectName() 
	{
		return "Movie";
	}
	
	public static void main(String[] args) throws DBQueryCreationException 
	{
		MovieSpacielConnectionRepository r = new MovieSpacielConnectionRepository();
		Map<String, String> parameters = new HashMap<>();
		parameters.put("title", "The Company");
		GetReleventMoviesAlgo alg = new GetReleventMoviesAlgo();
		Iterable<MovieSpacielConnection> movie = r.findDBObject(parameters);
		
		for (MovieSpacielConnection movieSpacielConnection : movie) 
		{
			MovieSpacielConnection currMovieRealation = r.find(movieSpacielConnection.getId());
			System.out.println(currMovieRealation.getSimpleConnection().size());
			alg.insertAllConnections(currMovieRealation.getOutSpacielConnections());
			Set<MovieSpacielConnection> likesMovies = alg.getRealatedMovies(20);
			
			for (MovieSpacielConnection movieSpacielConnection2 : likesMovies) 
			{
				System.out.println(movieSpacielConnection2.getTitle());
			}
		}
		
		
	}
}
