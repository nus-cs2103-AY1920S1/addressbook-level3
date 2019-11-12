package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.JOKER;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

class JsonAdaptedMovieTest {

    @Test
    void toModelType() throws IllegalValueException {
        Show show = new ShowBuilder(JOKER).build();
        assertEquals(new JsonAdaptedMovie(show).getName(), "Joker");
        Show name = new ShowBuilder(JOKER).withName("").build();
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie(name).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie", "", "false", "des",
                1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie", "24/09/1997", "", "des",
                1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie", "24/09/1997", "false", "",
                1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie",
                "24/09/1997", "false", "1",
                -1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie",
                "24/09/1997", "false", null,
                1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedMovie("NAME", "Movie",
                null, "false", "des",
                1, new ArrayList<JsonAdaptedActor>(), "poster",
                new ArrayList<JsonAdaptedGenre>()).toModelType());
    }
}
