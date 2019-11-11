package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DELETE_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.TutorAidParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commands.CommandObject;


/**
 * Contains integration tests and unit tests for
 * {@code DeleteCustomCommand}.
 */
public class DeleteCustomCommandTest {

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
    private TutorAidParser parser = new TutorAidParser();

    @Test
    public void execute_validCustomCommand_success() {
        CommandObject commandToDelete = model.getFilteredCommandsList().get(INDEX_FIRST.getZeroBased());
        DeleteCustomCommand deleteCustomCommand = new DeleteCustomCommand(commandToDelete);

        String expectedMessage = String.format(DeleteCustomCommand.MESSAGE_DELETE_COMMAND_SUCCESS, commandToDelete);

        ModelManager expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        expectedModel.deleteCommand(commandToDelete);

        assertCommandSuccess(deleteCustomCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteBasicCommand_throwsCommandException() {
        CommandObject basicCommand = DELETE_COMMAND;
        DeleteCustomCommand deleteCustomCommand = new DeleteCustomCommand(basicCommand);

        assertCommandFailure(deleteCustomCommand, model,
                String.format(DeleteCustomCommand.MESSAGE_DELETE_COMMAND_FAIL, basicCommand));
    }

    @Test
    public void equals() {
        CommandObject firstCustomCommand = model.getFilteredCommandsList().get(INDEX_FIRST.getZeroBased());
        CommandObject secondCustomCommand = model.getFilteredCommandsList().get(INDEX_SECOND.getZeroBased());

        DeleteCustomCommand deleteFirstCommand = new DeleteCustomCommand(firstCustomCommand);
        DeleteCustomCommand deleteSecondCommand = new DeleteCustomCommand(secondCustomCommand);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCustomCommand deleteFirstCommandCopy = new DeleteCustomCommand(firstCustomCommand);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different commandObject -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
