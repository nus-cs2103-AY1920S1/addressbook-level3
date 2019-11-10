package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.actor.Actor;

class MovieTest {
    private Set<Actor> actors = new HashSet<>();
    private Movie movie = new Movie(new Name("test"), new Description("test"), new IsWatched("false"),
            new Date("1/1/1"), new RunningTime(), actors);
    @Test
    void getNumOfEpisodesWatched() {
        assertEquals(movie.getNumOfEpisodesWatched(), 0);
    }

    @Test
    void getTotalNumOfEpisodes() {
        assertEquals(movie.getTotalNumOfEpisodes(), 0);
    }

    @Test
    void getTvSeasons() {
        assertEquals(movie.getTvSeasons(), null);
    }

    @Test
    void getLastWatchedSeasonNum() {
        assertEquals(movie.getLastWatchedSeasonNum(), 0);
    }

    @Test
    void getLastWatchedSeasonEpisode() {
        assertEquals(movie.getLastWatchedSeasonEpisode(), 0);
    }

    @Test
    void getNumOfSeasons() {
        assertEquals(movie.getNumOfSeasons(), 0);
    }

    @Test
    void getNumOfEpisodesOfSeason() {
        assertEquals(movie.getNumOfEpisodesOfSeason(1), 0);
    }
}
