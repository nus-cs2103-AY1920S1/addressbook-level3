// @@author shiweing
package tagline.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandResult.ViewType;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.NoteCommandTestUtil.NON_EXISTING_NOTE_ID;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;
import static tagline.testutil.TypicalNotes.getTypicalNoteBook;

import org.junit.jupiter.api.Test;

import tagline.commons.core.Messages;
import tagline.logic.commands.NoteCommandTestUtil;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.note.Note;
import tagline.model.note.NoteId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteNoteCommand}.
 */
class DeleteNoteCommandTest {

    private static final ViewType DELETE_NOTE_COMMAND_VIEW_TYPE = ViewType.NOTE;
    private Model model = new ModelManager(getTypicalNoteBook(), new UserPrefs());

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteNoteCommand(null));
    }

    @Test
    public void execute_validNoteIdUnfilteredList_success() {
        Note noteToDelete = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(noteToDelete.getNoteId());

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_SUCCESS, noteToDelete);

        Model expectedModel = new ModelManager(model.getNoteBook(), new UserPrefs());
        expectedModel.deleteNote(noteToDelete);

        assertCommandSuccess(deleteNoteCommand, model, expectedMessage, DELETE_NOTE_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_validNoteIdFilteredList_success() {
        Note noteToDelete = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(noteToDelete.getNoteId());

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_SUCCESS, noteToDelete);

        Model expectedModel = new ModelManager(model.getNoteBook(), new UserPrefs());
        expectedModel.deleteNote(noteToDelete);

        showNoNote(model);
        showNoNote(expectedModel);

        assertCommandSuccess(deleteNoteCommand, model, expectedMessage, DELETE_NOTE_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_nonExistingNoteId_throwsCommandException() {
        NoteId nonExistingNoteId = NON_EXISTING_NOTE_ID;
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(nonExistingNoteId);

        NoteCommandTestUtil.assertCommandFailure(deleteNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoNote(Model model) {
        model.updateFilteredNoteList(p -> false);

        assertTrue(model.getFilteredNoteList().isEmpty());
    }
}
