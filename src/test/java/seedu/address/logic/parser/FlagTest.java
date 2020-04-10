package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FlagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Flag(null));
    }

    @Test
    public void isValidFlag() {
        // null flag
        assertThrows(NullPointerException.class, () -> Flag.isValidFlag(null));

        // invalid flag
        assertFalse(Flag.isValidFlag("")); // empty string
        assertFalse(Flag.isValidFlag(" ")); // spaces only
        assertFalse(Flag.isValidFlag("-person")); // should be -p only
        assertFalse(Flag.isValidFlag("-event")); // should be -e only

        // valid flag
        assertTrue(Flag.isValidFlag("-p")); // person flag
        assertTrue(Flag.isValidFlag("-e")); // event flag
    }

    @Test
    public void equals() {
        Flag firstFlag = FLAG_PERSON;
        Flag secondFlag = FLAG_EVENT;

        // same object -> returns true
        assertTrue(firstFlag.equals(firstFlag));

        // same values -> returns true
        Flag firstFlagCopy = new Flag("-p");
        assertTrue(firstFlag.equals(firstFlagCopy));

        // different types -> returns false
        assertFalse(firstFlag.equals(1));

        // null -> returns false
        assertFalse(firstFlag.equals(null));

        // different flag -> returns false
        assertFalse(firstFlag.equals(secondFlag));
    }
}
