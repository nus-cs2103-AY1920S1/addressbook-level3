package seedu.weme.logic.commands.exportcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;

class UnstageCommandTest extends ApplicationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_unstageMeme_success() {
        Meme memeToStage = model.getWeme().getMemeList().get(INDEX_FIRST.getZeroBased());
        expectedModel.stageMeme(memeToStage);

        MemeStageCommand memeStageCommand = new MemeStageCommand(INDEX_FIRST);

        String expectedMessageStaged = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage);
        expectedModel.commitWeme(expectedMessageStaged);
        assertCommandSuccess(memeStageCommand, model, expectedMessageStaged, expectedModel);

        String expectedMessageUnstaged = String.format(UnstageCommand.MESSAGE_SUCCESS, memeToStage);
        expectedModel.unstageMeme(memeToStage);
        expectedModel.commitWeme(expectedMessageUnstaged);
        UnstageCommand unstageCommand = new UnstageCommand(INDEX_FIRST);
        assertCommandSuccess(unstageCommand, model, expectedMessageUnstaged, expectedModel);

    }

    @Test
    public void execute_invalidMemeIndex_failure() {
        // Attempt to unstage a meme in the empty staged list.
        UnstageCommand unstageCommand = new UnstageCommand(INDEX_FIRST);
        assertCommandFailure(unstageCommand, model, MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnstageCommand unstageFirstCommand = new UnstageCommand(INDEX_FIRST);
        UnstageCommand unstageSecondCommand = new UnstageCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unstageFirstCommand.equals(unstageFirstCommand));

        // same values -> returns true
        UnstageCommand unstageFirstCommandCopy = new UnstageCommand(INDEX_FIRST);
        assertTrue(unstageFirstCommand.equals(unstageFirstCommandCopy));

        // different types -> returns false
        assertFalse(unstageFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unstageFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(unstageFirstCommand.equals(unstageSecondCommand));
    }

}
