package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code MemeDeleteCommand}.
 */
public class MemeDeleteCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Meme memeToDelete = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeDeleteCommand memeDeleteCommand = new MemeDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(MemeDeleteCommand.MESSAGE_DELETE_MEME_SUCCESS, memeToDelete);

        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.deleteMeme(memeToDelete);
        String feedback = String.format(MemeDeleteCommand.MESSAGE_DELETE_MEME_SUCCESS, memeToDelete);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        MemeDeleteCommand memeDeleteCommand = new MemeDeleteCommand(outOfBoundIndex);

        assertCommandFailure(memeDeleteCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMemeAtIndex(model, INDEX_FIRST);

        Meme memeToDelete = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeDeleteCommand memeDeleteCommand = new MemeDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(MemeDeleteCommand.MESSAGE_DELETE_MEME_SUCCESS, memeToDelete);

        Model expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.deleteMeme(memeToDelete);
        showNoMeme(expectedModel);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of meme list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getMemeList().size());

        MemeDeleteCommand memeDeleteCommand = new MemeDeleteCommand(outOfBoundIndex);

        assertCommandFailure(memeDeleteCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteStagedMeme_failure() {
        Meme memeInList = model.getWeme().getMemeList().get(INDEX_SECOND.getZeroBased());
        model.stageMeme(memeInList);
        MemeDeleteCommand memeDeleteCommand = new MemeDeleteCommand(INDEX_SECOND);
        assertCommandFailure(memeDeleteCommand, model, MemeDeleteCommand.MESSAGE_STAGED_MEME);
    }

    @Test
    public void equals() {
        MemeDeleteCommand deleteFirstCommand = new MemeDeleteCommand(INDEX_FIRST);
        MemeDeleteCommand deleteSecondCommand = new MemeDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        MemeDeleteCommand deleteFirstCommandCopy = new MemeDeleteCommand(INDEX_FIRST);
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
