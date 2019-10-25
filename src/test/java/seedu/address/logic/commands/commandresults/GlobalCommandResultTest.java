package seedu.address.logic.commands.commandresults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.FunctionMode;
import seedu.address.logic.commands.CommandResult;

public class GlobalCommandResultTest {
    @Test
    public void equals() {

        //GlobalCommandResult with variables feedbackToUser: "feedback", showHelp: False, exit; False, toggle: False,
        //Optional<targetMode>: Optional.empty()
        GlobalCommandResult commandResult = new GlobalCommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new GlobalCommandResult("feedback")));
        assertTrue(commandResult.equals(new GlobalCommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new GlobalCommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new GlobalCommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new GlobalCommandResult("feedback", false, true)));

        // different toggle value -> returns false
        assertFalse(commandResult.equals((new GlobalCommandResult("feedback", false,
                false, true, Optional.empty()))));

        //different targetMode values -> returns false
        assertFalse(commandResult.equals((new GlobalCommandResult("feedback", false,
                false, false, Optional.of(FunctionMode.CHEATSHEET)))));
    }

    @Test
    public void hashcode() {

        //GlobalCommandResult with variables feedbackToUser: "feedback", showHelp: False, exit; False, toggle: False,
        //Optional<targetMode>: Optional.empty()
        CommandResult commandResult = new GlobalCommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new GlobalCommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GlobalCommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GlobalCommandResult("feedback",
                true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GlobalCommandResult("feedback",
                false, true).hashCode());

        // different toggle value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GlobalCommandResult("feedback",
                false, false, true, Optional.empty()).hashCode());

        // different targetMode value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new GlobalCommandResult("feedback",
                false, false, false, Optional.of(FunctionMode.CHEATSHEET)).hashCode());
    }
}
