package seedu.ezwatchlist.api.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImageRetrievalTest {

    @Test
    void constructor_nullPath_throwsIllegalArgumentException() {
        String validFileName = "john_wick";
        assertThrows(IllegalArgumentException.class, () ->
                new ImageRetrieval(null, null, validFileName));
    }
}
