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

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(CommandResult.CommandResultHelp("feedback")));

        // different exit value -> returns false
        assertFalse(commandResult.equals(CommandResult.CommandResultExit("feedback")));

        // different serve value -> returns false
        assertFalse(commandResult.equals(CommandResult.CommandResultServe("feedback")));

        // different done value -> returns false
        assertFalse(commandResult.equals(CommandResult.CommandResultDone("feedback")));

        // different info value -> returns false
        assertFalse(commandResult.equals(CommandResult.CommandResultInfo("feedback")));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different done value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.CommandResultDone("feedback").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.CommandResultHelp("feedback").hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.CommandResultExit("feedback").hashCode());

        // different serve value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.CommandResultServe("feedback").hashCode());

        // different info value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.CommandResultInfo("feedback").hashCode());
    }

    @Test
    public void isShowHelp() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isShowHelp());

        CommandResult commandResult2 = CommandResult.CommandResultHelp("feedback");
        assertTrue(commandResult2.isShowHelp());
    }

    @Test
    public void isExit() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isExit());

        CommandResult commandResult2 = CommandResult.CommandResultExit("feedback");
        assertTrue(commandResult2.isExit());
    }

    @Test
    public void isServe() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isServe());

        CommandResult commandResult2 = CommandResult.CommandResultServe("feedback");
        assertTrue(commandResult2.isServe());
    }

    @Test
    public void isDone() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isDone());

        CommandResult commandResult2 = CommandResult.CommandResultDone("feedback");
        assertTrue(commandResult2.isDone());
    }
}
