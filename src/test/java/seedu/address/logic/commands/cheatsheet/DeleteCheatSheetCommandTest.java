package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showCheatSheetAtIndex;
import static seedu.address.testutil.TypicalCheatSheets.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CHEATSHEET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CHEATSHEET;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteCheatSheetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCheatSheetList().size() + 1);
        DeleteCheatSheetCommand deleteCommand = new DeleteCheatSheetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCheatSheetAtIndex(model, INDEX_FIRST_CHEATSHEET);

        Index outOfBoundIndex = INDEX_SECOND_CHEATSHEET;
        // ensures that outOfBoundIndex is still in bounds of studybuddy book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyBuddyPro().getCheatSheetList().size());

        DeleteCheatSheetCommand deleteCommand = new DeleteCheatSheetCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCheatSheetCommand deleteFirstCommand = new DeleteCheatSheetCommand(INDEX_FIRST_CHEATSHEET);
        DeleteCheatSheetCommand deleteSecondCommand = new DeleteCheatSheetCommand(INDEX_SECOND_CHEATSHEET);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCheatSheetCommand deleteFirstCommandCopy = new DeleteCheatSheetCommand(INDEX_FIRST_CHEATSHEET);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no cheatsheet.
     */
    private void showNoCheatSheet(Model model) {
        model.updateFilteredCheatSheetList(p -> false);

        assertTrue(model.getFilteredCheatSheetList().isEmpty());
    }
}
