package core.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.cypher.internal.compiler.v3_1.helpers.simpleExpressionEvaluator;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@NodeEntity(label = "Movie")
public class MovieSpacielConnection extends Entity implements Comparable<MovieSpacielConnection>
{
	public static final long INVALID_ID = -1;
	
	@JsonIgnore
    @Relationship(type = "SPACIEL", direction = Relationship.OUTGOING)
    private Set<Spaciel> outSpaciel = new HashSet<Spaciel>();
	
//	@JsonIgnore
//    @Relationship(type = "SIMPLE_CONNECTION", direction=Relationship.INCOMING)
//    private Set<Simple> outSimple = new HashSet<Simple>();
    
    // Database - specific data
    @JsonProperty("MovieLensMovieId")
    private long movieLensMovieId = INVALID_ID;
    // Database - specific data
    @JsonProperty("NetflixMovieId")
    private long netflixMovieId = INVALID_ID;
    //    Metadata
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Rated")
    private String rated;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Genre")
    private ArrayList<String> genre;
    @JsonProperty("Director")
    private ArrayList<String> director;
    @JsonProperty("Writer")
    private ArrayList<String> writer;
    @JsonProperty("Actors")
    private ArrayList<String> actors;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Language")
    private ArrayList<String> language;
    @JsonProperty("Country")
    private ArrayList<String> country;
    @JsonProperty("Awards")
    private ArrayList<String> awards;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Metascore")
    private String metascore;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("imdbVotes")
    private String imdbVotes;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Response")
    private String response;
    
    @JsonIgnore
    private float thisIdmbRating;
    
    @JsonIgnore
    private float prob;
    
    @Override
    public int hashCode() 
    {
    	return title.hashCode();
    }
    
    @Override
    public boolean equals(Object movieToCompare) 
    {
    	if (movieToCompare == null) 
    	{
    		return false;
    	}
    	
        if (movieToCompare == this)
        {
        	return true;
        }
        
        if (!(movieToCompare instanceof MovieSpacielConnection))
        {
        	return false;
        }
        
        MovieSpacielConnection castMovieClass = (MovieSpacielConnection)movieToCompare;
        
    	return title.equals(castMovieClass.getTitle());
    }
    
    public float getidmbRatingCalc()
    {
    	if(thisIdmbRating == 0)
    	{
    		if(imdbRating.equals("N/A"))
    		{
    			thisIdmbRating = (float) 0.1;
    		}
    		else
    		{
    			thisIdmbRating = Float.valueOf(imdbRating) * 10;
    		}
    	}
    	
    	return thisIdmbRating;
    }
    
	@Override
	public int compareTo(MovieSpacielConnection movieToComper) 
	{
		float otherIdmbRating = movieToComper.getidmbRatingCalc();
		return (int) ((movieToComper.getProb() + otherIdmbRating) - (prob + getidmbRatingCalc()));
	}
    
	public Set<Simple> getSimpleConnection()
	{
		return null;//outSimple;
	}
	
	public void getSimpleConnection(Set<Simple> simpleConn)
	{
		//outSimple = simpleConn;
	}
	
	public void setProb(float prob)
	{
		this.prob = prob;
	}
	
	public float getProb()
	{
		return prob;
	}
    
    public Set<Spaciel> getOutSpacielConnections()
    {
    	return outSpaciel;
    }
    
    public long getMovieLensMovieId() {
        return movieLensMovieId;
    }

    public void setMovieLensMovieId(long movieLensMovieId) {
        this.movieLensMovieId = movieLensMovieId;
    }

    public long getNetflixMovieId() {
        return netflixMovieId;
    }

    public void setNetflixMovieId(long netflixMovieId) {
        this.netflixMovieId = netflixMovieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public ArrayList<String> getDirector() {
        return director;
    }

    public void setDirector(ArrayList<String> director) {
        this.director = director;
    }

    public ArrayList<String> getWriter() {
        return writer;
    }

    public void setWriter(ArrayList<String> writer) {
        this.writer = writer;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public ArrayList<String> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<String> language) {
        this.language = language;
    }

    public ArrayList<String> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<String> country) {
        this.country = country;
    }

    public ArrayList<String> getAwards() {
        return awards;
    }

    public void setAwards(ArrayList<String> awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
