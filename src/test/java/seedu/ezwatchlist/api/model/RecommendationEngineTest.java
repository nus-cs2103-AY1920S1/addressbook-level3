package seedu.ezwatchlist.api.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.TvShow;

class RecommendationEngineTest {

    @Test
    void constructor_null_passes() {
        List<TvShow> validTvList = new ArrayList<>();
        List<Movie> validMovieList = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () ->
                new RecommendationEngine(validMovieList, validTvList, null));

        assertThrows(IllegalArgumentException.class, () ->
                new RecommendationEngine(null, validTvList, null));

        assertThrows(IllegalArgumentException.class, () ->
                new RecommendationEngine(validMovieList, null, null));
    }
}
