package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ChoiceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Choice(null));
    }

    @Test
    public void constructor_invalidChoice_throwsIllegalArgumentException() {
        String invalidChoice = "";
        assertThrows(IllegalArgumentException.class, () -> new Choice(invalidChoice));
    }

    @Test
    public void isValidChoice() {
        // null Choice
        assertThrows(NullPointerException.class, () -> Choice.isValidChoice(null));

        // invalid Choice
        assertFalse(Choice.isValidChoice("")); // empty string
        assertFalse(Choice.isValidChoice(" ")); // spaces only

        // valid choice
        assertTrue(Choice.isValidChoice("3"));
        assertTrue(Choice.isValidChoice("/")); // one character
        assertTrue(Choice.isValidChoice("Builds applications by combining functionalities"
            + " packaged as programmatically")); // long answer
    }
}
