package seedu.ezwatchlist.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.Show;

class SampleDataUtilTest {

    @Test
    void getSampleShows() {
        assertTrue(SampleDataUtil.getSampleShows() instanceof Show[]);
    }

    @Test
    void getSampleWatchList() {
        assertTrue(SampleDataUtil.getSampleWatchList() instanceof ReadOnlyWatchList);
    }

}
