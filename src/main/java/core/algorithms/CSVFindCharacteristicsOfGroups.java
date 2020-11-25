package core.algorithms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.io.Files;
import com.google.gson.Gson;

import core.algorithms.parser.CsvSimilarityElementParser;
import core.algorithms.utils.CounterHahMapUtil;
import core.entities.GroupMoviesElement;
import core.entities.GroupStatistics;
import imdb.api.jsonobjects.IMDBMovieObject;

public class CSVFindCharacteristicsOfGroups implements FindCharacteristicsOfGroups
{
	
	CsvSimilarityElementParser csvSimilarityElementParser = new CsvSimilarityElementParser();

	@Override
	public void genrateStatistics(String dataFilePath, String resultStatisticsFile) 
	{
		Set<GroupMoviesElement> allMovies = csvSimilarityElementParser.getAllMoviesGroups(dataFilePath);
		Map<Integer, GroupStatistics> groupStatisticResult = new HashMap<>();
		HashMap<Integer, GroupStatistics> finalStat = new HashMap<Integer, GroupStatistics>();
		
		double maxStatSize = allMovies.size();
		double writerStat = 0;
		double directorStat = 0;
		double generStat = 0;
		double actorStat = 0;
		
		for (GroupMoviesElement currMoviesGroup : allMovies) 
		{
			GroupStatistics currGroupStat = calcStatistics(currMoviesGroup);
			groupStatisticResult.put(currMoviesGroup.getGroupId(), currGroupStat);
			writerStat += currGroupStat.getWriterStat();
			directorStat += currGroupStat.getDirectorStat();
			generStat += currGroupStat.getGenerStat();
			actorStat += currGroupStat.getActorStat();
		}
		
		writeResultFile(groupStatisticResult, resultStatisticsFile);
		
		writerStat = writerStat / maxStatSize;
		directorStat = directorStat / maxStatSize;
		generStat = generStat / maxStatSize;
		actorStat = actorStat / maxStatSize;
		
		GroupStatistics finalGroupStat = new GroupStatistics(actorStat, generStat, directorStat, writerStat);
		finalStat.put(1, finalGroupStat);
		writeResultFile(finalStat, "finalStat.json");
	}

	private void writeResultFile(Map<Integer, GroupStatistics> groupStatisticResult, String resultStatisticsFile) 
	{
		Gson gsonParser = new Gson();
		gsonParser.toJson(groupStatisticResult);
		
		try 
		{
			Files.write(gsonParser.toJson(groupStatisticResult).getBytes(), new File(resultStatisticsFile));
		}
		catch (IOException e) 
		{
			System.err.println("Can't write to the json file" + e.getMessage());
		}
	}
	
	private double calcStatistic(HashMap<String, Integer> mapCounter, int maxNumOfMovies) 
	{
		double maxNumOfKey = 0;
		
		for (String currObject : mapCounter.keySet()) 
		{
			double currNumOfObject = mapCounter.get(currObject);
			if(currNumOfObject > maxNumOfKey)
			{
				maxNumOfKey = currNumOfObject;
			}
		}
		
		return ((double)(maxNumOfKey/maxNumOfMovies)) * 100;
	}
	
	private GroupStatistics calcStatistics(GroupMoviesElement currMoviesGroup) 
	{
		HashMap<String, Integer> writersCounter = new HashMap<>();
		HashMap<String, Integer> directorsCounter = new HashMap<>();
		HashMap<String, Integer> generCounter = new HashMap<>();
		HashMap<String, Integer> actorsCounter = new HashMap<>();
		
		for (IMDBMovieObject currMovieToCheck : currMoviesGroup.getMoviesList()) 
		{
			CounterHahMapUtil.insertOrUpdateNewCounterMape(writersCounter, currMovieToCheck.getWriter());
			CounterHahMapUtil.insertOrUpdateNewCounterMape(directorsCounter, currMovieToCheck.getDirector());
			CounterHahMapUtil.insertOrUpdateNewCounterMape(generCounter, currMovieToCheck.getGenre());
			CounterHahMapUtil.insertOrUpdateNewCounterMape(actorsCounter, currMovieToCheck.getActors());
		}
		
		int maxNumOfMovies = currMoviesGroup.getMoviesList().size();
		
		double writerStat = calcStatistic(writersCounter, maxNumOfMovies);
		double directorStat = calcStatistic(directorsCounter, maxNumOfMovies);
		double generStat = calcStatistic(generCounter, maxNumOfMovies);
		double actorStat = calcStatistic(actorsCounter, maxNumOfMovies);
		
		return new GroupStatistics(actorStat, generStat, directorStat, writerStat);
	}
	
	public static void main(String[] args) throws IOException 
	{
		CSVFindCharacteristicsOfGroups trial = new CSVFindCharacteristicsOfGroups();
		trial.genrateStatistics("result.txt", "statisticResult.json");
		
	}
}
