package seedu.ezwatchlist.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ApiManager;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.testutil.ShowBuilder;

class StatisticsTest {
    private ModelManager model = new ModelManager();


    @Test
    void constructorTest() throws OnlineConnectionException {
        assertEquals(new Statistics(model).getModel(), model);
        ApiManager apiManager = new ApiManager();
        assertTrue(new Statistics(model).getApiManager() instanceof ApiManager);
    }
    @Test
    void getForgotten() throws OnlineConnectionException {
        model.addShow(new ShowBuilder().build());
        model.addShow(new ShowBuilder().withName("1").build());
        model.addShow(new ShowBuilder().withName("2").build());
        model.addShow(new ShowBuilder().withName("3").build());
        model.addShow(new ShowBuilder().withName("4").build());
        assertTrue(new Statistics(model).getForgotten() instanceof ObservableList);
    }

    @Test
    void getFavouriteGenre() throws OnlineConnectionException {
        model.addShow(new ShowBuilder().withGenres("Action").build());
        model.addShow(new ShowBuilder().withName("1").withGenres("Action").build());
        model.addShow(new ShowBuilder().withName("2").withGenres("Action").build());
        model.addShow(new ShowBuilder().withName("3").withGenres("Romance").build());
        model.addShow(new ShowBuilder().withName("4").withGenres("Romance").build());
        model.addShow(new ShowBuilder().withName("5").withGenres("Crime").build());
        assertTrue(new Statistics(model).getFavouriteGenre() instanceof ObservableMap);
    }

    @Test
    void getMovieRecommendations() throws OnlineConnectionException {
        model.addShow(new ShowBuilder().build());
        assertFalse(new Statistics(model).getMovieRecommendations() instanceof ObservableMap);
    }

    @Test
    void getTvShowRecommendations() throws OnlineConnectionException {
        model.addShow(new ShowBuilder().build());
        assertFalse(new Statistics(model).getTvShowRecommendations() instanceof ObservableList);
    }
}
