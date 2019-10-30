package seedu.ezwatchlist.logic.commands;

import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;

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
        EditCommand.EditShowDescriptor descriptor =
                new WatchShowDescriptorBuilder().withName(VALID_NAME_BOB_THE_BUILDER).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

     */

    @Test
    public void equals() {
        /*
        final WatchCommand standardCommand =
                new WatchCommand(INDEX_FIRST_SHOW, WATCH_DESC_ANNABELLE, false, false);

        // same values -> returns true
        WatchCommand.WatchShowDescriptor copyDescriptor = new WatchCommand.WatchShowDescriptor(WATCH_DESC_ANNABELLE);
        WatchCommand commandWithSameValues =
                new WatchCommand(INDEX_FIRST_SHOW, copyDescriptor, false, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new WatchCommand(
                INDEX_SECOND_SHOW, WATCH_DESC_ANNABELLE, false, false)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new WatchCommand(
                INDEX_FIRST_SHOW, WATCH_DESC_BOB_THE_BUILDER, false, false)));
         */
    }
}
