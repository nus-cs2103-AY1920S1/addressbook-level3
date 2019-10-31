package seedu.ezwatchlist.api.model;

import java.util.List;
import java.util.Set;

import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Interface to retrieve information from online API.
 * Methods used here will return the information for Movies and Tv Shows.
 */
public interface ApiInterface {

    /**
     * Returns a list of Movies from the API search method.
     *
     * @param name the name of the Movie that the user wants to search.
     * @exception OnlineConnectionException when the user is not connected to the internet.
     */
    List<Movie> getMovieByName(String name) throws OnlineConnectionException;

    /**
     * Returns a list of Tv Shows from the API search method.
     *
     * @param name the name of the Tv Show that the user wants to search.
     * @exception OnlineConnectionException when the user is not connected to the internet.
     */
    List<TvShow> getTvShowByName(String name) throws OnlineConnectionException;

    /**
     * Retrieves a list of upcoming Movies in the API.
     *
     * @return a list of Movies that are upcoming from the API.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    List<Movie> getUpcomingMovies() throws OnlineConnectionException;

    /**
     * Retrieves a list of recommended Movies based on the list of Movies the user has.
     * @param userMovies list of Movies that belongs to the user.
     * @param noOfRecommendations Number of recommendations returned. The method will attempt to reach that number,
     *                             it will act as an upper limit to the amount of Movies returned.
     * @return list of Movies that are recommended to the user.
     * @throws OnlineConnectionException when not connected to the internet.
     * @throws NoRecommendationsException when no recommendations can be generated.
     */
    List<Movie> getMovieRecommendations(List<Movie> userMovies, int noOfRecommendations)
            throws OnlineConnectionException, NoRecommendationsException;

    /**
     * Retrieves a list of recommended Tv Shows based on the list of Tv Shows the user has.
     * @param userTvShows list of Tv Shows that belongs to the user.
     * @param noOfRecommendations Number of recommendations returned. The method will attempt to reach that number,
     *                             it will act as an upper limit to the amount of Tv Shows returned.
     * @return list of Tv Shows that are recommended to the user.
     * @throws OnlineConnectionException when not connected to the internet.
     * @throws NoRecommendationsException when no recommendations can be generated.
     */
    List<TvShow> getTvShowRecommendations(List<TvShow> userTvShows, int noOfRecommendations)
            throws OnlineConnectionException, NoRecommendationsException;

    /**
     * Returns a list of Tv Shows from the API search method.
     *
     * @param genreSet the set of genres that the user wants to search.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    List<TvShow> getTvShowByGenre(Set<Genre> genreSet) throws OnlineConnectionException;

    /**
     * Returns a list of movies from the API search method.
     *
     * @param genreSet the set of genres that the user wants to search.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    List<Movie> getMovieByGenre(Set<Genre> genreSet) throws OnlineConnectionException;

    /**
     * Checks if the api is connected to the internet.
     */
    boolean isConnected();
}
