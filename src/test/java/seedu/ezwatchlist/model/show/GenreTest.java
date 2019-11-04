package seedu.ezwatchlist.model.show;

import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void constructor_null_passes() {
        assert(new Genre(null).equals(new Genre(null)));
    }

    @Test
    public void isValidGenreName() {
        // null Actor name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));
    }

}
