package seedu.ezwatchlist.logic.commands;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyWatchList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyWatchList_success() {
        Model model = new ModelManager(getTypicalWatchList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalWatchList(), new UserPrefs());
        expectedModel.setWatchList(new WatchList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
