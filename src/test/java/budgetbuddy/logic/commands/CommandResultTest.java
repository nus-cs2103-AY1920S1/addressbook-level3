package budgetbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback", CommandCategory.MISC);

        // same values -> returns true
        assertEquals(commandResult, new CommandResult("feedback", CommandCategory.MISC));
        assertEquals(commandResult, new CommandResult("feedback", CommandCategory.MISC));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertNotEquals(0.5f, commandResult);

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult("different", CommandCategory.MISC));

        // different continuation -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", CommandCategory.MISC,
                CommandContinuation.showHelp()));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback", CommandCategory.MISC);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback", CommandCategory.MISC).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different", CommandCategory.MISC).hashCode());

        // different continuation -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", CommandCategory.MISC,
                        CommandContinuation.showHelp()).hashCode());
    }
}
