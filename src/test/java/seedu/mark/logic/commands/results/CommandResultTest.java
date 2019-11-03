package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;

public class CommandResultTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandResult(null));
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

        // different isShowHelp() value -> returns false
        assertFalse(commandResult.equals(new HelpCommandResult("feedback")));

        // different isExit() value -> returns false
        assertFalse(commandResult.equals(new ExitCommandResult("feedback")));

        // different getTab() value -> returns false
        assertFalse(commandResult.equals(new TabCommandResult("feedback", TabCommand.Tab.DASHBOARD)));

        // different getLevelsToExpand() value -> returns false
        assertFalse(commandResult.equals(new ExpandCommandResult("feedback", 2)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different isShowHelp() value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new HelpCommandResult("feedback").hashCode());

        // different isExit() value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new ExitCommandResult("feedback").hashCode());

        // different getTab() value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new TabCommandResult("feedback", TabCommand.Tab.DASHBOARD).hashCode());

        // different getLevelsToExpand() value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new ExpandCommandResult("feedback", 2).hashCode());
    }
}
