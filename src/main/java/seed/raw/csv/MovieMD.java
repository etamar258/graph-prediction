package seed.raw.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.entities.DbType;

public abstract class MovieMD {
    protected String title;

    @JsonProperty("movieId")
    protected long movieId;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public abstract void setTitle(String title);

    public abstract DbType getDbType();
}
