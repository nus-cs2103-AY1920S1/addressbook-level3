package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        int wordMaxLength = 80;

        // null name
        assertThrows(NullPointerException.class, () -> Word.isValidWord(null));

        // invalid name
        assertFalse(Word.isValidWord("")); // empty string
        assertFalse(Word.isValidWord(" \n \t   ")); // spaces only
        // todo dont know if this should be allowed
        assertTrue(Word.isValidWord("^")); // only non-alphanumeric characters
        assertTrue(Word.isValidWord("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Word.isValidWord("peter jack")); // alphabets only
        assertTrue(Word.isValidWord("12345")); // numbers only
        assertTrue(Word.isValidWord("peter the 2nd")); // alphanumeric characters
        assertTrue(Word.isValidWord("Capital Tan")); // with capital letters
        assertTrue(Word.isValidWord("David Roger Jackson Ray Jr 2nd")); // long names

        // more than 80 characters.
        String moreThan80Characters = "";
        for (int i = 0; i < wordMaxLength * 2; i++) {
            moreThan80Characters += "0";
        }
        assertFalse(Word.isValidWord(moreThan80Characters));

        // exactly 80 characters
        String exactly80Characters = "";
        for (int i = 0; i < wordMaxLength; i++) {
            exactly80Characters += "0";
        }
        assertTrue(Word.isValidWord(exactly80Characters));

        // exactly 1 character
        assertTrue(Word.isValidWord("1"));
    }

    @Test
    void testToString() {
        Word dummyWord = new Word("dummyWord");
        assertEquals(dummyWord.toString(), "dummyWord");
    }

    @Test
    void getValue() {
        Word dummyWord = new Word("dummyWord");
        assertEquals(dummyWord.getValue(), "dummyWord");
    }

    @Test
    void testEquals() {
        Word dummyWord = new Word("dummyWord");
        Word dummyWord2 = new Word("dummyWord2");
        Word dummyWord3 = new Word("dummyWord");

        assertFalse(dummyWord.equals(dummyWord2));
        assertTrue(dummyWord.equals(dummyWord));
        assertTrue(dummyWord.equals(dummyWord3));
    }
}
