package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.ModelManager;


public class GoToCommandTest {
    @Test
    void execute() throws CommandException {
        ModelManager modelManager = new ModelManager();
        ModelManager expectedManager = new ModelManager();
        String pageTitle = "1";
        GoToCommand goToCommand = new GoToCommand(pageTitle);
        String expectedMessage = goToCommand.execute(modelManager).getFeedbackToUser();
        assertCommandSuccess(goToCommand, modelManager, expectedMessage, expectedManager);
    }

    @Test
    void testEquals() {
        String one = "one";
        String two = "two";

        GoToCommand goToCommand = new GoToCommand(one);
        GoToCommand goToCommand1 = new GoToCommand(two);

        // same object -> returns true test using ==
        assertTrue(goToCommand == goToCommand);

        // same object -> returns true
        assertTrue(goToCommand.equals(goToCommand));

        // same values -> returns true
        GoToCommand goToCommandCopy = new GoToCommand(one);
        assertTrue(goToCommandCopy.equals(goToCommand));

        // different types -> returns false
        assertFalse(goToCommand1.equals(1));

        // null -> returns false
        assertFalse(goToCommand1.equals(null));

        // different show -> returns false
        assertFalse(goToCommand.equals(goToCommand1));
    }
}
