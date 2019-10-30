package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
