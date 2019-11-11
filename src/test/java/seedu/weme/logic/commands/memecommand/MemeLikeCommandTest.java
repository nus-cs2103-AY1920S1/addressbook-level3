package seedu.weme.logic.commands.memecommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.memecommand.MemeLikeCommand.MESSAGE_LIKE_MEME_SUCCESS;
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

class MemeLikeCommandTest {
    private Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());

    @Test
    void execute_likeSuccessful() {
        Meme memeToLike = model.getFilteredMemeList().get(INDEX_FIRST.getZeroBased());
        MemeLikeCommand command = new MemeLikeCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_LIKE_MEME_SUCCESS, memeToLike);

        expectedModel.incrementMemeLikeCount(memeToLike);
        expectedModel.commitWeme(expectedMessage);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_likeOutOfBoundIndex_fail() {
        List<Meme> lastShownList = model.getFilteredMemeList();
        Index outOfBoundIndex = Index.fromZeroBased(lastShownList.size() + 1);
        MemeLikeCommand likeOutOfBoundCommand = new MemeLikeCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> likeOutOfBoundCommand.execute(model),
                Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        MemeLikeCommand likeFirstCommand = new MemeLikeCommand(INDEX_FIRST);
        MemeLikeCommand likeSecondCommand = new MemeLikeCommand(INDEX_SECOND);

        // same object -> return true
        assertEquals(likeFirstCommand, likeFirstCommand);

        // same value -> return true
        MemeLikeCommand likeFirstCommandCopy = new MemeLikeCommand(INDEX_FIRST);
        assertEquals(likeFirstCommand, likeFirstCommandCopy);

        // different types -> return false
        assertNotEquals(likeFirstCommand, 1);

        // null -> return false
        assertNotEquals(likeFirstCommand, null);

        // different indices -> return false
        assertNotEquals(likeFirstCommand, likeSecondCommand);
    }
}
