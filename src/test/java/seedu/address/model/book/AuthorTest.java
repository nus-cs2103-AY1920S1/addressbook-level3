package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_1;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void equals_sameAuthor_assertTrue() {
        assertTrue(new Author(VALID_AUTHOR_BOOK_1).equals(new Author("J K Rowling")));
    }

    @Test
    public void toString_author() {
        assertTrue(new Author(VALID_AUTHOR_BOOK_1).toString().equals("J K Rowling"));
    }

    @Test
    public void hashCode_sameAuthor_assertsTrue() {
        Author author1 = new Author(VALID_AUTHOR_BOOK_1);
        Author author2 = new Author(VALID_AUTHOR_BOOK_1);
        assertTrue(author1.hashCode() == author2.hashCode());
    }
}
