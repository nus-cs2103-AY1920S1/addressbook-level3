package seedu.mark.logic.commands.commandresult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Url;

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

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new HelpCommandResult("feedback")));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new ExitCommandResult("feedback")));

        // different goto value -> returns false
        assertFalse(commandResult.equals(new GotoCommandResult("feedback",
                new Url(VALID_URL_AMY))));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new HelpCommandResult("feedback").hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new ExitCommandResult("feedback").hashCode());

        // different goto value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GotoCommandResult("feedback",
                new Url(VALID_URL_AMY)).hashCode());
    }
}
