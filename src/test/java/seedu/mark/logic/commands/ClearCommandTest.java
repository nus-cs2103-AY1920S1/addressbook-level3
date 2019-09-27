package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarkManager;

import org.junit.jupiter.api.Test;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyBookmarkManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBookmarkManager_success() {
        Model model = new ModelManager(getTypicalBookmarkManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBookmarkManager(), new UserPrefs());
        expectedModel.setBookmarkManager(new BookmarkManager());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
