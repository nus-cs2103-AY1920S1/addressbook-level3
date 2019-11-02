package seedu.address.model.genre;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {
    public static final String NAME_29_CHARACTER_LENGTH = "12345678901234567890123456789";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Genre(null));
    }

    @Test
    public void constructor_invalidGenreName_throwsIllegalArgumentException() {
        String invalidGenreName = "";
        assertThrows(IllegalArgumentException.class, () -> new Genre(invalidGenreName));
    }

    @Test
    public void constructor_convertsUppercase() {
        Genre genre = new Genre("test");
        assertTrue(genre.genreName.equals("TEST"));
    }

    @Test
    public void isValidGenreName() {
        // null genre name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));

        // invalid name
        assertFalse(Genre.isValidGenreName("")); // empty string
        assertFalse(Genre.isValidGenreName(" ")); // space only
        assertFalse(Genre.isValidGenreName("   ")); // spaces only
        assertFalse(Genre.isValidGenreName(" A")); // space at start
        assertFalse(Genre.isValidGenreName("^")); // starting with non-alphanumeric characters
        assertFalse(Genre.isValidGenreName("peteromollie")); // lowercase
        assertFalse(Genre.isValidGenreName("CapitalTan")); // CamelCase
        assertFalse(Genre.isValidGenreName(";[]{}|<>.,:?\"!@#$%)^&*('")); // Punctuation
        assertFalse(Genre.isValidGenreName("Operations +-*/~`")); // +-*/~`
        assertFalse(Genre.isValidGenreName("PETER*")); // contains non-alphanumeric characters

        // invalid length
        assertFalse(Genre.isValidGenreLength(NAME_29_CHARACTER_LENGTH + "aa"));

        // valid name
        assertTrue(Genre.isValidGenreName("A")); // 1 Alphabet
        assertTrue(Genre.isValidGenreName("0")); // 1 Number
        assertTrue(Genre.isValidGenreName("12345")); // numbers only
        assertTrue(Genre.isValidGenreName("FICTION")); // UPPERCASE
        assertTrue(Genre.isValidGenreName("NON-FICTION")); // UPPERCASE with hyphen

        assertTrue(Genre.isValidGenreLength(NAME_29_CHARACTER_LENGTH));
        assertTrue(Genre.isValidGenreLength(NAME_29_CHARACTER_LENGTH + "a"));
    }

}
