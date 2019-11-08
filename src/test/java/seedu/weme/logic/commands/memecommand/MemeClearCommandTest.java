package seedu.weme.logic.commands.memecommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;

public class MemeClearCommandTest extends ApplicationTest {

    @Test
    public void execute_emptyWeme_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitWeme(MemeClearCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyWeme_success() {
        Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel.clearMemes();
        expectedModel.commitWeme(MemeClearCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_deleteStagedMeme_failure() {
        Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
        Meme memeInList = model.getWeme().getMemeList().get(INDEX_SECOND.getZeroBased());
        model.stageMeme(memeInList);
        assertCommandFailure(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_NON_EMPTY_STAGING_AREA);
    }

}
