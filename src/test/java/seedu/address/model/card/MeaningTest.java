package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MeaningTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Meaning(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String emptyDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Meaning(emptyDescription));
        StringBuilder tooLongDescription = new StringBuilder();
        for (int i = 0; i < Meaning.MAX_LEN + 1; ++i) {
            tooLongDescription.append("a");
        }
        assertThrows(IllegalArgumentException.class, () -> new Meaning(tooLongDescription.toString()));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Meaning.isValidMeaning(null));

        // invalid descriptions
        assertFalse(Meaning.isValidMeaning("")); // empty string
        assertFalse(Meaning.isValidMeaning(" ")); // spaces only

        // valid descriptions
        assertTrue(Meaning.isValidMeaning("I need help."));
        assertTrue(Meaning.isValidMeaning("Ineedhelp."));
        assertTrue(Meaning.isValidMeaning("-")); // one character
        StringBuilder maxLenDescription = new StringBuilder();
        for (int i = 0; i < Meaning.MAX_LEN; ++i) {
            maxLenDescription.append("a");
        }
        assertTrue(Meaning.isValidMeaning(maxLenDescription.toString())); // maximum length
    }
}
