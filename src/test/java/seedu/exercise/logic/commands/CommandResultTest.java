package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.exercise.ui.ListResourceType;

public class CommandResultTest {
    @Test
    public void execute_accessors_success() {
        CommandResult commandResult = new CommandResult("feedback", true, true, true);

        //boolean field accessors
        assertTrue(commandResult.isShowHelp() == true);
        assertTrue(commandResult.isExit() == true);
        assertTrue(commandResult.isShowResolve() == true);

        //listResourceType accessor
        assertTrue(commandResult.getShowListResourceType().equals(ListResourceType.NULL));

    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));

        // different resolve value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true)));

        //undefined list resource type -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", ListResourceType.NULL)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, ListResourceType.NULL)));

        assertFalse(commandResult.equals(
                new CommandResult("feedback", false, false, false, ListResourceType.EXERCISE)));
        assertFalse(commandResult.equals(
                new CommandResult("feedback", false, false, false, ListResourceType.REGIME)));
        assertFalse(commandResult.equals(
                new CommandResult("feedback", false, false, false, ListResourceType.SCHEDULE)));
        assertFalse(commandResult.equals(
                new CommandResult("feedback", false, false, false, ListResourceType.SUGGESTION)));

        //different list resource type -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", ListResourceType.EXERCISE)));
        assertFalse(commandResult.equals(new CommandResult("feedback", ListResourceType.REGIME)));
        assertFalse(commandResult.equals(new CommandResult("feedback", ListResourceType.SCHEDULE)));
        assertFalse(commandResult.equals(new CommandResult("feedback", ListResourceType.SCHEDULE)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());

        //different list resource type -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", ListResourceType.NULL));
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", ListResourceType.EXERCISE));
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", ListResourceType.REGIME));
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", ListResourceType.SCHEDULE));
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", ListResourceType.SUGGESTION));

        //multiple different field values -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, true, ListResourceType.SCHEDULE).hashCode());

    }
}
