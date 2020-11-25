package core.entities;

import com.google.gson.annotations.SerializedName;

public class GroupStatistics implements JsonEntity
{
	@SerializedName("Actors")
	private Double actorsConnectionStatistic;
	
	@SerializedName("Generes")
	private Double generesConnectionStatistic;
	
	@SerializedName("Director")
	private Double directorConnectionStatistic;
	
	@SerializedName("Writer")
	private Double writerConnectionStatistic;
	
	public GroupStatistics(Double actorStat, Double generStat, Double directStat, Double writerStat) 
	{
		actorsConnectionStatistic = actorStat;
		generesConnectionStatistic = generStat;
		directorConnectionStatistic = directStat;
		writerConnectionStatistic = writerStat;
	}
	
	public double getActorStat()
	{
		return actorsConnectionStatistic.doubleValue();
	}
	
	public double getGenerStat()
	{
		return generesConnectionStatistic.doubleValue();
	}
	
	public double getDirectorStat()
	{
		return directorConnectionStatistic.doubleValue();
	}
	
	public double getWriterStat()
	{
		return writerConnectionStatistic.doubleValue();
	}
}
