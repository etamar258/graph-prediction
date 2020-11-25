package core.algorithms.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import core.entities.GroupMoviesElement;
import imdb.api.exception.JsonConvertorException;
import imdb.api.exception.RequestException;
import imdb.api.jsonobjects.IMDBMovieObject;
import imdb.api.requstobjects.IMDBGetRequest;
import imdb.api.requstobjects.IMDBRequestParameterType;
import imdb.api.utils.IMDBMovieJsonConvertor;

public class CsvSimilarityElementParser 
{
	private IMDBGetRequest imdbMovieRequest;
	private IMDBMovieJsonConvertor jsonConvertor;
	
	public CsvSimilarityElementParser() 
	{
		imdbMovieRequest = new IMDBGetRequest();
		jsonConvertor = new IMDBMovieJsonConvertor();
	}
	
	public Set<GroupMoviesElement> getAllMoviesGroups(String fileName)
	{
		Set<GroupMoviesElement> resultGroups = new HashSet<>();
		BufferedReader allGroupsLines = 
				new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
		
		String CurrMovieGroupLine;
		

		try 
		{
			while ((CurrMovieGroupLine = allGroupsLines.readLine()) != null)   
			{
				CurrMovieGroupLine = cleanNoisesFromFileLine(CurrMovieGroupLine);
				
				if(CurrMovieGroupLine.split(",").length == 2)
				{
					continue;
				}
				
				GroupMoviesElement currGroupElement = null;
				
				try 
				{
					currGroupElement = getGroupMovieElementFromCsvLine(CurrMovieGroupLine);
				} 
				catch (RequestException e) 
				{
					continue;
				} 
				catch (JsonConvertorException e) 
				{
					continue;
				}
				
				resultGroups.add(currGroupElement);
			}
			
			return resultGroups;
		} 
		catch (IOException e) 
		{
			return null;
		}
	}
	
	private GroupMoviesElement getGroupMovieElementFromCsvLine(String currMovieGroupLine) throws RequestException, JsonConvertorException 
	{
		GroupMoviesElement resultGroup = new GroupMoviesElement();
		String[] allParameters = currMovieGroupLine.split(",");
		resultGroup.setGroupId(Integer.valueOf(allParameters[0]));
		
		for (int currMovieIndex = 1; currMovieIndex < allParameters.length; currMovieIndex++) 
		{
			IMDBMovieObject currMovieObject = getMovieObject(allParameters[currMovieIndex]);
			
			if(currMovieObject != null)
			{
				resultGroup.getMoviesList().add(currMovieObject);
			}
		}
		
		if(resultGroup.getMoviesList().size() > 1)
		{
			return resultGroup;
		}
		else
		{
			return null;
		}
	}

	private IMDBMovieObject getMovieObject(String movieName) throws RequestException, JsonConvertorException 
	{
		imdbMovieRequest.resetRequest();
		imdbMovieRequest.addParameter(IMDBRequestParameterType.TITLE, movieName);
		
		String response = imdbMovieRequest.sendRequest();

        return jsonConvertor.getJsonJavaObject(response);
	}

	private String cleanNoisesFromFileLine(String fileLine)
	{
		String result = fileLine.replace("\"", "");
		result = result.replace("[", "");
		result = result.replace("]", "");
		
		return result;
	}
}
