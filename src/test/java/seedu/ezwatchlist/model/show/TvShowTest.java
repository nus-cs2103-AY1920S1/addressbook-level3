package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.testutil.ShowBuilder;
import seedu.ezwatchlist.testutil.TypicalShows;

public class TvShowTest {

    private Show showWatchedAll = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(195).build();
    private Show showWatched100 = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(100).build();
    private Show showWatched0 = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(0).build();

    @Test
    public void getNumOfEpisodesWatched() {
        assertEquals(showWatched0.getNumOfEpisodesWatched(), 0);
        assertEquals(showWatchedAll.getNumOfEpisodesWatched(), 195);
    }

    @Test
    public void getTotalNumOfEpisodes() {
        assertEquals(showWatched0.getTotalNumOfEpisodes(), showWatched100.getTotalNumOfEpisodes());
    }

    @Test
    public void getNumberOfSeasons() {
        assertEquals(showWatchedAll.getNumOfSeasons(), 9);
    }

    @Test
    public void getNumOfEpisodesOfSeason() {
        assertEquals(showWatchedAll.getNumOfEpisodesOfSeason(2), 22);
        assertEquals(showWatchedAll.getNumOfEpisodesOfSeason(9), 23);
    }

    @Test
    public void getLastWatchedSeasonNum() {
        assertEquals(showWatchedAll.getLastWatchedSeasonNum(), 9);
        assertEquals(showWatched100.getLastWatchedSeasonNum(), 6);
        assertEquals(showWatched0.getLastWatchedSeasonNum(), 0);
    }

    @Test
    public void getLastWatchedSeasonEpisode() {
        assertEquals(showWatchedAll.getLastWatchedSeasonEpisode(), 23);
        assertEquals(showWatched100.getLastWatchedSeasonEpisode(), 4);
        assertEquals(showWatched0.getLastWatchedSeasonEpisode(), 0);
    }
}
