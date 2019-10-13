package seedu.address.model.genre;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Genre(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Genre(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null genre name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));
    }

}
