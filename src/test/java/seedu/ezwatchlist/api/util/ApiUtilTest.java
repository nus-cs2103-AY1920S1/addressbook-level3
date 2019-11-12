package seedu.ezwatchlist.api.util;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.TypicalShows;

class ApiUtilTest {

    @Test
    void filterToMovieFromShow() {
        List<Show> validShowList = TypicalShows.getTypicalShow();
        assertAll(() -> ApiUtil.filterToMovieFromShow(validShowList), () ->
                ApiUtil.filterToMovieFromShow(new ArrayList<>()));

    }

    @Test
    void filterToTvShowsFromShow() {
        List<Show> validShowList = TypicalShows.getTypicalShow();
        assertAll(() -> ApiUtil.filterToTvShowsFromShow(validShowList), () ->
                ApiUtil.filterToMovieFromShow(new ArrayList<>()));
    }
}
