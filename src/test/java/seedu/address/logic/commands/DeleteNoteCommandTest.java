package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_STRING_COMMAND_ARG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteNoteCommand}.
 */
public class DeleteNoteCommandTest {

    private Model model = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
            getTypicalNoteBook(), new PasswordBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Note noteToDelete = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST, VALID_STRING_COMMAND_ARG);

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete.getTitle());

        ModelManager expectedModel = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
                model.getNoteBook(), new PasswordBook(), new UserPrefs());

        expectedModel.deleteNote(noteToDelete);

        assertCommandSuccess(deleteNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteNoteCommand deleteFirstCommand = new DeleteNoteCommand(INDEX_FIRST, VALID_STRING_COMMAND_ARG);
        DeleteNoteCommand deleteSecondCommand = new DeleteNoteCommand(INDEX_SECOND, VALID_STRING_COMMAND_ARG);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteNoteCommand deleteFirstCommandCopy = new DeleteNoteCommand(INDEX_FIRST, VALID_STRING_COMMAND_ARG);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different note -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoNote(Model model) {
        model.updateFilteredNoteList(p -> false);

        assertTrue(model.getFilteredNoteList().isEmpty());
    }
}
