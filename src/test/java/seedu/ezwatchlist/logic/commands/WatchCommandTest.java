package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_NAME_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.WATCH_DESC_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.WATCH_DESC_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.TypicalShows;
import seedu.ezwatchlist.testutil.WatchShowDescriptorBuilder;

class WatchCommandTest {

    private Model model = new ModelManager(getTypicalWatchList(), new UserPrefs());

    @Test
    void execute_noFieldsSpecifiedUnfilteredList_success() {
        Show watchedShow = TypicalShows.WATCHEDJOKER;
        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(watchedShow).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, false);

        String expectedMessage = String.format(WatchCommand.MESSAGE_WATCH_SHOW_SUCCESS, watchedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), new UserPrefs());
        //Replacing the unwatched Joker in the list with the watched Joker
        expectedModel.setShow(model.getFilteredShowList().get(0), watchedShow);

        //Check that the Joker movie in the model is now marked as watched.
        assertCommandSuccess(watchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidShowIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        WatchCommand.WatchShowDescriptor descriptor =
                new WatchShowDescriptorBuilder().withName(VALID_NAME_BOB_THE_BUILDER).build();
        WatchCommand watchCommand = new WatchCommand(outOfBoundIndex, descriptor, false, false);

        assertCommandFailure(watchCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
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
    }
}
