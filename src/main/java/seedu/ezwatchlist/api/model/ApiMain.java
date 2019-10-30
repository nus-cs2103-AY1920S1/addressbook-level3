package seedu.ezwatchlist.api.model;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.tools.MovieDbException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.util.ApiMainUtil;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Main class for the API to connect to the server
 */
public class ApiMain implements ApiInterface {
    //API key is to connect with the TMDB server.
    private static final String API_KEY = "44ed1d7975d7c699743229199b1fc26e";
    private static final String CONNECTION_ERROR_MESSAGE = "Looks like you're not connected to the internet";
    private TmdbApi apiCall;
    private boolean isConnected = false;

    /**
     * Constructor for ApiMain object used to interact with the API.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public ApiMain() throws OnlineConnectionException {
        try {
            apiCall = new TmdbApi(API_KEY);
            isConnected = true;
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
    public boolean isConnected() {
        try {
            apiCall = new TmdbApi(API_KEY);
            isConnected = true;
        } catch (MovieDbException e) {
            isConnected = false;
        } finally {
            return isConnected;
        }
    }

    /**
     * Helper function to call when not connected to the API.
     *
     * @throws OnlineConnectionException when the method is called with an error message.
     */
    private void notConnected() throws OnlineConnectionException {
        throw new OnlineConnectionException(CONNECTION_ERROR_MESSAGE);
    }

    /**
     * Retrieves a list of upcoming movies in the API.
     *
     * @return a list of movies that are upcoming from the API.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    public List<Movie> getUpcomingMovies() throws OnlineConnectionException {
        try {
            MovieResultsPage upcoming = apiCall.getMovies().getUpcoming(null, null, null);
            ArrayList<Movie> movies = new ArrayList<>();
            ApiMainUtil.extractMovies(movies, upcoming, apiCall);

            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return new ArrayList<Movie>();
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
                    null, null, true, 1);

            ApiMainUtil.extractMovies(movies, page, apiCall);

            return movies;
        } catch (MovieDbException e) {
            notConnected();
            return movies;
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
            TmdbTV apiCallTvSeries = apiCall.getTvSeries();

            ApiMainUtil.extractTvShow(tvShows, page, apiCallTvSeries, apiCall);
            return tvShows;
        } catch (MovieDbException e) {
            notConnected();
            return tvShows;
        }
    }
}
