package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_showAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingShowAdded modelStub = new ModelStubAcceptingShowAdded();
        Show validShow = new ShowBuilder().build();

        CommandResult commandResult = new AddCommand(validShow).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validShow), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validShow), modelStub.showsAdded);
    }

    @Test
    public void execute_duplicateShow_throwsCommandException() {
        Show validShow = new ShowBuilder().build();
        AddCommand addCommand = new AddCommand(validShow);
        ModelStub modelStub = new ModelStubWithShow(validShow);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_SHOW, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Show Avenger = new ShowBuilder().withName("Avenger").build();
        Show bobthebuilder = new ShowBuilder().withName("Bob The Builder").build();
        AddCommand addAvengerCommand = new AddCommand(Avenger);
        AddCommand addBobCommand = new AddCommand(bobthebuilder);

        // same object -> returns true
        assertTrue(addAvengerCommand.equals(addAvengerCommand));

        // same values -> returns true
        AddCommand addShowCommandCopy = new AddCommand(Avenger);
        assertTrue(addAvengerCommand.equals(addAvengerCommand));

        // different types -> returns false
        assertFalse(addAvengerCommand.equals(1));

        // null -> returns false
        assertFalse(addAvengerCommand.equals(null));

        // different show -> returns false
        assertFalse(addAvengerCommand.equals(addBobCommand));
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
        public void setWatchListFilePath(Path watchListFilePath) {
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
        public ReadOnlyWatchList getWatchList() {
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
        public List<Show> getShowIfSameNameAs(Name showName) {
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
        public ObservableList<Show> getWatchedShowList() {
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
    }

    /**
     * A Model stub that contains a single show.
     */
    private class ModelStubWithShow extends ModelStub {
        private final Show show;

        ModelStubWithShow(Show show) {
            requireNonNull(show);
            this.show = show;
        }

        @Override
        public boolean hasShow(Show show) {
            requireNonNull(show);
            return this.show.isSameShow(show);
        }
    }

    /**
     * A Model stub that always accept the show being added.
     */
    private class ModelStubAcceptingShowAdded extends ModelStub {
        final ArrayList<Show> showsAdded = new ArrayList<>();

        @Override
        public boolean hasShow(Show show) {
            requireNonNull(show);
            return showsAdded.stream().anyMatch(show::isSameShow);
        }

        @Override
        public void addShow(Show show) {
            requireNonNull(show);
            showsAdded.add(show);
        }

        @Override
        public ReadOnlyWatchList getWatchList() {
            return new WatchList();
        }
    }

}
