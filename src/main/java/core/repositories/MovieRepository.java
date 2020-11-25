package core.repositories;

import core.entities.DbType;
import core.entities.Movie;
import java.util.HashMap;
import java.util.Map;

public class MovieRepository extends Repository<Movie> {

    private static final String TITLE_PARAMETER = "title";

	public MovieRepository() {
    }

    public Movie getMovieByTitle(String title) 
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(TITLE_PARAMETER, title);

        return getFirstObject(parameters);
    }

    public Movie getMovieByDbId(DbType type, long dbId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("dbId", dbId);
        String query = "";

        if (dbId == Movie.INVALID_ID) {
            return null;
        } else {
            switch (type) {
                case MovieLens: {
                    query = "MATCH (m:Movie) WHERE EXISTS(m.movieLensMovieId) AND m.movieLensMovieId={dbId} RETURN (m)";
                    break;
                }
                case Netflix: {
                    query = "MATCH (m:Movie) WHERE EXISTS(m.netflixMovieId) AND m.netflixMovieId={dbId} RETURN (m)";
                    break;
                }
            }

            return this.getSession()
                    .queryForObject(getEntityType(), query, parameters);
        }
    }

    public Class<Movie> getEntityType() {
        return Movie.class;
    }

	@Override
	public String getObjectName() 
	{
		return "Movie";
	}
}
