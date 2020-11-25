package core.entities;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuiMovie 
{
	private final static long INVALID_ID = -1;
	
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
    
    public GuiMovie(MovieSpacielConnection copyMovie) 
    {
		this.actors = copyMovie.getActors();
		this.awards = copyMovie.getAwards();
		this.country = copyMovie.getCountry();
		this.director = copyMovie.getDirector();
		this.genre = copyMovie.getGenre();
		this.imdbID = copyMovie.getImdbID();
		this.imdbRating = copyMovie.getImdbRating();
		this.imdbVotes = copyMovie.getImdbVotes();
		this.language = copyMovie.getLanguage();
		this.metascore = copyMovie.getMetascore();
		this.movieLensMovieId = copyMovie.getMovieLensMovieId();
		this.netflixMovieId = copyMovie.getNetflixMovieId();
		this.plot = copyMovie.getPlot();
		this.poster = copyMovie.getPoster();
		this.rated = copyMovie.getRated();
		this.released = copyMovie.getReleased();
		this.response = copyMovie.getResponse();
		this.runtime = copyMovie.getRuntime();
		this.title = copyMovie.getTitle();
		this.type = copyMovie.getType();
		this.writer = copyMovie.getWriter();
		this.year = copyMovie.getYear();
	}
}
