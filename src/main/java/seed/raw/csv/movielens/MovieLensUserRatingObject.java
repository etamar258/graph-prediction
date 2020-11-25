package seed.raw.csv.movielens;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.entities.DbType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a record in a csv file that has user ratings
 * That file was changed to replace movie id to movie title
 */
public class MovieLensUserRatingObject {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("movieTitle")
    private String movieTitle;

    @JsonProperty("rating")
    private float rating;

    @JsonProperty("timestamp")
    private long timestamp;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    @JsonProperty("movieTitle")
    public void setMovieTitle(String title) {
        String[] splittedTitle = title.split("[ ][(]\\d{4}[)]$");
        Pattern yearPattern = Pattern.compile("[(](\\d{4})[)]");
        Matcher matcher = yearPattern.matcher(title);
        this.movieTitle = seed.Util.escapeTitle(splittedTitle[0]);
        boolean found = matcher.find();
    }

    public DbType getDbType() {
        return DbType.MovieLens;
    }
}
