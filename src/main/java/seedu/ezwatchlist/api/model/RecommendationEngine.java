package seedu.ezwatchlist.api.model;

import static java.util.Map.Entry.comparingByValue;
import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import info.movito.themoviedbapi.tools.MovieDbException;
import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.util.ApiUtil;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Class used to generate recommendations based on list that the user has. The recommendations are sorted based on the
 * likelihood that the recommendations are more suited to the user, with the best recommendation being the first entry
 * in the list.
 */
public class RecommendationEngine {
    private List<Movie> userMovies;
    private List<TvShow> userTvShows;
    private TmdbApi tmdbApi;
    private HashMap<Integer, Integer> movieRecommendationOccurrences;
    private HashMap<Integer, Integer> tvRecommendationOccurrences;
    private List<Movie> movieRecommendations;
    private List<TvShow> tvShowRecommendations;

    /**
     * Creates an instance of RecommendationEngine used to generate recommendations for the user.
     * @param movies @nullable the list of Movies the user has.
     * @param tvShows @nullable the list of Tv Shows the user has.
     * @param tmdbApi the Api call to retrieve online information.
     */
    public RecommendationEngine(List<Movie> movies, List<TvShow> tvShows, TmdbApi tmdbApi) {
        userMovies = movies;
        userTvShows = tvShows;
        this.tmdbApi = tmdbApi;
        movieRecommendationOccurrences = new HashMap<>();
        tvRecommendationOccurrences = new HashMap<>();
        movieRecommendations = new LinkedList<>();
        tvShowRecommendations = new LinkedList<>();
    }

    /**
     * Generates Movie recommendations, returning a sorted list of recommendations.
     * @param noOfRecommendations the upper limit of recommendations returned.
     * @return Sorted list of recommendations, with the best recommendation in the first entry.
     * @throws NoRecommendationsException thrown when unable to generate recommendations.
     * @throws OnlineConnectionException thrown when not connected to the internet.
     */
    public List<Movie> getMovieRecommendations(int noOfRecommendations)
            throws NoRecommendationsException, OnlineConnectionException {
        if (movieRecommendations.size() != noOfRecommendations) {
            validForRecommendations(isNull(userMovies), "Movies from the user is null, unable to");
            validForRecommendations(userMovies.isEmpty(), "No movies from the user to");

            parseUserMovies();
            sortMovieRecommendations(noOfRecommendations);

            validForRecommendations(movieRecommendationOccurrences.isEmpty(), "Unable to");
        }

        return movieRecommendations;
    }

    /**
     * Helper method to check if recommendations are valid.
     * @param check the boolean check.
     * @param prefix the prefix for the error message.
     * @throws NoRecommendationsException thrown if invalid.
     */
    private void validForRecommendations(boolean check, String prefix) throws NoRecommendationsException {
        if (check) {
            throw new NoRecommendationsException(prefix
                    + " generate recommendations");
        }
    }

    /**
     * Generates TV Shows recommendations, returning a sorted list of recommendations.
     * @param noOfRecommendations the upper limit of recommendations returned.
     * @return Sorted list of recommendations, with the best recommendation in the first entry.
     * @throws NoRecommendationsException thrown when unable to generate recommendations.
     * @throws OnlineConnectionException thrown when not connected to the internet.
     */
    public List<TvShow> getTvShowRecommendations(int noOfRecommendations)
            throws NoRecommendationsException, OnlineConnectionException {
        if (tvShowRecommendations.size() != noOfRecommendations) {
            validForRecommendations(isNull(userTvShows), "TvShows from the user is null, unable to");
            validForRecommendations(userTvShows.isEmpty(), "No TvShows from the user to");

            parseUserTvShows();
            sortTvShowRecommendations(noOfRecommendations);

            validForRecommendations(tvRecommendationOccurrences.isEmpty(), "Unable to");
        }

        return tvShowRecommendations;
    }

    /**
     * Sorts and adds the recommendations to the list of recommendations.
     * @param noOfRecommendations the upper limit of recommendations.
     */
    private void sortMovieRecommendations(int noOfRecommendations) {
        movieRecommendationOccurrences.entrySet().stream()
                .sorted(comparingByValue())
                .limit(noOfRecommendations)
                .forEachOrdered(x -> movieRecommendations.add(getMovie(x.getKey())));
    }

    /**
     * Sorts and adds the recommendations to the list of recommendations.
     * @param noOfRecommendations the upper limit of recommendations.
     */
    private void sortTvShowRecommendations(int noOfRecommendations) {
        tvRecommendationOccurrences.entrySet().stream()
                .sorted(comparingByValue())
                .limit(noOfRecommendations)
                .forEachOrdered(x -> tvShowRecommendations.add(getTvShow(x.getKey())));
    }

    /**
     * Retrieves a Movie from it's ID.
     * @param movieId the ID of the Movie.
     * @return Movie
     */
    private Movie getMovie(Integer movieId) {
        try {
            MovieDb movie = tmdbApi.getMovies().getMovie(movieId, null, TmdbMovies.MovieMethod.values());
            return ApiUtil.extractMovie(tmdbApi, movie);
        } catch (OnlineConnectionException e) {
            return null;
        }
    }

    /**
     * Retrieves a Tv Show from it's ID
     * @param tvId the ID of the Tv Show
     * @return TvShow
     */
    private TvShow getTvShow(Integer tvId) {
        try {
            TvSeries tvSeries = tmdbApi.getTvSeries().getSeries(tvId, null, TmdbTV.TvMethod.values());
            return ApiUtil.extractTvShow(tmdbApi, tvSeries);
        } catch (OnlineConnectionException e) {
            return null;
        }
    }

    /**
     * Parses the user's Tv Shows to get it's respective entry in the online database. A recommendation is then
     * retrieved and added to the map of recommendations based on the number of occurrences.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void parseUserTvShows() throws OnlineConnectionException {
        try {
            for (TvShow tvShow : userTvShows) {
                String tvShowName = tvShow.getName().showName;

                if (isInvalidName(tvShowName)) {
                    continue;
                }

                TvResultsPage tvDbs = tmdbApi.getSearch().searchTv(tvShowName, null, 1);
                int tvId = tvDbs.getResults().get(0).getId(); //retrieves the first Tv Show that matches the name.

                TvSeries series = tmdbApi.getTvSeries().getSeries(tvId, null, TmdbTV.TvMethod.recommendations);
                ResultsPage<TvSeries> recommendations = series.getRecommendations();
                List<TvSeries> results = recommendations.getResults();
                results.forEach((tvSeries) -> addToRecommendations(tvSeries.getId(), false));
            }
        } catch (MovieDbException e) {
            ApiMain.notConnected();
        }
    }

    /**
     * Checks if the name is invalid.
     * @param showName the Name to be checked.
     * @return true if invalid.
     */
    private boolean isInvalidName(String showName) {
        return showName.equals(Name.DEFAULT_NAME);
    }

    /**
     * Parses the user's Movies to get it's respective entry in the online database. A recommendation is then
     * retrieved and added to the map of recommendations based on the number of occurrences.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void parseUserMovies() throws OnlineConnectionException {
        try {
            for (Movie m : userMovies) {
                String movieName = m.getName().showName;

                if (isInvalidName(movieName)) {
                    continue;
                }

                MovieResultsPage movieDbs = tmdbApi.getSearch()
                        .searchMovie(movieName, null, null, true, 1);
                int movieId = movieDbs.getResults().get(0).getId(); //retrieves the first Movie that matches the name.

                MovieDb movieDb = tmdbApi.getMovies().getMovie(movieId, null, TmdbMovies.MovieMethod.similar);
                List<MovieDb> similarMovies = movieDb.getSimilarMovies();
                similarMovies.forEach((movie) -> addToRecommendations(movie.getId(), true));
            }
        } catch (MovieDbException e) {
            ApiMain.notConnected();
        }
    }

    /**
     * Adds an entry to the respective map. Increasing the occurrences if there is a duplicate.
     * @param id the id of the Show.
     * @param isMovie if the id belongs to a Movie.
     */
    private void addToRecommendations(int id, boolean isMovie) {
        HashMap<Integer, Integer> showOccurrences =
                isMovie ? movieRecommendationOccurrences : tvRecommendationOccurrences;

        if (showOccurrences.containsKey(id)) {
            Integer numberOfOccurrences = showOccurrences.remove(id);
            showOccurrences.put(id, numberOfOccurrences + 1);
        } else {
            showOccurrences.put(id, 1);
        }
    }
}
