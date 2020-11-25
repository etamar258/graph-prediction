package seed;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import core.MainProperties;
import core.entities.Movie;
import core.entities.User;
import core.repositories.MovieRepository;
import core.repositories.UserRepository;
import seed.raw.csv.movielens.MovieLensMovieObject;
import seed.raw.csv.movielens.MovieLensUserRatingObject;
import seed.raw.csv.netflix.NetflixMovieObject;
import seed.resolvers.IMDBMovieResolver;
import seed.resolvers.UserRatingResolver;

import java.io.*;
import java.util.Properties;

public class CsvSeeder {
    private final char DELIMITER = ',';
    private CsvSchema _bootstrapSchema;
    private CsvMapper _mapper = new CsvMapper();

    // TODO: add connection
    public CsvSeeder() {
        // Read schema from the first line; start with bootstrap instance
        // to enable reading of schema from the first line
        _bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(DELIMITER);
    }

    public void write(String movielensMoviesCsv, String netflixMoviesCsv, String movielensUserRatingsCsv) throws FileNotFoundException, IOException {
        // Seed Objects into Neo4j database
        Seeder<Movie> csvSeeder = new Seeder<Movie>(_mapper, _bootstrapSchema, new MovieRepository());
        Seeder<User> csvUserSeeder = new Seeder<User>(_mapper, _bootstrapSchema, new UserRepository());

        // Create streams to read from
        FileInputStream movielensMoviesStream = new FileInputStream(new File(movielensMoviesCsv));
        FileInputStream netflixMoviesStream = new FileInputStream(new File(netflixMoviesCsv));
        //FileInputStream movielensUserRatingStream = new FileInputStream(new File(movielensUserRatingsCsv));

        // Create resolvers to convert from one data structure to the wanted data
        IMDBMovieResolver imdbResolver = new IMDBMovieResolver();
        UserRatingResolver userRatingResolver = new UserRatingResolver();

        // Run seeds
        Properties prop = new Properties();
        InputStream input = MainProperties.class.getClassLoader().getResourceAsStream("seeder.properties");

        // load a properties file
        prop.load(input);

        if (Boolean.parseBoolean(prop.getProperty("db.raw.seed.movies")) == true) {
            csvSeeder.seed(movielensMoviesStream, MovieLensMovieObject.class, imdbResolver);
            csvSeeder.seed(netflixMoviesStream, NetflixMovieObject.class, imdbResolver);
        }

        /*if (Boolean.parseBoolean(prop.getProperty("db.raw.seed.ratings")) == true) {
            csvUserSeeder.seed(movielensUserRatingStream, MovieLensUserRatingObject.class, userRatingResolver);
        }*/
    }
}
