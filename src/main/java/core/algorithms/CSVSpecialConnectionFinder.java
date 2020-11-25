package core.algorithms;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import core.algorithms.parser.CsvSimilarityElementParser;
import core.algorithms.utils.CounterHahMapUtil;
import core.algorithms.utils.JSONIOUtils;
import core.entities.GroupMoviesElement;
import core.entities.SpecialConnectionElement;
import imdb.api.jsonobjects.IMDBMovieObject;

public class CSVSpecialConnectionFinder implements FindCharacteristicsOfGroups
{

	private static final String SEPARATOR_KEY = ",";
	private final int MIN_NUM_OF_APPEARANCE = 2;
	private CsvSimilarityElementParser csvSimilarityElementParser = new CsvSimilarityElementParser();
	
	@Override
	public void genrateStatistics(String dataFilePath, String resultJsonFile) 
	{
		Set<GroupMoviesElement> allMovies = csvSimilarityElementParser.getAllMoviesGroups(dataFilePath);
		Set<SpecialConnectionElement> allTheSpacielConnection = new HashSet<>();
		HashMap<SpecialConnectionElement, Integer> connectionMap = new HashMap<>();
		double maxNumOfEntities = 0;
		
		for (GroupMoviesElement currMovieGroup : allMovies) 
		{
			SpecialConnectionElement currSpecialConnections = getMovieSpecialConnections(currMovieGroup);
			
			if(checkIfIsComplexConnection(currSpecialConnections))
			{
				CounterHahMapUtil.insertOrUpdateJsonEntityCounterMape(connectionMap, currSpecialConnections);
				maxNumOfEntities++;
			}
		}
		
		
		for (SpecialConnectionElement specialConnectionElement : connectionMap.keySet()) 
		{
			specialConnectionElement.setProbability(connectionMap.get(specialConnectionElement) / maxNumOfEntities);
			allTheSpacielConnection.add(specialConnectionElement);
		}
		
		JSONIOUtils.writeSetOfObjectsFile(allTheSpacielConnection, resultJsonFile);
	}
	
	public Set<SpecialConnectionElement> GetAllTheSpacielConnection(String dataFilePath)
	{
		Set<GroupMoviesElement> allMovies = csvSimilarityElementParser.getAllMoviesGroups(dataFilePath);
		Set<SpecialConnectionElement> allTheSpacielConnection = new HashSet<>();
		HashMap<SpecialConnectionElement, Integer> connectionMap = new HashMap<>();
		double maxNumOfEntities = 0;
		
		for (GroupMoviesElement currMovieGroup : allMovies) 
		{
			SpecialConnectionElement currSpecialConnections = getMovieSpecialConnections(currMovieGroup);
			
			if(checkIfIsComplexConnection(currSpecialConnections))
			{
				CounterHahMapUtil.insertOrUpdateJsonEntityCounterMape(connectionMap, currSpecialConnections);
				maxNumOfEntities++;
			}
		}
		
		
		for (SpecialConnectionElement specialConnectionElement : connectionMap.keySet()) 
		{
			specialConnectionElement.setProbability(connectionMap.get(specialConnectionElement) / maxNumOfEntities);
			allTheSpacielConnection.add(specialConnectionElement);
		}
		
		return allTheSpacielConnection;
	}

	private boolean checkIfIsComplexConnection(SpecialConnectionElement currSpecialConnections) 
	{
		int numOfElements = currSpecialConnections.getActorsList().size() + 
							currSpecialConnections.getDirectorsList().size() +
							currSpecialConnections.getWriterList().size() + 
							currSpecialConnections.getGenList().size();
		
		return numOfElements > 1;
	}

	private SpecialConnectionElement getMovieSpecialConnections(GroupMoviesElement currMovieGroup) 
	{
		HashMap<String, Integer> appearanceActorCounter = new HashMap<>();
		HashMap<String, Integer> appearanceWriterCounter = new HashMap<>();
		HashMap<String, Integer> appearanceDirectorCounter = new HashMap<>();
		HashMap<String, Integer> appearanceGenCounter = new HashMap<>();
		Set<String> actorList = new HashSet<>();
		Set<String> writerList = new HashSet<>();
		Set<String> directorList = new HashSet<>();
		Set<String> genList = new HashSet<>();
		
		for (IMDBMovieObject currMovie : currMovieGroup.getMoviesList()) 
		{
			CounterHahMapUtil.createHashmapFromString(appearanceActorCounter, currMovie.getActors(), SEPARATOR_KEY);
			CounterHahMapUtil.createHashmapFromString(appearanceWriterCounter, currMovie.getWriter(), SEPARATOR_KEY);
			CounterHahMapUtil.createHashmapFromString(appearanceDirectorCounter, currMovie.getDirector(), SEPARATOR_KEY);
			CounterHahMapUtil.createHashmapFromString(appearanceGenCounter, currMovie.getGenre(), SEPARATOR_KEY);
		}
		
		updateSetByAppearance(appearanceActorCounter, actorList);
		updateSetByAppearance(appearanceWriterCounter, writerList);
		updateSetByAppearance(appearanceDirectorCounter, directorList);
		updateSetByAppearance(appearanceGenCounter, genList);
		
		return new SpecialConnectionElement(actorList, directorList, writerList, genList, 0);
	}

	private void updateSetByAppearance(HashMap<String, Integer> appearanceCounter, Set<String> setToUpdate) 
	{
		for (String currCountObject : appearanceCounter.keySet()) 
		{
			if(appearanceCounter.get(currCountObject) >= MIN_NUM_OF_APPEARANCE)
			{
				setToUpdate.add(currCountObject);
			}
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		CSVSpecialConnectionFinder trial = new CSVSpecialConnectionFinder();
		trial.genrateStatistics("result.txt", "specialConnectionResult.json");
	}
}
