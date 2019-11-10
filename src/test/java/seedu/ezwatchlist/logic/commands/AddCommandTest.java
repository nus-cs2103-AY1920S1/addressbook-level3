package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullShow_throwsNullPointerException() {
        Show show = null;
        assertThrows(NullPointerException.class, () -> new AddCommand(show));
    }

    @Test
    public void check_static_fields() {
        final String messageusage = "add" + ": Adds a show to the watchlist. "
                + "Parameters: "
                + PREFIX_NAME + "NAME "
                + PREFIX_TYPE + "TYPE ('movie' or 'tv') "
                + "[" + PREFIX_DATE_OF_RELEASE + "DATE OF RELEASE] "
                + "[" + PREFIX_IS_WATCHED + "WATCHED ('true' or 'false')] "
                + "[" + PREFIX_RUNNING_TIME + "RUNNING TIME] "
                + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
                + "[" + PREFIX_ACTOR + "ACTOR]...\n"
                + "Example: " + "add" + " "
                + PREFIX_NAME + "Joker "
                + PREFIX_TYPE + "movie "
                + PREFIX_DATE_OF_RELEASE + "4 October 2019 "
                + PREFIX_IS_WATCHED + "true "
                + PREFIX_RUNNING_TIME + "122 "
                + PREFIX_DESCRIPTION + "Joker is funny "
                + PREFIX_ACTOR + "Joaquin Phoenix "
                + PREFIX_ACTOR + "Robert De Niro";
        assertEquals(AddCommand.MESSAGE_USAGE, messageusage);
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
    void fromSearch() {
    }

    @Test
    void testExecute() throws CommandException {
        ModelStubAcceptingShowAdded modelStub = new ModelStubAcceptingShowAdded();
        Show validShow = new ShowBuilder().build();

        CommandResult commandResult = new AddCommand(validShow).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validShow), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validShow), modelStub.showsAdded);
        Show validShow2 = new ShowBuilder().build();
        AddCommand addCommand = new AddCommand(validShow2);
        ModelStub modelStub2 = new ModelStubWithShow(validShow2);

        AddCommand fromsearch = new AddCommand(1);
        //assertEquals();
    }

    @Test
    void testFromSearch() {
    }

    @Test
    void testEquals() {
        Show avenger = new ShowBuilder().withName("Avenger").build();
        Show bobthebuilder = new ShowBuilder().withName("Bob The Builder").build();
        AddCommand addAvengerCommand = new AddCommand(avenger);
        AddCommand addBobCommand = new AddCommand(bobthebuilder);

        AddCommand addIndex0 = new AddCommand(1);
        AddCommand addIndex1 = new AddCommand(2);
        // same object -> returns true test using ==
        assertTrue(addAvengerCommand == addAvengerCommand);

        // same object -> returns true
        assertTrue(addAvengerCommand.equals(addAvengerCommand));

        // same values -> returns true
        AddCommand addShowCommandCopy = new AddCommand(avenger);
        assertTrue(addAvengerCommand.equals(addAvengerCommand));

        // different types -> returns false
        assertFalse(addAvengerCommand.equals(1));

        // null -> returns false
        assertFalse(addAvengerCommand.equals(null));

        // different show -> returns false
        assertFalse(addAvengerCommand.equals(addBobCommand));

        // same index -> returns true
        assertTrue(addIndex0.equals(addIndex0));

        // null -> returns false
        assertFalse(addIndex0.equals(null));
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
        public void syncMovie(List<Movie> syncMovie) {

        }

        @Override
        public String getPage(String shortCutKey) {
            return null;
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
