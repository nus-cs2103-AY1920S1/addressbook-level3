package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureNoExceptionThrown;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalObjects.COMMAND_ITEM_1;
import static seedu.address.testutil.TypicalObjects.COMMAND_ITEM_2;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.CommandTask;
import seedu.address.model.commanditem.CommandWord;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteShortcutCommand}.
 */
public class DeleteShortcutCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void execute_validShortcut_success() {
        CommandItem shortcutToDelete = model.getFilteredCommandsList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteShortcutCommand deleteShortcutCommand = new DeleteShortcutCommand(shortcutToDelete);

        String expectedMessage = String.format(DeleteShortcutCommand.MESSAGE_DELETE_SHORTCUT_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
        expectedModel.deleteCommand(shortcutToDelete);

        assertCommandSuccess(deleteShortcutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidShortcut_throwsError() {
        DeleteShortcutCommand deleteShortcutCommand = new DeleteShortcutCommand(new CommandItem(new CommandWord("exit"),
                new CommandTask("exit")));

        assertCommandFailureNoExceptionThrown(deleteShortcutCommand, model,
                deleteShortcutCommand.MESSAGE_DELETE_SHORTCUT_FAIL);
    }


    @Test
    public void equals() {
        DeleteShortcutCommand deleteFirstShortcut = new DeleteShortcutCommand(COMMAND_ITEM_1);
        DeleteShortcutCommand deleteSecondShortcut = new DeleteShortcutCommand(COMMAND_ITEM_2);

        // same object -> returns true
        assertTrue(deleteFirstShortcut.equals(deleteFirstShortcut));

        // same values -> returns true
        DeleteShortcutCommand deleteFirstShortcutCopy = new DeleteShortcutCommand(COMMAND_ITEM_1);
        assertTrue(deleteFirstShortcut.equals(deleteFirstShortcutCopy));

        // different types -> returns false
        assertFalse(deleteFirstShortcut.equals(1));

        // null -> returns false
        assertFalse(deleteFirstShortcut.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstShortcut.equals(deleteSecondShortcut));
    }

}
