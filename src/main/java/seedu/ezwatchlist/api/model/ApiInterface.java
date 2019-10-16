package seedu.ezwatchlist.api.model;

import java.util.List;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Interface to retrieve information from online API.
 * Methods used here will return the information for movies and tv shows.
 */
public interface ApiInterface {

    /**
     * Returns a list of movies from the API search method.
     *
     * @param name the name of the movie that the user wants to search.
     * @exception OnlineConnectionException when the user is not connected to the internet.
     */
    public List<Movie> getMovieByName(String name) throws OnlineConnectionException;

    /**
     * Returns a list of tv shows from the API search method.
     *
     * @param name the name of the tv show that the user wants to search.
     * @exception OnlineConnectionException when the user is not connected to the internet.
     */
    public List<TvShow> getTvShowByName(String name) throws OnlineConnectionException;

    /**
     * Checks if the api is connected to the internet.
     */
    public boolean isApiConnected();
}
