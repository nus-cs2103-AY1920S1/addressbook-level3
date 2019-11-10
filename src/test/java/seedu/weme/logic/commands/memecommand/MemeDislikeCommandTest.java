package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.memecommand.MemeDislikeCommand.MESSAGE_DISLIKE_MEME_SUCCESS;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;

class MemeDislikeCommandTest {
    private Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());

    @Test
    void execute_dislikeSuccessful() {
        Meme memeToDislike = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeDislikeCommand command = new MemeDislikeCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_DISLIKE_MEME_SUCCESS, memeToDislike);

        expectedModel.incrementMemeDislikeCount(memeToDislike);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_likeOutOfBoundIndex_fail() {
        List<Meme> lastShownList = model.getFilteredMemeList();
        Index outOfBoundIndex = Index.fromZeroBased(lastShownList.size() + 1);
        MemeDislikeCommand dislikeOutOfBoundCommand = new MemeDislikeCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> dislikeOutOfBoundCommand.execute(model),
                Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        MemeDislikeCommand dislikeFirstCommand = new MemeDislikeCommand(INDEX_FIRST);
        MemeDislikeCommand dislikeSecondCommand = new MemeDislikeCommand(INDEX_SECOND);

        // same object -> return true
        assertEquals(dislikeFirstCommand, dislikeFirstCommand);

        // same value -> return true
        MemeDislikeCommand dislikeFirstCommandCopy = new MemeDislikeCommand(INDEX_FIRST);
        assertEquals(dislikeFirstCommand, dislikeFirstCommandCopy);

        // different types -> return false
        assertNotEquals(dislikeFirstCommand, 1);

        // null -> return false
        assertNotEquals(dislikeFirstCommand, null);

        // different indices -> return false
        assertNotEquals(dislikeFirstCommand, dislikeSecondCommand);
    }
}
