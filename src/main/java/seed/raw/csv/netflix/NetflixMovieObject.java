package seed.raw.csv.netflix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import core.entities.DbType;
import seed.raw.csv.MovieMD;

@JsonIgnoreProperties()
public class NetflixMovieObject extends MovieMD {
    @JsonProperty("year")
    private String year;

    @Override
    public DbType getDbType() {
        return DbType.Netflix;
    }

    @Override
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = seed.Util.escapeTitle(title);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
