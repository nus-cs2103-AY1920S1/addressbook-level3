package seedu.address.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE;
import static seedu.address.commons.core.Messages.MESSAGE_CONFIRM_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showNoteAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_NOTE;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteNoteCommand}.
 */
public class DeleteNoteCommandTest {


    private Model model = new ModelManager(getTypicalNoteList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Note noteToDelete = model.getFilteredNoteList().get(INDEX_FIRST_NOTE.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_NOTE);
        String deleteCheck = (MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE + "\n" + noteToDelete + "\n"
                + String.format(MESSAGE_CONFIRM_DELETE, deleteNoteCommand.getTargetIndex().getOneBased()));
        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete);

        NoteCommandResult checkCommandResult = new NoteCommandResult(deleteCheck);
        NoteCommandResult expectedCommandResult = new NoteCommandResult(expectedMessage);

        Model originalModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        expectedModel.deleteNote(noteToDelete);

        assertCommandSuccess(deleteNoteCommand, model, checkCommandResult, originalModel);
        // assertCommandSuccess(deleteNoteCommand, originalModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNoteList().size() + 1);
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(outOfBoundIndex);

        assertCommandFailure(deleteNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showNoteAtIndex(model, INDEX_FIRST_NOTE);

        Note noteToDelete = model.getFilteredNoteList().get(INDEX_FIRST_NOTE.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_NOTE);
        String deleteCheck = (MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE + "\n" + noteToDelete + "\n"
                + String.format(MESSAGE_CONFIRM_DELETE, deleteNoteCommand.getTargetIndex().getOneBased()));
        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete);

        NoteCommandResult checkCommandResult = new NoteCommandResult(deleteCheck);
        NoteCommandResult expectedCommandResult = new NoteCommandResult(expectedMessage);

        Model originalModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        showNoteAtIndex(originalModel, INDEX_FIRST_NOTE);
        Model expectedModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        expectedModel.deleteNote(noteToDelete);
        showNoNote(expectedModel);

        assertCommandSuccess(deleteNoteCommand, model, checkCommandResult, originalModel);
        // assertCommandSuccess(deleteNoteCommand, originalModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showNoteAtIndex(model, INDEX_FIRST_NOTE);

        Index outOfBoundIndex = INDEX_SECOND_NOTE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyBuddyPro().getNoteList().size());

        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(outOfBoundIndex);

        assertCommandFailure(deleteNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteNoteCommand deleteFirstNoteCommand = new DeleteNoteCommand(INDEX_FIRST_NOTE);
        DeleteNoteCommand deleteSecondNoteCommand = new DeleteNoteCommand(INDEX_SECOND_NOTE);

        // same object -> returns true
        assertTrue(deleteFirstNoteCommand.equals(deleteFirstNoteCommand));

        // same values -> returns true
        DeleteNoteCommand deleteFirstNoteCommandCopy = new DeleteNoteCommand(INDEX_FIRST_NOTE);
        assertTrue(deleteFirstNoteCommand.equals(deleteFirstNoteCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstNoteCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstNoteCommand.equals(null));

        // different note -> returns false
        assertFalse(deleteFirstNoteCommand.equals(deleteSecondNoteCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoNote(Model model) {
        model.updateFilteredNoteList(p -> false);

        assertTrue(model.getFilteredNoteList().isEmpty());
    }
}
