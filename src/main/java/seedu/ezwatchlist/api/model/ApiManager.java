package seedu.ezwatchlist.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.tools.MovieDbException;
import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.util.ApiUtil;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Main class for the API to connect to the server
 */
public class ApiManager extends ApiUtil implements ApiInterface {
    //API key is to connect with the TMDB server.
    private static final String API_KEY = "44ed1d7975d7c699743229199b1fc26e";
    private static final String CONNECTION_ERROR_MESSAGE = "Looks like you're not connected to the internet";
    private TmdbApi apiCall;

    /**
     * Constructor for ApiMain object used to interact with the API.
     *
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public ApiManager() throws OnlineConnectionException {
        try {
            apiCall = new TmdbApi(API_KEY);
        } catch (MovieDbException e) {
            //when not connected to the internet
            notConnected();
        }
    }

    /**
     * Checks if the API is connected to the internet.
     *
     * @return true if connected to the API.
     */
    public static boolean isConnected() {
        try {
            new TmdbApi(API_KEY);
        } catch (MovieDbException e) {
            return false;
        }
        return true;
    }

    /**
     * Helper function to call when not connected to the API.
     *
     * @throws OnlineConnectionException when the method is called with an error message.
     */
    public static void notConnected() throws OnlineConnectionException {
        throw new OnlineConnectionException(CONNECTION_ERROR_MESSAGE);
    }

    /**
     * Retrieves a list of upcoming movies in the API.
     *
     * @return a list of movies that are upcoming from the API.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<Movie> getUpcomingMovies() throws OnlineConnectionException {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            MovieResultsPage upcoming = apiCall.getMovies().getUpcoming(null, null, null);
            ApiUtil.extractMovies(movies, upcoming, apiCall);

            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return movies;
        }
    }

    /**
     * Retrieves the movies from the API by the string given.
     *
     * @param name the name of the movie that the user wants to search.
     * @return a list of movies that are returned from the API search call.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<Movie> getMovieByName(String name) throws OnlineConnectionException {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            MovieResultsPage page = apiCall.getSearch().searchMovie(name,
                    null, null, false, 1);


            ApiUtil.extractMovies(movies, page, apiCall);

            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return movies;
        }
    }

    /**
     * Retrieves a list of recommended Movies based on the list of Movies the user has.
     * @param userMovies list of Movies that belongs to the user.
     * @param noOfRecommendations Number of recommendations returned. The method will attempt to reach that number,
     *                             it will act as an upper limit to the amount of Movies returned.
     * @return list of Movies that are recommended to the user.
     * @throws OnlineConnectionException when not connected to the internet.
     * @throws NoRecommendationsException when no recommendations can be generated.
     */
    public List<Movie> getMovieRecommendations(List<Movie> userMovies, int noOfRecommendations)
            throws OnlineConnectionException, NoRecommendationsException {
        try {
            RecommendationEngine recommendation = new RecommendationEngine(userMovies, null, apiCall);
            return recommendation.getMovieRecommendations(noOfRecommendations);
        } catch (MovieDbException e) {
            notConnected();
            return null;
        }
    }

    /**
     * Retrieves a list of recommended Tv Shows based on the list of Tv Shows the user has.
     * @param userTvShows list of Tv Shows that belongs to the user.
     * @param noOfRecommendations Number of recommendations returned. The method will attempt to reach that number,
     *                             it will act as an upper limit to the amount of Tv Shows returned.
     * @return list of Tv Shows that are recommended to the user.
     * @throws OnlineConnectionException when not connected to the internet.
     * @throws NoRecommendationsException when no recommendations can be generated.
     */
    public List<TvShow> getTvShowRecommendations(List<TvShow> userTvShows, int noOfRecommendations)
            throws OnlineConnectionException, NoRecommendationsException {
        try {
            RecommendationEngine recommendation = new RecommendationEngine(null, userTvShows, apiCall);
            return recommendation.getTvShowRecommendations(noOfRecommendations);
        } catch (MovieDbException e) {
            notConnected();
            return null;
        }
    }

    /**
     * Retrieves the tv shows from the API by the string given.
     *
     * @param name the name of the tv show that the user wants to search.
     * @return a list of tv shows that are returned from the API search call.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<TvShow> getTvShowByName(String name) throws OnlineConnectionException {
        ArrayList<TvShow> tvShows = new ArrayList<>();

        try {
            TvResultsPage page = apiCall.getSearch().searchTv(name, null, 1);

            ApiUtil.extractTvShows(tvShows, page, apiCall);
            return tvShows;
        } catch (MovieDbException e) {
            notConnected();
            return tvShows;
        }
    }

    /*public List<TvShow> getTvShowByGenre(Set<Genre> genreSet) throws OnlineConnectionException {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        try{
            List<info.movito.themoviedbapi.model.Genre> genreList = apiCall.getGenre().getGenreList(null);
            for (Genre genreSearched : genreSet) {
                for (info.movito.themoviedbapi.model.Genre genreApi : genreList) {
                    if (genreApi.getName().toLowerCase().contains(genreSearched.getGenreName().toLowerCase())) {
                        int genreID = genreApi.getId();
                        apiCall.getGenre().getGenreMovies()
                        Discover discover = new Discover();
                        discover.includeAdult(false).withGenres(genreID);
                        MovieResultsPage tvPage = apiCall.getDiscover().getDiscover(discover);
                        ApiUtil.extractTvShows(tvShows, tvPage, apiCall);
                    }
                }
            }
            return tvShows;
        } catch (MovieDbException e) {
            notConnected();
            return tvShows;
        }
    }

    */

    /**
     * Returns a list of movies from the API search method.
     *
     * @param genreSet the set of genres that the user wants to search.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<Movie> getMovieByGenre(Set<Genre> genreSet) throws OnlineConnectionException {
        ArrayList<Movie> movies = new ArrayList<>();
        try {

            List<info.movito.themoviedbapi.model.Genre> genreList = apiCall.getGenre().getGenreList(null);
            for (Genre genreSearched : genreSet) {
                for (info.movito.themoviedbapi.model.Genre genreApi : genreList) {
                    if (genreApi.getName().toLowerCase().contains(genreSearched.getGenreName().toLowerCase())) {
                        int genreId = genreApi.getId();
                        MovieResultsPage moviePage = apiCall.getGenre().getGenreMovies(genreId, null, 1,
                                true);
                        ApiUtil.extractMovies(movies, moviePage, apiCall);
                    }
                }
            }

            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return movies;
        }
    }
}
