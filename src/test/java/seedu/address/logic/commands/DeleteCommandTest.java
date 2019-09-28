package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEME;
import static seedu.address.testutil.TypicalMemes.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meme.Meme;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Meme memeToDelete = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEME);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEME_SUCCESS, memeToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeme(memeToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        Meme memeToDelete = model.getFilteredMemeList().get(INDEX_FIRST_MEME.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEME);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEME_SUCCESS, memeToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeme(memeToDelete);
        showNoMeme(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);

        Index outOfBoundIndex = INDEX_SECOND_MEME;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMemeList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_MEME);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_MEME);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_MEME);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeme(Model model) {
        model.updateFilteredMemeList(p -> false);

        assertTrue(model.getFilteredMemeList().isEmpty());
    }
}
