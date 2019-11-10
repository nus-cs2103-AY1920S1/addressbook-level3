package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Genre;

class JsonAdaptedGenreTest {

    @Test
    void getGenreName() {
        JsonAdaptedGenre jsonAdaptedGenre = new JsonAdaptedGenre("Action");
        Genre genre = new Genre("Action");
        assertEquals(jsonAdaptedGenre.getGenreName(), "Action");
        assertEquals(new JsonAdaptedGenre(genre).getGenreName(), "Action");
    }

    @Test
    void toModelType() {
        JsonAdaptedGenre jsonAdaptedGenre = new JsonAdaptedGenre("");
        assertThrows(IllegalValueException.class, () -> jsonAdaptedGenre.toModelType());
    }
}
