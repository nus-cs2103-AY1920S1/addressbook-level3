package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.DeleteNotesCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteNoteCommandTest {
    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void equals() {
        DeleteNotesCommand deleteFirstCommand = new DeleteNotesCommand(INDEX_FIRST);
        DeleteNotesCommand deleteSecondCommand = new DeleteNotesCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteNotesCommand deleteFirstCommandCopy = new DeleteNotesCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoNotes(Model model) {
        model.updateFilteredNotesList(p -> false);

        assertTrue(model.getFilteredNotesList().isEmpty());
    }
}
