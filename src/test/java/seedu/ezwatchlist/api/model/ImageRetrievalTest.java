package seedu.ezwatchlist.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;

class ImageRetrievalTest {
    private TmdbApi tmdbApi = new TmdbApi("44ed1d7975d7c699743229199b1fc26e");
    private String testpath = "https://www.thesun.co.uk/wp-content/uploads/2019/10/"
            + "NINTCHDBPICT000519469867-e1572450369403.jpg";

    @Test
    void constructor_nullPath_throwsIllegalArgumentException() {
        String validFileName = "john_wick";
        assertThrows(IllegalArgumentException.class, () ->
                new ImageRetrieval(null, null, validFileName));
    }

    @Test
    void defaultDirectory() {
    }

    @Test
    void retrieveImage() throws OnlineConnectionException {
        TmdbConfiguration configuration = tmdbApi.getConfiguration();
        String imageUrltest = configuration.getBaseUrl() + "w300" + testpath;
        assertEquals(new ImageRetrieval(tmdbApi, testpath, "Joker").getImageUrl(), imageUrltest);
    }

    @Test
    void getImageUrl() {
    }
}
