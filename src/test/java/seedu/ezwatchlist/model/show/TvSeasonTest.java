package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TvSeasonTest {

    @Test
    public void isValidTvSeasonNumber() {
        // Negative Season number
        assertFalse(TvSeason.isValidTvSeasonNumber(-1));

        // Season number is 0
        assertFalse(TvSeason.isValidTvSeasonNumber(0));
    }

    @Test
    public void isValidTotalNumberOfEpisodes() {
        // Total Number of Episodes is a negative number
        assertFalse(TvSeason.isValidTotalNumOfEpisodes(-1));
    }

    @Test
    void getSeasonNum() {
        ArrayList<Episode> arr = new ArrayList<>();
        Episode episode = new Episode("testName", 1);
        arr.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, arr);
        assertEquals(tvSeason.getSeasonNum(), 1);
    }

    @Test
    void getTotalNumOfEpisodes() {
        ArrayList<Episode> arr = new ArrayList<>();
        Episode episode = new Episode("testName", 1);
        arr.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, arr);
        assertEquals(tvSeason.getTotalNumOfEpisodes(), 1);
    }

    @Test
    void getEpisodes() {
        ArrayList<Episode> arr = new ArrayList<>();
        Episode episode = new Episode("testName", 1);
        arr.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, arr);
        assertEquals(tvSeason.getEpisodes(), arr);
    }
}
