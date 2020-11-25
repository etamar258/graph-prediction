package services.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.algorithms.GetReleventMoviesAlgo;
import core.algorithms.SimpleConnectionGetBestAlgo;
import core.entities.GuiMovie;
import core.entities.MovieSpacielConnection;
import core.exceptions.DBQueryCreationException;
import core.repositories.MovieSpacielConnectionRepository;

@Controller
public class GetSpecialConnectionController 
{
	private static final String EMPTY_JSON = "{}";
	private static final String TITLE_PARAMETER = "title";
	private static final int DEFUALT_MAX_NUM_OF_MOVIES = 10;
	private GetReleventMoviesAlgo movieFinderAlgo;
	private SimpleConnectionGetBestAlgo simpleConnAlg ;
	private MovieSpacielConnectionRepository dbRepository;
	
	public GetSpecialConnectionController() 
	{
		movieFinderAlgo = new GetReleventMoviesAlgo();
		dbRepository = new MovieSpacielConnectionRepository();
		simpleConnAlg = new SimpleConnectionGetBestAlgo();
	}
	
	@CrossOrigin
	@RequestMapping(value="/special-movie", method=RequestMethod.GET)
	public @ResponseBody String getAllTheRelativesMovies(@RequestParam(value="movieName", required=true) String movieName,
																@RequestParam(value="maxNumOfMovies", required=true) int maxNumOfMovies)
	{
		movieFinderAlgo.cleanAll();
		
		if(movieName == null)
		{
			return EMPTY_JSON;
		}
		movieName = movieName.replace("\"", "");
		int finalMaxNumOfMovies = maxNumOfMovies == 0 ? DEFUALT_MAX_NUM_OF_MOVIES : maxNumOfMovies;
		
		MovieSpacielConnection requestedMovie = getMovieByName(movieName);
		
		if(requestedMovie == null)
		{
			return EMPTY_JSON;
		}
		
		movieFinderAlgo.insertAllConnections(requestedMovie.getOutSpacielConnections());
		Set<MovieSpacielConnection> realatedMovies = movieFinderAlgo.getRealatedMovies(finalMaxNumOfMovies);
		
		return getObjectAsStringJson(realatedMovies);
	}
	
	@CrossOrigin
	@RequestMapping(value="/simple-movie", method=RequestMethod.GET)
	public @ResponseBody String getAllTheSimpleRelativesMovies(@RequestParam(value="movieName", required=true) String movieName,
																@RequestParam(value="maxNumOfMovies", required=true) int maxNumOfMovies)
	{
		simpleConnAlg.cleanAll();
		
		if(movieName == null)
		{
			return EMPTY_JSON;
		}
		movieName = movieName.replace("\"", "");
		int finalMaxNumOfMovies = maxNumOfMovies == 0 ? DEFUALT_MAX_NUM_OF_MOVIES : maxNumOfMovies;
		
		MovieSpacielConnection requestedMovie = getMovieByName(movieName);
		
		if(requestedMovie == null)
		{
			return EMPTY_JSON;
		}
		
		simpleConnAlg.insertAllConnections(requestedMovie.getSimpleConnection());
		List<MovieSpacielConnection> realatedMovies = simpleConnAlg.getRealatedMovies(finalMaxNumOfMovies);
		
		return getListAsStringJson(realatedMovies);
	}
	
	private String getListAsStringJson(List<MovieSpacielConnection> realatedMovies) 
	{
		ObjectMapper mapper = new ObjectMapper();
		String resultString = "[";
		int indexCounter = 1;int 
		maxNumOfJsons = realatedMovies.size();
		
		try 
		{
			for (MovieSpacielConnection movieSpacielConnection : realatedMovies) 
			{
				GuiMovie currMovie = new GuiMovie(movieSpacielConnection);
				resultString += mapper.writeValueAsString(currMovie);
				
				if(indexCounter < maxNumOfJsons)
				{
					resultString += ",";
				}
				indexCounter++;
			}
			
			resultString += "]";
			return resultString;
		} 
		catch (JsonProcessingException e) 
		{
			System.out.println(e.getMessage());
			return EMPTY_JSON;
		}
	}

	@CrossOrigin
	@RequestMapping(value="/movies-year", method=RequestMethod.GET)
	public @ResponseBody String getMoviesByYear(@RequestParam(value="year", required=true) String year)
	{
		Iterable<MovieSpacielConnection> allMovies = 
				dbRepository.executeQueryWitoutParameters("MATCH(n:Movie) WHERE n.year=\"" + year + "\" RETURN n");
		Set<MovieSpacielConnection> resultMovies = new HashSet<>();
		
		for (MovieSpacielConnection movieSpacielConnection : allMovies) 
		{
			resultMovies.add(movieSpacielConnection);
		}
		
		return getObjectAsStringJson(resultMovies);
	}

	@CrossOrigin
	@RequestMapping(value="/movies-name", method=RequestMethod.GET)
	public @ResponseBody String getMoviesByName(@RequestParam(value="movieName", required=true) String movieName)
	{
		movieName = movieName.replace("\"", "");
		Iterable<MovieSpacielConnection> allMovies = 
				dbRepository.executeQueryWitoutParameters("MATCH(movie1:Movie) WHERE movie1.title =~ '.*" + movieName + "*.' RETURN movie1");
		Set<MovieSpacielConnection> resultMovies = new HashSet<>();
		
		for (MovieSpacielConnection movieSpacielConnection : allMovies) 
		{
			resultMovies.add(movieSpacielConnection);
		}
		
		return getObjectAsStringJson(resultMovies);
	}
	
	private String getObjectAsStringJson(Set<MovieSpacielConnection> resultMovies)
	{
		ObjectMapper mapper = new ObjectMapper();
		String resultString = "[";
		int indexCounter = 1;int 
		maxNumOfJsons = resultMovies.size();
		
		try 
		{
			for (MovieSpacielConnection movieSpacielConnection : resultMovies) 
			{
				GuiMovie currMovie = new GuiMovie(movieSpacielConnection);
				resultString += mapper.writeValueAsString(currMovie);
				
				if(indexCounter < maxNumOfJsons)
				{
					resultString += ",";
				}
				indexCounter++;
			}
			
			resultString += "]";
			return resultString;
		} 
		catch (JsonProcessingException e) 
		{
			System.out.println(e.getMessage());
			return EMPTY_JSON;
		}
	}

	private MovieSpacielConnection getMovieByName(String movieName) 
	{
		Map<String, String> parameters = new HashMap<>();
		parameters.put(TITLE_PARAMETER, movieName);
		
		try 
		{
			Iterable<MovieSpacielConnection> moviesToFind = dbRepository.findDBObject(parameters);
			
			for (MovieSpacielConnection movieSpacielConnection : moviesToFind) 
			{
				return dbRepository.find(movieSpacielConnection.getId());
			}
			
			return null;
		} 
		catch (DBQueryCreationException e) 
		{
			return null;
		}
	}
}
