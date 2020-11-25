import core.MainProperties;
import core.exceptions.DBQueryCreationException;
import seed.CsvSeeder;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

public class Main {

    public static void main(String[] args) throws DBQueryCreationException 
    {
        /*UserRepository ur = new UserRepository();
        Iterable<User> allUsers1 = ur.findAll();
        
        for (User currUserSimilarty : allUsers1)
        {
        	if(currUserSimilarty.getSimilatryUsers().size() > 0)
        	{
        		System.out.println(currUserSimilarty.getId());
        	}
		}
        */
    	
    	BasicConfigurator.configure();
        
        try {
        	Properties prop = new Properties();
        	InputStream  input = MainProperties.class.getClassLoader().getResourceAsStream("seeder.properties");
        	CsvSeeder csvSeeder = new CsvSeeder();

            // load a properties file
            prop.load(input);

            String movielensMoviePath = prop.getProperty("db.raw.movielens.movies.csv.path");
            String netflixMoviePath = prop.getProperty("db.raw.netflix.movies.csv.path");
           // String movielensRatingPath = prop.getProperty("db.raw.movielens.ratings.csv.path");

            if (input == null) {
                System.out.println("Sorry, unable to find seeder.properties");
                return;
            }

            // Write csv data into database
            csvSeeder.write(movielensMoviePath, netflixMoviePath, null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }/**/
        
//        MovieRepository rep = new MovieRepository();
//        Map<String, String> parameters = new HashMap<String, String>();
//        parameters.put("year", "1999");
//        TFIDFCalculator calc = new TFIDFCalculator();
//        //Toy Story 2
//        Iterable<Movie> allMovies = rep.findDBObject(parameters, rep.getObjectName());
//        Iterator<Movie> allMoviester = allMovies.iterator();
//        
//        Map<String, String> parameters1 = new HashMap<String, String>();
//        parameters1.put("title", "Toy Story 2");
//        
//        Movie firstMovie = rep.getFirstObject(parameters1, rep.getObjectName());
//        String[] firstMovieWords = firstMovie.getPlot().split(" ");
//        List<List<String>> documentsToCalc = new ArrayList<List<String>>();
//        List<String> firstMovieDoc = Arrays.asList(firstMovieWords);
//        documentsToCalc.add(firstMovieDoc);
        /*while(allMoviester.hasNext())
        {
        	Movie currMovie = allMoviester.next();
        	String[] curMoviesWordes = firstMovie.getPlot().split(" ");
        	documentsToCalc.add(Arrays.asList(curMoviesWordes));
        	
        	List<String> firstMovieDoc = Arrays.asList(firstMovieWords);
            double result = 0;
            
            for (String currWord : firstMovieWords) 
            {
            	double grade = calc.tfIdf(firstMovieDoc, documentsToCalc, currWord);
            	
            	if(grade > 0)
            		System.out.println("For the term: " + currWord + " the result: " + grade);
    		}
            
            documentsToCalc.clear();
        }*/
        
        //double a = calc.tfIdf(firstMovieDoc, documentsToCalc, "Woody");
        
        //System.out.println("The result: " + a);
    }
}
