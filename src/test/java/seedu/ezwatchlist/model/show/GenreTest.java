package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void constructor_null_passes() {
        assert(new Genre(null).equals(new Genre(null)));
    }

    @Test
    public void isValidGenreName() {
        // null Genre name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));
    }
    @Test
    public void genreConstructorTest() {
        assertEquals(new Genre("Action").genreName, "Action");
        assertEquals(new Genre("Action").getGenreName(), "Action");
        assertEquals(new Genre("Action").toString(), "Action");
    }

}
