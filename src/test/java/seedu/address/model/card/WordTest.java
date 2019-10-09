package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Word(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Word(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Word.isValidWord(null));

        // invalid name
        assertFalse(Word.isValidWord("")); // empty string
        assertFalse(Word.isValidWord(" \n \t   ")); // spaces only
        // todo dont know if this should be allowed
        //assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        //assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Word.isValidWord("peter jack")); // alphabets only
        assertTrue(Word.isValidWord("12345")); // numbers only
        assertTrue(Word.isValidWord("peter the 2nd")); // alphanumeric characters
        assertTrue(Word.isValidWord("Capital Tan")); // with capital letters
        assertTrue(Word.isValidWord("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
