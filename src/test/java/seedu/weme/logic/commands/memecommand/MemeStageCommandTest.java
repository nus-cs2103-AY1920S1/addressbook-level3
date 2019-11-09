package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.logic.commands.memecommand.MemeStageCommand.MESSAGE_MEME_ALREADY_STAGED;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalIndexes.INDEX_THIRD;
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

class MemeStageCommandTest extends ApplicationTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_stageMeme_success() {
        Meme memeToStage = model.getWeme().getMemeList().get(INDEX_FIRST.getZeroBased());
        expectedModel.stageMeme(memeToStage);

        MemeStageCommand memeStageCommand = new MemeStageCommand(INDEX_FIRST);

        String expectedMessage = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage);
        expectedModel.commitWeme(expectedMessage);
        assertCommandSuccess(memeStageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_stageMultipleMemes_success() {
        Meme memeToStage1 = model.getWeme().getMemeList().get(INDEX_FIRST.getZeroBased());
        Meme memeToStage2 = model.getWeme().getMemeList().get(INDEX_SECOND.getZeroBased());
        Meme memeToStage3 = model.getWeme().getMemeList().get(INDEX_THIRD.getZeroBased());

        String expectedMessage1 = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage1);
        String expectedMessage2 = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage2);
        String expectedMessage3 = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage3);

        MemeStageCommand memeStageCommand1 = new MemeStageCommand(INDEX_FIRST);
        MemeStageCommand memeStageCommand2 = new MemeStageCommand(INDEX_SECOND);
        MemeStageCommand memeStageCommand3 = new MemeStageCommand(INDEX_THIRD);

        expectedModel.stageMeme(memeToStage1);
        expectedModel.commitWeme(expectedMessage1);
        assertCommandSuccess(memeStageCommand1, model, expectedMessage1, expectedModel);

        expectedModel.stageMeme(memeToStage2);
        expectedModel.commitWeme(expectedMessage2);
        assertCommandSuccess(memeStageCommand2, model, expectedMessage2, expectedModel);

        expectedModel.stageMeme(memeToStage3);
        expectedModel.commitWeme(expectedMessage3);
        assertCommandSuccess(memeStageCommand3, model, expectedMessage3, expectedModel);
    }

    /**
     * Stage filtered list where index is larger than size of filtered list,
     * but smaller than size of the meme list.
     */
    @Test
    public void execute_invalidMemeIndexFilteredList_failure() {
        showMemeAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of weme list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWeme().getMemeList().size());

        MemeStageCommand memeStageCommand = new MemeStageCommand(outOfBoundIndex);

        assertCommandFailure(memeStageCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void execute_stageStagedMeme_failure() {
        Meme memeToStage = model.getWeme().getMemeList().get(INDEX_SECOND.getZeroBased());
        String expectedMessage = String.format(MemeStageCommand.MESSAGE_SUCCESS, memeToStage);
        model.stageMeme(memeToStage);
        model.commitWeme(expectedMessage);

        MemeStageCommand memeStageCommand = new MemeStageCommand(INDEX_SECOND);

        assertCommandFailure(memeStageCommand, model, MESSAGE_MEME_ALREADY_STAGED);
    }

    @Test
    public void equals() {
        MemeStageCommand stageFirstCommand = new MemeStageCommand(INDEX_FIRST);
        MemeStageCommand stageSecondCommand = new MemeStageCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(stageFirstCommand.equals(stageFirstCommand));

        // same values -> returns true
        MemeStageCommand stageFirstCommandCopy = new MemeStageCommand(INDEX_FIRST);
        assertTrue(stageFirstCommand.equals(stageFirstCommandCopy));

        // different types -> returns false
        assertFalse(stageFirstCommand.equals(1));

        // null -> returns false
        assertFalse(stageFirstCommand.equals(null));

        // different meme -> returns false
        assertFalse(stageFirstCommand.equals(stageSecondCommand));
    }

}
