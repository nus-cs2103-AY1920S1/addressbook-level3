package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

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
        assertFalse(commandResult.equals(CommandResult.commandResultHelp("feedback")));

        // different exit value -> returns false
        assertFalse(commandResult.equals(CommandResult.commandResultExit("feedback")));

        // different serve value -> returns false
        assertFalse(commandResult.equals(CommandResult.commandResultServe("feedback")));

        // different done value -> returns false
        assertFalse(commandResult.equals(CommandResult.commandResultDone("feedback")));

        // different info value -> returns false
        assertFalse(commandResult.equals(CommandResult.commandResultInfo("feedback", BOOK_1)));

        CommandResult commandResult1 = CommandResult.commandResultInfo("info", BOOK_1);
        CommandResult commandResult2 = CommandResult.commandResultInfo("info", BOOK_2);

        assertNotEquals(commandResult1, commandResult2);
        assertNotEquals(commandResult, commandResult2);
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
                CommandResult.commandResultDone("feedback").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.commandResultHelp("feedback").hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.commandResultExit("feedback").hashCode());

        // different serve value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.commandResultServe("feedback").hashCode());

        // different info value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                CommandResult.commandResultInfo("feedback", BOOK_1).hashCode());
    }

    @Test
    public void isShowHelp() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isShowHelp());

        CommandResult commandResult2 = CommandResult.commandResultHelp("feedback");
        assertTrue(commandResult2.isShowHelp());
        assertFalse(commandResult1.isExit());
        assertFalse(commandResult1.isServe());
        assertFalse(commandResult1.isDone());
        assertFalse(commandResult1.isInfo());

        assertThrows(AssertionError.class, () -> commandResult2.getBook());
    }

    @Test
    public void isExit() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isExit());

        CommandResult commandResult2 = CommandResult.commandResultExit("feedback");
        assertTrue(commandResult2.isExit());
        assertFalse(commandResult1.isShowHelp());
        assertFalse(commandResult1.isServe());
        assertFalse(commandResult1.isDone());
        assertFalse(commandResult1.isInfo());

        assertThrows(AssertionError.class, () -> commandResult2.getBook());
    }

    @Test
    public void isServe() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isServe());

        CommandResult commandResult2 = CommandResult.commandResultServe("feedback");
        assertTrue(commandResult2.isServe());
        assertFalse(commandResult1.isShowHelp());
        assertFalse(commandResult1.isExit());
        assertFalse(commandResult1.isDone());
        assertFalse(commandResult1.isInfo());

        assertThrows(AssertionError.class, () -> commandResult2.getBook());
    }

    @Test
    public void isDone() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isDone());

        CommandResult commandResult2 = CommandResult.commandResultDone("feedback");
        assertTrue(commandResult2.isDone());
        assertFalse(commandResult1.isShowHelp());
        assertFalse(commandResult1.isExit());
        assertFalse(commandResult1.isServe());
        assertFalse(commandResult1.isInfo());

        assertThrows(AssertionError.class, () -> commandResult2.getBook());
    }

    @Test
    public void isInfo() {
        CommandResult commandResult1 = new CommandResult("feedback");
        assertFalse(commandResult1.isInfo());

        CommandResult commandResult2 = CommandResult.commandResultInfo("feedback", BOOK_1);
        assertTrue(commandResult2.isInfo());
        assertFalse(commandResult1.isShowHelp());
        assertFalse(commandResult1.isExit());
        assertFalse(commandResult1.isServe());
        assertFalse(commandResult1.isDone());

        assertDoesNotThrow(() -> commandResult2.getBook());
        assertEquals(BOOK_1, commandResult2.getBook());
    }
}
