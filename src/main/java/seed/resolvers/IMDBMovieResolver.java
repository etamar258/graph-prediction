package seed.resolvers;

import java.util.ArrayList;

import core.entities.Movie;
import core.repositories.MovieRepository;
import imdb.api.jsonobjects.IMDBMovieObject;
import imdb.api.requstobjects.IMDBGetRequest;
import imdb.api.requstobjects.IMDBRequestParameterType;
import imdb.api.utils.IMDBMovieJsonConvertor;
import imdb.api.utils.MovieJsonConvertor;
import seed.raw.csv.MovieMD;

public class IMDBMovieResolver implements Resolver<Movie> {
    private IMDBGetRequest _request = null;
    private MovieRepository _repository = null;

    public IMDBMovieResolver() {
        _request = new IMDBGetRequest();
        _repository = new MovieRepository();
    }

    public Movie resolve(Object data) throws ClassCastException {
        MovieMD movieMD = MovieMD.class.cast(data);
        Movie result = null;
        Movie searchedMovie = _repository.getMovieByDbId(movieMD.getDbType(), movieMD.getMovieId());
        System.out.println("Parsing movie - " + movieMD.getTitle());

        // check if the title already exists in the database and if so do not resolve the object
        if (searchedMovie != null) {
            System.out.println("Movie already exists");

            result = searchedMovie;
        } else {
            _request.resetRequest();
            _request.addParameter(IMDBRequestParameterType.TITLE, movieMD.getTitle());
            IMDBMovieJsonConvertor jsonNewConvertor = null;

            try {
                String response = _request.sendRequest();

                // Execute the service - convert json response to a movie object
                //jsonConvertor = new MovieJsonConvertor();
                jsonNewConvertor = new IMDBMovieJsonConvertor();
                IMDBMovieObject oldMovie = jsonNewConvertor.getJsonJavaObject(response);
                Movie responseMov = convertToNeoMovie(oldMovie);
                searchedMovie = _repository.getMovieByTitle(responseMov.getTitle());

                // Sanity check if the resolved data is already in the database
                if (searchedMovie == null) {
                    System.out.println("Movie does not exists");
                    result = responseMov;
                } else {
                    System.out.println("Movie already exists but we had to resolve its title by IMDB service");

                    result = searchedMovie;
                }
            } catch (Exception e) {
                System.out.println("Error occurred while trying to find - " + movieMD.getTitle());
            }
        }

        if (result != null) {
            switch (movieMD.getDbType()) {
                case MovieLens: {
                    result.setMovieLensMovieId(movieMD.getMovieId());
                    break;
                }
                case Netflix: {
                    result.setNetflixMovieId(movieMD.getMovieId());
                    break;
                }
            }
        }

        return result;
    }

	private Movie convertToNeoMovie(IMDBMovieObject oldMovie) 
	{
		if(oldMovie == null)
		{
			return null;
		}
		
		
		Movie movieResult = new Movie();
		
		movieResult.setActors(createListFromString(oldMovie.getActors()));
		movieResult.setCountry(createListFromString(oldMovie.getCountry()));
		movieResult.setAwards(createListFromString(oldMovie.getAwards()));
		movieResult.setDirector(createListFromString(oldMovie.getDirector()));
		movieResult.setGenre(createListFromString(oldMovie.getGenre()));
		movieResult.setImdbID(oldMovie.getImdbID());
		movieResult.setImdbRating(oldMovie.getImdbRating());
		movieResult.setImdbVotes(oldMovie.getImdbVotes());
		movieResult.setLanguage(createListFromString(oldMovie.getLanguage()));
		movieResult.setMetascore(oldMovie.getMetascore());
		movieResult.setPlot(oldMovie.getPlot());
		movieResult.setPoster(oldMovie.getPoster());
		movieResult.setRated(oldMovie.getRated());
		movieResult.setReleased(oldMovie.getReleased());
		movieResult.setResponse(oldMovie.getResponse());
		movieResult.setRuntime(oldMovie.getRuntime());
		movieResult.setTitle(oldMovie.getTitle());
		movieResult.setType(oldMovie.getType());
		movieResult.setWriter(createListFromString(oldMovie.getWriter()));
		movieResult.setYear(oldMovie.getYear());
		
		return movieResult;
	}

	private ArrayList<String> createListFromString(String actors) 
	{
		String[] listOfElements = actors.split(",");
		ArrayList<String> resultListOfElements = new ArrayList<>();
		
		for (String currElement : listOfElements) 
		{
			if(currElement.equals(""))
			{
				continue;
			}
			
			resultListOfElements.add(currElement.trim());
		}
		
		return resultListOfElements;
	}

}
