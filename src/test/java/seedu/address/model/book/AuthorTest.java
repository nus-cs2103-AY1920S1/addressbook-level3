package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void isValidAuthor() {
        // null name
        assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // space only
        assertFalse(Title.isValidTitle("   ")); // spaces only
        assertFalse(Title.isValidTitle(" <- is crazy")); // space at start
        assertFalse(Title.isValidTitle("^")); // starting with non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("A")); // 1 Alphabet
        assertTrue(Title.isValidTitle("0")); // 1 Number
        assertTrue(Title.isValidTitle("3DExpert")); // numbers only
        assertTrue(Title.isValidTitle("Maxi+millan-lan*1/1~max`i")); // +-*/~`
        assertTrue(Title.isValidTitle("Porter D KG the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("peter*")); // contains non-alphanumeric characters
        assertTrue(Title.isValidTitle("Susan Suriana with a load of ;<>|}[]{:\"!@#$%)^&*('")); // Punctuation
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
