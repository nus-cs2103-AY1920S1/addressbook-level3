package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.planner.testutil.contact.TypicalContacts.ALICE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;

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
    }

    @Test
    public void execute_accessors_success() {
        CommandResult commandResult = new CommandResult("feedback", true, true);
        CommandResult commandResultWithUiFocus = new CommandResult("feedback",
                new UiFocus[]{UiFocus.INFO});
        CommandResult commandResultWithResultInformationAndUiFocus = new CommandResult(
                "feedback",
                new ResultInformation[]{new ResultInformation(ALICE, INDEX_FIRST)},
                new UiFocus[]{UiFocus.AGENDA}
        );

        //boolean field accessors
        assertTrue(commandResult.isShowHelp() == true);
        assertTrue(commandResult.isExit() == true);
        assertTrue(Arrays.equals(commandResultWithUiFocus.getUiFocus().get(), new UiFocus[]{UiFocus.INFO}));
        assertTrue(Arrays.equals(commandResultWithResultInformationAndUiFocus.getInformationToUser().get(),
                new ResultInformation[]{new ResultInformation(ALICE, INDEX_FIRST)}
        ));
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
    }
}
