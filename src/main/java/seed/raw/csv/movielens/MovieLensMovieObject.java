package seed.raw.csv.movielens;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import core.entities.DbType;
import seed.raw.csv.MovieMD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties()
public class MovieLensMovieObject extends MovieMD {
    final String GENRES_DELIMITER = "[|]";
    private String[] genres;
    private String year;

    public String getTitle() {
        return title;
    }

    @Override
    @JsonProperty("title")
    public void setTitle(String title) {
        String[] splittedTitle = title.split("[ ][(]\\d{4}[)]$");
        Pattern yearPattern = Pattern.compile("[(](\\d{4})[)]");
        Matcher matcher = yearPattern.matcher(title);
        boolean found = matcher.find();
        this.title = seed.Util.escapeTitle(splittedTitle[0]);
        if (found) {
            this.year = matcher.group(1);
        } else {
            this.year = "0000";
        }
    }

    @Override
    public DbType getDbType() {
        return DbType.MovieLens;
    }

    public String[] getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(String genres) {
        this.genres = genres.split(GENRES_DELIMITER);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
