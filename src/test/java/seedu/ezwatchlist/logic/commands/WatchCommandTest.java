package seedu.ezwatchlist.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.WatchShowDescriptorBuilder;
import seedu.ezwatchlist.testutil.ShowBuilder;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_NAME_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

class WatchCommandTest {

    private Model model = new ModelManager(getTypicalWatchList(), new UserPrefs());

    @Test
    void execute_noFieldsSpecifiedUnfilteredList_success() {
        /*Show watchedShow = new ShowBuilder().withIsWatched(false).build();
        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(watchedShow).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, false);

        String expectedMessage = String.format(WatchCommand.MESSAGE_WATCH_SHOW_SUCCESS, watchedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(0), watchedShow);

        assertCommandSuccess(watchCommand, model, expectedMessage, expectedModel);
         */
    }
    /*
    @Test
    public void execute_invalidShowIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        EditCommand.EditShowDescriptor descriptor = new WatchShowDescriptorBuilder().withName(VALID_NAME_BOB_THE_BUILDER).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

     */
}