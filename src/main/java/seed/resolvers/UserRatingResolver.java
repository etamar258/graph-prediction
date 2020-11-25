package seed.resolvers;

import core.entities.DbType;
import core.entities.Movie;
import core.entities.Rating;
import core.entities.User;
import core.repositories.MovieRepository;
import core.repositories.UserRepository;
import seed.raw.csv.movielens.MovieLensUserRatingObject;

public class UserRatingResolver implements Resolver<User> {
    private UserRepository _userRatingRepository = null;
    private MovieRepository _movieRepository = null;

    public UserRatingResolver() {
        _userRatingRepository = new UserRepository();
        _movieRepository = new MovieRepository();
    }

    public User resolve(Object data) throws ClassCastException {
        User result = null;

        try {
            MovieLensUserRatingObject obj = MovieLensUserRatingObject.class.cast(data);

            // check if user already exists
            User usrFromDb = this._userRatingRepository.getUserByDbID(DbType.MovieLens, obj.getUserId());
//            Movie movFromDb = _movieRepository.getMovieByDbId(DbType.MovieLens, obj.movieId);
            Movie movFromDb = _movieRepository.getMovieByTitle(obj.getMovieTitle());
            Rating nRating = new Rating();
            nRating.setStars(obj.getRating());
            nRating.setTimestamp(obj.getTimestamp());

            if (movFromDb == null) {
                result = null;
            } else {
                if (usrFromDb == null) {
                    // User does not exists
                    result = new User();
                    result.setUserType(DbType.MovieLens);
                    result.setUserDbId(obj.getUserId());
                } else {
                    result = usrFromDb;
                }

                // Add relationship
                nRating.setUser(result);
                nRating.setMovie(movFromDb);

                // Check if rating from user to movie already exists
                result.addRating(nRating);
            }
        } catch (ClassCastException ex) {
            throw ex;
        }

        return result;
    }

}
