package seedu.algobase.model.Problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AuthorTest {

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
        // null author number
        assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid author numbers
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only
        assertFalse(Author.isValidAuthor("91")); // less than 3 numbers
        assertFalse(Author.isValidAuthor("author")); // non-numeric
        assertFalse(Author.isValidAuthor("9011p041")); // alphabets within digits
        assertFalse(Author.isValidAuthor("9312 1534")); // spaces within digits

        // valid author numbers
        assertTrue(Author.isValidAuthor("911")); // exactly 3 numbers
        assertTrue(Author.isValidAuthor("93121534"));
        assertTrue(Author.isValidAuthor("124293842033123")); // long author numbers
    }
}
