package seedu.address.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.note.Note;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and {@code NoteDeleteCommand}.
 */
public class NoteDeleteCommandTest {

    private Model model = new ModelManager();

    public NoteDeleteCommandTest() {
        model.setNotesRecord(getTypicalNotesRecord());
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Note noteToDelete = model.getFilteredNotesList().get(INDEX_ONE.getZeroBased());
        NoteDeleteCommand deleteCommand = new NoteDeleteCommand(INDEX_ONE);

        String expectedMessage = String.format(NoteDeleteCommand.MESSAGE_DELETE_NOTE_SUCCESS, noteToDelete);

        CommandResult commandResult = deleteCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNotesList().size() + 1);
        NoteDeleteCommand deleteCommand = new NoteDeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model), MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NoteDeleteCommand deleteFirstCommand = new NoteDeleteCommand(INDEX_ONE);
        NoteDeleteCommand deleteSecondCommand = new NoteDeleteCommand(INDEX_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        NoteDeleteCommand deleteFirstCommandCopy = new NoteDeleteCommand(INDEX_ONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
