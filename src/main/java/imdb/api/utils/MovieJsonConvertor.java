package imdb.api.utils;

import core.entities.Movie;

public class MovieJsonConvertor extends JsonConvertor<Movie> {
    @Override
    protected Class<Movie> getClassObject() {
        return Movie.class;
    }
}
