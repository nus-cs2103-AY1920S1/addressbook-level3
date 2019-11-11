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
import seedu.weme.testutil.MemeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code MemeUnarchiveCommand}.
 */
public class MemeUnarchiveCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        model.updateFilteredMemeList(Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // prepare archived meme
        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.updateFilteredMemeList(Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES);

        Meme memeToUnarchive = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeUnarchiveCommand memeUnarchiveCommand = new MemeUnarchiveCommand(INDEX_FIRST);
        Meme unarchivedMeme = new MemeBuilder(memeToUnarchive).withIsArchived(false).build();

        String expectedMessage = String.format(MemeUnarchiveCommand.MESSAGE_UNARCHIVE_MEME_SUCCESS, memeToUnarchive);

        expectedModel.setMeme(memeToUnarchive, unarchivedMeme);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeUnarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        MemeUnarchiveCommand memeUnarchiveCommand = new MemeUnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(memeUnarchiveCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.updateFilteredMemeList(Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES);

        showMemeAtIndex(model, INDEX_FIRST);

        Meme memeToUnarchive = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeUnarchiveCommand memeUnarchiveCommand = new MemeUnarchiveCommand(INDEX_FIRST);
        Meme unarchivedMeme = new MemeBuilder(memeToUnarchive).withIsArchived(false).build();

        String expectedMessage = String.format(MemeUnarchiveCommand.MESSAGE_UNARCHIVE_MEME_SUCCESS, memeToUnarchive);

        expectedModel.setMeme(memeToUnarchive, unarchivedMeme);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeUnarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of meme list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getMemeList().size());

        MemeUnarchiveCommand memeUnarchiveCommand = new MemeUnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(memeUnarchiveCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unarchivedMeme_throwsCommandException() {
        model.updateFilteredMemeList(Model.PREDICATE_SHOW_ALL_UNARCHIVED_MEMES);
        MemeUnarchiveCommand memeUnarchiveCommand = new MemeUnarchiveCommand(INDEX_FIRST);

        assertCommandFailure(memeUnarchiveCommand, model, MemeUnarchiveCommand.MESSAGE_ALREADY_UNARCHIVED);
    }

    @Test
    public void equals() {
        MemeUnarchiveCommand unarchiveFirstCommand = new MemeUnarchiveCommand(INDEX_FIRST);
        MemeUnarchiveCommand unarchiveSecondCommand = new MemeUnarchiveCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        MemeUnarchiveCommand unarchiveFirstCommandCopy = new MemeUnarchiveCommand(INDEX_FIRST);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

}
