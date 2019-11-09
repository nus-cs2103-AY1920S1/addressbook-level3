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
 * {@code MemeArchiveCommand}.
 */
public class MemeArchiveCommandTest extends ApplicationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Meme memeToArchive = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeArchiveCommand memeArchiveCommand = new MemeArchiveCommand(INDEX_FIRST);
        Meme archivedMeme = new MemeBuilder(memeToArchive).withIsArchived(true).build();

        String expectedMessage = String.format(MemeArchiveCommand.MESSAGE_ARCHIVE_MEME_SUCCESS, memeToArchive);

        ModelManager expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.setMeme(memeToArchive, archivedMeme);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        MemeArchiveCommand memeArchiveCommand = new MemeArchiveCommand(outOfBoundIndex);

        assertCommandFailure(memeArchiveCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMemeAtIndex(model, INDEX_FIRST);

        Meme memeToArchive = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeArchiveCommand memeArchiveCommand = new MemeArchiveCommand(INDEX_FIRST);
        Meme archivedMeme = new MemeBuilder(memeToArchive).withIsArchived(true).build();

        String expectedMessage = String.format(MemeArchiveCommand.MESSAGE_ARCHIVE_MEME_SUCCESS, memeToArchive);

        Model expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
        expectedModel.setMeme(memeToArchive, archivedMeme);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(memeArchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemeAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of meme list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getMemeList().size());

        MemeArchiveCommand memeArchiveCommand = new MemeArchiveCommand(outOfBoundIndex);

        assertCommandFailure(memeArchiveCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archivedMeme_throwsCommandException() {
        model.updateFilteredMemeList(Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES);
        MemeArchiveCommand memeArchiveCommand = new MemeArchiveCommand(INDEX_FIRST);

        assertCommandFailure(memeArchiveCommand, model, MemeArchiveCommand.MESSAGE_ALREADY_ARCHIVED);
    }

    @Test
    public void equals() {
        MemeArchiveCommand archiveFirstCommand = new MemeArchiveCommand(INDEX_FIRST);
        MemeArchiveCommand archiveSecondCommand = new MemeArchiveCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        MemeArchiveCommand archiveFirstCommandCopy = new MemeArchiveCommand(INDEX_FIRST);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

}
