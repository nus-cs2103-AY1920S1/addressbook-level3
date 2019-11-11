package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // space only
        assertFalse(Title.isValidTitle("   ")); // spaces only
        assertFalse(Title.isValidTitle(" <- is crazy")); // space at start
        assertFalse(Title.isValidTitle("^")); // starting with non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("A")); // 1 Alphabet
        assertTrue(Title.isValidTitle("0")); // 1 Number
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("Math for computers +-*/~`")); // +-*/~`
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("peter*")); // contains non-alphanumeric characters
        assertTrue(Title.isValidTitle("My Tale: A Story of Blah-blah & \"!@#$%)^&*('")); // Punctuation
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
