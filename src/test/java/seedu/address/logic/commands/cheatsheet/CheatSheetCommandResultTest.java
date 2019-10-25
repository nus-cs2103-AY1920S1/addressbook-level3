package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;

public class CheatSheetCommandResultTest {

    //To implement
    //public static final CheatSheetBuilder CheatSheet_BUILDER = new CheatSheetBuilder();

    @Test
    public void equals() {

        //CheatSheetCommandResult with variables feedbackToUser: "feedback", Optional<CheatSheet>: Optional.empty()
        CheatSheetCommandResult commandResult = new CheatSheetCommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CheatSheetCommandResult("feedback")));
        assertTrue(commandResult.equals(new CheatSheetCommandResult("feedback", Optional.empty())));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CheatSheetCommandResult("different")));

        /* To implement
        // different Optional<CheatSheet> value -> returns false
        assertFalse(commandResult.equals(new CheatSheetCommandResult("feedback",
                Optional.of(CheatSheet_BUILDER.build()))));
        */
    }

    @Test
    public void hashcode() {

        //CheatSheetCommandResult with variables feedbackToUser: "feedback", Optional<CheatSheet>: Optional.empty()
        CommandResult commandResult = new CheatSheetCommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CheatSheetCommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CheatSheetCommandResult("different").hashCode());

        /* To implement
        // different Optional<CheatSheet> value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CheatSheetCommandResult("feedback",
                Optional.of(CheatSheet_BUILDER.build())).hashCode());
         */
    }
}
