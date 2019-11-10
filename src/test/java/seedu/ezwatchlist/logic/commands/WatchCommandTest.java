package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.WATCH_DESC_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.WATCH_DESC_BOB_THE_BUILDER;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIFTH_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;
import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList2;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;
import seedu.ezwatchlist.testutil.TypicalShows;
import seedu.ezwatchlist.testutil.WatchShowDescriptorBuilder;

class WatchCommandTest {

    private Model model = new ModelManager(getTypicalWatchList2(), getDatabase(),new UserPrefs());
    private int validSeasonNum = 6;
    private int validEpisodeNum = 4;
    private int invalidSeasonNum = 100;
    private int invalidEpisodeNum = 1000;

    @Test
    void execute_noFieldsSpecifiedUnfilteredList_success() {
        //testing marking a show
        Show watchedShow = TypicalShows.WATCHEDJOKER;
        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(watchedShow).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, false);
        String expectedMarkMessage = String.format(WatchCommand.MESSAGE_WATCH_SHOW_SUCCESS, watchedShow);
        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(),new UserPrefs());
        //Replacing the unwatched Joker in the list with the watched Joker
        expectedModel.setShow(model.getFilteredShowList().get(0), watchedShow);
        //Check that the Joker movie in the model is now marked as watched.
        assertCommandSuccess(watchCommand, model, expectedMarkMessage, expectedModel);

        //Testing un-marking a show
        Show unwatchedShow = TypicalShows.JOKER;
        descriptor = new WatchShowDescriptorBuilder(unwatchedShow).build();
        watchCommand = new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, false);
        String expectedUnmarkMessage = String.format(WatchCommand.MESSAGE_UNWATCH_SHOW_SUCCESS, unwatchedShow);
        expectedModel.setShow(model.getFilteredShowList().get(0), unwatchedShow);
        assertCommandSuccess(watchCommand, model, expectedUnmarkMessage, expectedModel);
    }

    @Test
    public void execute_invalidShowIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredShowList().size() + 1);
        WatchCommand.WatchShowDescriptor descriptor =
                new WatchShowDescriptorBuilder().withName(VALID_SHOW_NAME_BOB_THE_BUILDER).build();
        WatchCommand watchCommand = new WatchCommand(outOfBoundIndex, descriptor, false, false);

        assertCommandFailure(watchCommand, model, Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editingEpisodesWatchedOfMovie_failure() {
        Show watchedShow = TypicalShows.WATCHEDJOKER;
        WatchCommand.WatchShowDescriptor descriptor =
                new WatchShowDescriptorBuilder(watchedShow).withNumOfEpisodesWatched(validEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIRST_SHOW, descriptor, false, true);
        String expectedMessage = WatchCommand.MESSAGE_EDITING_MOVIE_EPISODES_OR_SEASONS;
        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        //Replacing the unwatched Joker in the list with the watched Joker
        expectedModel.setShow(model.getFilteredShowList().get(0), watchedShow);
        //Check that the Joker movie in the model is now marked as watched.
        assertCommandFailure(watchCommand, model, expectedMessage);
    }

    @Test
    public void execute_validEpisodeNumber_pass() {
        Show show = TypicalShows.THEOFFICE;
        Show markedShow = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(validEpisodeNum).build();

        WatchCommand.WatchShowDescriptor descriptor =
                new WatchShowDescriptorBuilder(show).withNumOfEpisodesWatched(validEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIFTH_SHOW, descriptor, false, true);

        String expectedMessage = String.format(WatchCommand.MESSAGE_MARK_EPISODES_SUCCESS,
                markedShow.getNumOfEpisodesWatched(), markedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(4), markedShow);

        assertCommandSuccess(watchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEpisodeNumberAndSeasonNumber_pass() {
        Show show = TypicalShows.THEOFFICE;
        Show markedShow = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(100).build();

        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(show)
                .withNumOfSeasonsWatched(validSeasonNum).withNumOfEpisodesWatched(validEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIFTH_SHOW, descriptor, true, true);

        String expectedMessage = String.format(WatchCommand.MESSAGE_MARK_EPISODES_SUCCESS,
                markedShow.getNumOfEpisodesWatched(), markedShow);

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(4), markedShow);

        assertCommandSuccess(watchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSeasonNumber_failure() {
        Show show = TypicalShows.THEOFFICE;
        Show markedShow = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(validEpisodeNum).build();

        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(show)
                .withNumOfSeasonsWatched(invalidSeasonNum).withNumOfEpisodesWatched(validEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIFTH_SHOW, descriptor, true, true);

        String expectedMessage = String.format(WatchCommand.MESSAGE_INVALID_SEASON_NUMBER,
                markedShow.getNumOfSeasons(), markedShow.getName());

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(4), markedShow);

        assertCommandFailure(watchCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidEpisodeNumber_failure() {
        Show show = TypicalShows.THEOFFICE;
        Show markedShow = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(invalidEpisodeNum).build();

        WatchCommand.WatchShowDescriptor descriptor =
                new WatchShowDescriptorBuilder(show).withNumOfEpisodesWatched(invalidEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIFTH_SHOW, descriptor, false, true);

        String expectedMessage = String.format(WatchCommand.MESSAGE_INVALID_EPISODE_NUMBER,
                markedShow.getTotalNumOfEpisodes(), markedShow.getName());

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(4), markedShow);

        assertCommandFailure(watchCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidEpisodeNumberOfSeason_failure() {
        Show show = TypicalShows.THEOFFICE;
        Show markedShow = new ShowBuilder(TypicalShows.THEOFFICE).withNumOfEpisodesWatched(invalidEpisodeNum).build();

        WatchCommand.WatchShowDescriptor descriptor = new WatchShowDescriptorBuilder(show)
                .withNumOfSeasonsWatched(validSeasonNum).withNumOfEpisodesWatched(invalidEpisodeNum).build();
        WatchCommand watchCommand =
                new WatchCommand(INDEX_FIFTH_SHOW, descriptor, true, true);

        String expectedMessage = String.format(WatchCommand.MESSAGE_INVALID_EPISODE_NUMBER_OF_SEASON,
                markedShow.getTvSeasons().get(validSeasonNum - 1).getSeasonNum(),
                markedShow.getName(),
                markedShow.getNumOfEpisodesOfSeason(validSeasonNum));

        Model expectedModel = new ModelManager(new WatchList(model.getWatchList()), getDatabase(), new UserPrefs());
        expectedModel.setShow(model.getFilteredShowList().get(4), markedShow);

        assertCommandFailure(watchCommand, model, expectedMessage);
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
