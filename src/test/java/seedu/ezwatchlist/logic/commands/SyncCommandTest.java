package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.SyncCommand.MESSAGE_SUCCESS;
import static seedu.ezwatchlist.logic.commands.SyncCommand.MESSAGE_UNSUCCESFUL2;
import static seedu.ezwatchlist.logic.commands.SyncCommand.MESSAGE_UNSUCCESSFUL;
import static seedu.ezwatchlist.logic.commands.SyncCommand.MESSAGE_UNSUCCESSFUL3;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_SECOND_SHOW;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

public class SyncCommandTest {
    @Test
    void constructor_nullShow_throwsNullPointerException() {
        Index index = null;
        assertThrows(NullPointerException.class, () -> new SyncCommand(index));
    }

    @Test
    void testEquals() {
        Index index = null;
        Index index1 = INDEX_FIRST_SHOW;
        Index index2 = INDEX_SECOND_SHOW;

        SyncCommand syncCommand1 = new SyncCommand(index1);
        SyncCommand syncCommand2 = new SyncCommand(index2);

        // same object -> returns true test using ==
        assertTrue(syncCommand1 == syncCommand1);

        // same object -> returns true
        assertTrue(syncCommand1.equals(syncCommand1));

        // same values -> returns true
        SyncCommand addSyncCommandCopy = new SyncCommand(index1);
        assertTrue(addSyncCommandCopy.equals(syncCommand1));

        // different types -> returns false
        assertFalse(syncCommand1.equals(1));

        // null -> returns false
        assertFalse(syncCommand1.equals(null));

        // different show -> returns false
        assertFalse(syncCommand1.equals(syncCommand2));

    }

    @Test
    void execute() throws CommandException {

        //check for null model
        Model model = null;
        assertThrows(NullPointerException.class, () -> new SyncCommand(INDEX_FIRST_SHOW).execute(model));

        //check for sync command error: User input index is greater than search list size
        SyncCommand syncCommand = new SyncCommand(INDEX_FIRST_SHOW);
        WatchList watchList = new WatchList();
        WatchList database = new WatchList();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(watchList, database, userPrefs);
        assertThrows(CommandException.class, () -> syncCommand.execute(modelManager));


        Show superman = new ShowBuilder().withName("Superman").build();
        modelManager.addShow(superman);
        List<Show> search = new ArrayList<>();
        Show supermanSync = new ShowBuilder().withName("Superman").withRunningTime(125).build();
        search.add(new ShowBuilder().withName("Superman").withRunningTime(125).build());
        modelManager.updateSearchResultList(search);
        CommandResult commandResult = syncCommand.execute(modelManager);
        Assertions.assertEquals(String.format(MESSAGE_SUCCESS, supermanSync), commandResult.getFeedbackToUser());

        SyncCommand syncCommand1 = new SyncCommand(INDEX_FIRST_SHOW);
        List<Show> search2 = new ArrayList<>();
        search2.add(new ShowBuilder().build());
        modelManager.updateSearchResultList(search2);
        assertThrows(CommandException.class, MESSAGE_UNSUCCESSFUL + " "
                + MESSAGE_UNSUCCESFUL2 + "\n" + MESSAGE_UNSUCCESSFUL3, ()->
                syncCommand1.execute(modelManager));

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getWatchListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDatabaseFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWatchListFilePath(Path watchListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDatabaseFilePath(Path databaseFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addShow(Show show) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWatchList(ReadOnlyWatchList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDatabase(ReadOnlyWatchList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWatchList getWatchList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWatchList getDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasShow(Show show) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasShowName(Name showName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromWatchlistIfHasName(Name showName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromDatabaseIfHasName(Name showName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasActor(Set<Actor> actorSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromWatchlistIfIsGenre(Set<Genre> genreSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromDatabaseIfIsGenre(Set<Genre> genreSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromWatchlistIfHasActor(Set<Actor> actorSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Show> getShowFromDatabaseIfHasActor(Set<Actor> actorSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteShow(Show target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setShow(Show target, Show editedShow) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Show> getFilteredShowList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredShowList(Predicate<Show> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Show> getUnWatchedShowList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Show> getWatchedShowList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateUnWatchedShowList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateWatchedShowList() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Show> getSearchResultList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSearchResultList(List<Show> searchResult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getPage(String shortCutKey) {
            return null;
        }
    }

    private class ModelStubAcceptingShow extends ModelStub {
        private ArrayList<Show> showArrayList = new ArrayList<>();

        @Override
        public boolean hasShow(Show show) {
            requireNonNull(show);
            return showArrayList.stream().anyMatch(show::isSameShow);
        }

        @Override
        public void addShow(Show show) {
            requireNonNull(show);
            showArrayList.add(show);
        }

        @Override
        public ReadOnlyWatchList getWatchList() {
            return new WatchList();
        }
    }
}
