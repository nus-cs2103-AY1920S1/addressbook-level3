package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Context;
import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

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

        CommandResult contextualResult = new CommandResult("output", new Context(TypicalPersons.ALICE));

        // identity -> returns true
        assertTrue(contextualResult.equals(new CommandResult("output", new Context(TypicalPersons.ALICE))));

        // empty Context -> returns false
        assertFalse(contextualResult.equals(new CommandResult("output")));

        // different ContextType -> returns false
        assertFalse(contextualResult.equals(new CommandResult("output", Context.newListContactContext())));
        assertFalse(contextualResult.equals(new CommandResult("output", new Context(TypicalActivities.BREAKFAST))));

        // same ContextType but different value -> returns false
        assertFalse(contextualResult.equals(new CommandResult("output", new Context(TypicalPersons.BOB))));
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

        CommandResult contextualResult = new CommandResult("feedback", new Context(TypicalActivities.BREAKFAST));
        int expectedHash = contextualResult.hashCode();

        // identity -> returns same hashcode
        assertEquals(expectedHash, new CommandResult("feedback", new Context(TypicalActivities.BREAKFAST)).hashCode());

        // empty Context -> returns false
        assertNotEquals(expectedHash, new CommandResult("feedback").hashCode());

        // different ContextType -> returns different hashcode
        assertNotEquals(expectedHash, new CommandResult("feedback", Context.newListActivityContext()).hashCode());
        assertNotEquals(expectedHash, new CommandResult("feedback", new Context(TypicalPersons.ALICE)).hashCode());

        // same ContextType but different value -> returns false
        assertNotEquals(expectedHash, new CommandResult("feedback", new Context(TypicalActivities.LUNCH)).hashCode());
    }
}
