package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ContextType;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        CommandResult contextualResult = new CommandResult("output", ContextType.LIST_ACTIVITY);

        // identity -> returns true
        assertTrue(contextualResult.equals(new CommandResult("output", ContextType.LIST_ACTIVITY)));

        // empty context -> returns false
        assertFalse(contextualResult.equals(commandResult));

        // non-empty but different context -> returns false
        assertFalse(contextualResult.equals(new CommandResult("output", ContextType.VIEW_ACTIVITY)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        CommandResult contextualResult = new CommandResult("feedback", ContextType.VIEW_ACTIVITY);

        // identity -> returns same hashcode
        assertEquals(contextualResult.hashCode(),
                new CommandResult("feedback", ContextType.VIEW_ACTIVITY).hashCode());

        // different context type -> returns different hashcode
        assertNotEquals(contextualResult.hashCode(),
                new CommandResult("feedback", ContextType.VIEW_CONTACT).hashCode());

    }
}
