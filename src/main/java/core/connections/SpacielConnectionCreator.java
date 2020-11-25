package core.connections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.model.Result;

import core.algorithms.CSVSpecialConnectionFinder;
import core.entities.EntityQueryProbability;
import core.entities.Movie;
import core.entities.MovieSpacielConnection;
import core.entities.SpecialConnectionElement;
import core.repositories.MovieRepository;
import core.repositories.MovieSpacielConnectionRepository;

public class SpacielConnectionCreator {
	private static final String GENERE_ARRAY_A = "movie1.genre[0]";
	private static final String GENERE_ARRAY_B = "movie2.genre[0]";
	private static final String ACTORS_ARRAY_A = "movie1.actors[0]";
	private static final String ACTORS_ARRAY_B = "movie2.actors[0]";
	private static final String WRITER_ARRAY_A = "movie1.writer[0]";
	private static final String WRITER_ARRAY_B = "movie2.writer[0]";
	private static final String DIRECTOR_ARRAY_A = "movie1.director[0]";
	private static final String DIRECTOR_ARRAY_B = "movie2.director[0]";
	private static final String MOVIE_TITLE_A = "<MOVIE_TITLE_A>";
	private static final String MOVIE_PROB = "<MOVIE_PROB>";
	private static final String CONNECT_MOVIE_QUERY = "MATCH (movie1:Movie), (movie2:Movie) WHERE movie1.title <> movie2.title and movie1.title=\""
			+ MOVIE_TITLE_A + "\"";
	private static final String CREATE_CONNECT = " CREATE (movie1)-[r:SPACIEL { prob: " + MOVIE_PROB
			+ " }]->(movie2) RETURN r";

	private MovieSpacielConnectionRepository dbRepository = new MovieSpacielConnectionRepository();

	public void createSpacielConnection(String jsonConnectionFile) {
		CSVSpecialConnectionFinder connectionFinder = new CSVSpecialConnectionFinder();
		Set<SpecialConnectionElement> allSpacielConnections = connectionFinder
				.GetAllTheSpacielConnection(jsonConnectionFile);

		for (SpecialConnectionElement currSpecialConnectionElement : allSpacielConnections) {
			if (!checkIfNeedDecentralization(currSpecialConnectionElement)) {
				Map<String, String> parameters = new HashMap<>();
				String queryToExecute = createQuery(currSpecialConnectionElement, parameters);

				createConnections(parameters, queryToExecute);
			} else {
				EntityQueryProbability currQuery = new EntityQueryProbability(
						createStringQuery(currSpecialConnectionElement.getGenList()),
						currSpecialConnectionElement.getProbability() * 1000,
						createGenArray(currSpecialConnectionElement.getGenList()));
				createSpacielConnectionByQuery(currQuery);
			}
		}

	}

	private static String[] createGenArray(Set<String> genList) {
		String[] resultGenreArray = new String[genList.size()];
		int currIndex = 0;

		for (String currGen : genList) {
			resultGenreArray[currIndex] = currGen;
			currIndex++;
		}

		return resultGenreArray;
	}

	private static String createStringQuery(Set<String> genList) {
		String queryToExecute = "MATCH (movie1:Movie) WHERE ";
		int currIndex = 1;
		int maxNumOfGen = genList.size();

		for (String currGen : genList) {
			queryToExecute += GENERE_ARRAY_A + " CONTAINS '" + currGen + "'";

			if (currIndex < maxNumOfGen) {
				queryToExecute += " and ";
			}

			currIndex++;
		}

		return queryToExecute + " RETURN movie1";
	}

	private boolean checkIfNeedDecentralization(SpecialConnectionElement currSpecialConnectionElement) {
		return currSpecialConnectionElement.getActorsList().size() == 0
				&& currSpecialConnectionElement.getDirectorsList().size() == 0
				&& currSpecialConnectionElement.getWriterList().size() == 0
				&& currSpecialConnectionElement.getGenList().size() < 3;
	}

	private void createConnections(Map<String, String> parameters, String queryToExecute) {
		Result executeQueryResult = dbRepository.executeQuery(queryToExecute, parameters);
		System.out.println("Get " + executeQueryResult.queryStatistics().getRelationshipsCreated() + "connections");
	}

	public void createSpacielConnectionByQuery(EntityQueryProbability currElements) {
		Iterable<MovieSpacielConnection> queryResult = dbRepository.executeQueryWitoutParameters(currElements.getEntityQuery());
		createMovieConnections(queryResult, currElements.getProbability(), currElements.getAllGenere());
	}

	private void createMovieConnections(Iterable<MovieSpacielConnection> queryResult, double connectionProbability, String[] genreList) {

		String queryToExecute = createGenreListQuery(genreList);

		for (MovieSpacielConnection currMovie : queryResult) {
			String finalQueryToExecute = queryToExecute.replace(MOVIE_TITLE_A, currMovie.getTitle()).replace(MOVIE_PROB,
					connectionProbability + "");
			createConnections(new HashMap<String, String>(), finalQueryToExecute);
		}

	}

	public String createGenreListQuery(String[] genreList) {
		String resultQuery = CONNECT_MOVIE_QUERY;

		for (String currGen : genreList) {
			resultQuery += " and " + GENERE_ARRAY_B + " CONTAINS '" + currGen + "'";
		}

		return resultQuery + CREATE_CONNECT;
	}

	private String createQuery(SpecialConnectionElement currSpecialConnectionElement, Map<String, String> parameters) {
		String resultQuery = "MATCH (movie1:Movie), (movie2:Movie) WHERE movie1.title <> movie2.title";

		for (String currGenre : currSpecialConnectionElement.getGenList()) {

			resultQuery += " and " + GENERE_ARRAY_A + " CONTAINS '" + currGenre + "' and " + GENERE_ARRAY_B
					+ " CONTAINS '" + currGenre + "'";
		}

		for (String currActor : currSpecialConnectionElement.getActorsList()) {
			resultQuery += " and " + ACTORS_ARRAY_A + " CONTAINS '" + currActor + "' and " + ACTORS_ARRAY_B
					+ " CONTAINS '" + currActor + "'";
		}

		for (String currDriector : currSpecialConnectionElement.getDirectorsList()) {
			resultQuery += " and " + DIRECTOR_ARRAY_A + " CONTAINS '" + currDriector + "' and " + DIRECTOR_ARRAY_B
					+ " CONTAINS '" + currDriector + "'";
		}

		for (String currWriter : currSpecialConnectionElement.getDirectorsList()) {
			resultQuery += " and " + WRITER_ARRAY_A + " CONTAINS '" + currWriter + "' and " + WRITER_ARRAY_B
					+ " CONTAINS '" + currWriter + "'";
		}

		resultQuery += " CREATE (movie1)-[r:SPACIEL { prob: " + currSpecialConnectionElement.getProbability() * 1000
				+ " }]->(movie2) RETURN r";

		return resultQuery;
	}

	public static void main(String[] args) {

		Set<String> genList = new HashSet<>();
		genList.add("Drama");
		genList.add("Romance");

		EntityQueryProbability currQuery = new EntityQueryProbability(createStringQuery(genList), 0.0372 * 1000,
				createGenArray(genList));
		SpacielConnectionCreator c = new SpacielConnectionCreator();

		c.createSpacielConnectionByQuery(currQuery);

		// SpacielConnectionCreator a = new SpacielConnectionCreator();
		//
		// a.createSpacielConnection("result.txt");

		// String[] genreList = {"Action", "Sci-Fi", "Thriller"};
		//
		// EntityQueryProbability[] allConnection = {new
		// EntityQueryProbability("MATCH (movie1:Movie) WHERE 'Action' in
		// split(movie1.genre[0],', ') and 'Sci-Fi' in split(movie1.genre[0],',
		// ') and 'Thriller' in split(movie1.genre[0],', ') RETURN movie1",
		// 15.25,
		// genreList)};
		// a.createSpacielConnectionByQuery(allConnection);
	}
}
