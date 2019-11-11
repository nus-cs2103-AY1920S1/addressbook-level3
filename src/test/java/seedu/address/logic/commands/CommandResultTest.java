package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));

        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false,
            false, false, false, false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false,
            false, false, false, false, false, false, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true,
            false, false, false, false, false, false, false, false)));

        // different list policy value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            true, false, false, false, false, false, false, false)));

        // different list people value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            false, true, false, false, false, false, false, false)));

        // different report value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            false, false, true, false, false, false, false, false)));

        // different display value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            false, false, false, true, false, false, false, false)));

        // different expand person value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            false, false, false, false, true, false, false, false)));

        // different expand policy value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
            false, false, false, false, false, true, false, false)));

        // different list history value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                false, false, false, false, false, false, true, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true,
            false, false, false, false, false, false, false, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true,
            false, false, false, false, false, false, false, false).hashCode());

        // different listPolicy value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false,
            true, false, false, false, false, false, false, false).hashCode());

        // different listPeople value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false,
            false, true, false, false, false, false, false, false).hashCode());

        // different report value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false,
            false, false, true, false, false, false, false, false).hashCode());

        // different display value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false,
            false, true, false, true, false, false, false, false).hashCode());

        // different expand person value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
            false, false, false, false, false, true, false, false, false).hashCode());

        // different expand policy value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
            false, false, false, false, false, false, true, false, false).hashCode());

        // different histHistory value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
            false, false, false, false, false, false, false, true, false).hashCode());
    }
}
