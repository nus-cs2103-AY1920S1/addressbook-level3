package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.model.ModelContext.CONTEXT_VIEW;
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

class MemeViewCommandTest extends ApplicationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());

    }

    @Test
    public void execute_viewMeme_success() {
        Meme memeToView = model.getWeme().getMemeList().get(INDEX_FIRST.getZeroBased());
        expectedModel.setViewableMeme(memeToView);

        MemeViewCommand memeViewCommand = new MemeViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(MemeViewCommand.MESSAGE_VIEW_MEME_SUCCESS, memeToView);
        expectedModel.setContext(CONTEXT_VIEW);
        assertCommandSuccess(memeViewCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Views meme in filtered list where index is larger than size of filtered list,
     * but smaller than size of the meme list.
     */
    @Test
    public void execute_invalidMemeIndexFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of weme list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getMemeList().size());

        MemeViewCommand memeViewCommand = new MemeViewCommand(outOfBoundIndex);

        assertCommandFailure(memeViewCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        MemeViewCommand viewFirstCommand = new MemeViewCommand(INDEX_FIRST);
        MemeViewCommand viewSecondCommand = new MemeViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        MemeViewCommand viewFirstCommandCopy = new MemeViewCommand(INDEX_FIRST);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

}
