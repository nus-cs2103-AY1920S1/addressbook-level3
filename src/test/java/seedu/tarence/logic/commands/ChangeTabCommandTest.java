package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalStudents.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeTabCommand}.
 */
public class ChangeTabCommandTest {
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_commandConstructor_showsSuccess() {
        String validTabName = "m";
        String messageSuccess = String.format(ChangeTabCommand.MESSAGE_SUCCESS,
                TabNames.MODULES.toString().toLowerCase());
        assertCommandSuccess(new ChangeTabCommand(validTabName), model, messageSuccess, expectedModel);
    }

    @Test
    public void equals() {
        // same object -> returns true
        ChangeTabCommand changeTabCommand = new ChangeTabCommand(TabNames.MODULES.toString());
        assertTrue(changeTabCommand.equals(changeTabCommand));

        // same value -> returns true
        assertTrue(changeTabCommand.equals(new ChangeTabCommand(TabNames.MODULES.toString())));

        // different types -> returns false
        assertFalse(changeTabCommand.equals(1));

        // null -> returns false
        assertFalse(changeTabCommand.equals(null));

        // different tab -> returns false
        assertFalse(changeTabCommand.equals(new ChangeTabCommand(TabNames.STUDENTS.toString())));
    }

    @Test
    public void execute_invalidTabName_throwsCommandException() {
        String invalidTab1 = "@";
        String invalidTab2 = "mod tuts";
        String invalidTab3 = "";

        ChangeTabCommand changeTabCommand = new ChangeTabCommand(invalidTab1);
        assertCommandFailure(changeTabCommand, model, Messages.MESSAGE_INVALID_TAB);

        changeTabCommand = new ChangeTabCommand(invalidTab2);
        assertCommandFailure(changeTabCommand, model, Messages.MESSAGE_INVALID_TAB);

        changeTabCommand = new ChangeTabCommand(invalidTab3);
        assertCommandFailure(changeTabCommand, model, ChangeTabCommand.MESSAGE_USAGE);
    }
}
