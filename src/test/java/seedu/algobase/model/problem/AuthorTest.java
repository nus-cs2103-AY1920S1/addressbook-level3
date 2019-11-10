package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidAuthor_throwsIllegalArgumentException() {
        String invalidAuthor = "";
        assertThrows(IllegalArgumentException.class, () -> new Author(invalidAuthor));
    }

    @Test
    public void isValidAuthor() {
        // null author
        assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid author
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // only spaces
        assertFalse(Author.isValidAuthor("^")); // non-whitelisted characters
        assertFalse(Author.isValidAuthor("*Lee HL*")); // contains non-whitelisted characters

        // valid author
        assertTrue(Author.isValidAuthor("VictorTan")); // only alphabets
        assertTrue(Author.isValidAuthor("Victor Tan")); // alphabets with spaces
        assertTrue(Author.isValidAuthor("123456789")); // only numbers
        assertTrue(Author.isValidAuthor("2nd Victor Tan")); // combination of alphanumeric characters and spaces
        assertTrue(Author.isValidAuthor("Hubert Blaine Wolfeschlegelsteinhausenbergerdorff")); // long name
    }

}
