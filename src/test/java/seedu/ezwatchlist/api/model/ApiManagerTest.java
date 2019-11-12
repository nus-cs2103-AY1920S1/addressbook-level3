package seedu.ezwatchlist.api.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.MovieDbException;
import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.model.show.Genre;

class ApiManagerTest {

    @Test
    void isConnected() throws OnlineConnectionException {
        assertTrue(new ApiManager().isConnected());
    }

    @Test
    void notConnected() {
        assertThrows(OnlineConnectionException.class, () -> new ApiManagerStub());
    }

    @Test
    void getUpcomingMovies() throws OnlineConnectionException {
        assertTrue(new ApiManager().getUpcomingMovies() instanceof ArrayList);
    }

    @Test
    void getMovieByName() throws OnlineConnectionException {
        assertTrue(new ApiManager().getMovieByName("test") instanceof ArrayList);
    }

    @Test
    void getMovieRecommendations() throws OnlineConnectionException, NoRecommendationsException {
        assertThrows(NoRecommendationsException.class, () ->
                new ApiManager().getMovieRecommendations(new ArrayList<>(), 1));
    }

    @Test
    void getTvShowRecommendations() throws OnlineConnectionException, NoRecommendationsException {
        assertThrows(NoRecommendationsException.class, () ->
                new ApiManager().getTvShowRecommendations(new ArrayList<>(), 1));
    }

    @Test
    void getTvShowByName() throws OnlineConnectionException {
        assertTrue(new ApiManager().getTvShowByName("Fantastic Beast") instanceof List);
    }

    @Test
    void getMovieByGenre() throws OnlineConnectionException {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("Action"));
        assertTrue(new ApiManager().getMovieByGenre(genres) instanceof ArrayList);
    }

    public static class ApiManagerStub extends ApiManager {
        private TmdbApi apiCall;
        /**
         * Constructor for ApiMain object used to interact with the API.
         *
         * @throws OnlineConnectionException when not connected to the internet.
         */
        public ApiManagerStub() throws OnlineConnectionException {
            try {
                apiCall = new TmdbApi("Error");
            } catch (MovieDbException e) {
                //when not connected to the internet
                notConnected();
            }
        }
    }
}
