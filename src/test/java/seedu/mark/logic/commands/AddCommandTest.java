package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Bookmark validBookmark = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validBookmark).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBookmark), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBookmark), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Bookmark validBookmark = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validBookmark);
        ModelStub modelStub = new ModelStubWithPerson(validBookmark);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_BOOKMARK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Bookmark alice = new PersonBuilder().withName("Alice").build();
        Bookmark bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public Path getBookmarkManagerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookmarkManagerFilePath(Path bookmarkManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBookmark(Bookmark bookmark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookmarkManager(ReadOnlyBookmarkManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBookmarkManager getBookmarkManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBookmark(Bookmark bookmark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBookmark(Bookmark target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBookmark(Bookmark target, Bookmark editedBookmark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Bookmark> getFilteredBookmarkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookmarkList(Predicate<Bookmark> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single bookmark.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Bookmark bookmark;

        ModelStubWithPerson(Bookmark bookmark) {
            requireNonNull(bookmark);
            this.bookmark = bookmark;
        }

        @Override
        public boolean hasBookmark(Bookmark bookmark) {
            requireNonNull(bookmark);
            return this.bookmark.isSameBookmark(bookmark);
        }
    }

    /**
     * A Model stub that always accept the bookmark being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Bookmark> personsAdded = new ArrayList<>();

        @Override
        public boolean hasBookmark(Bookmark bookmark) {
            requireNonNull(bookmark);
            return personsAdded.stream().anyMatch(bookmark::isSameBookmark);
        }

        @Override
        public void addBookmark(Bookmark bookmark) {
            requireNonNull(bookmark);
            personsAdded.add(bookmark);
        }

        @Override
        public ReadOnlyBookmarkManager getBookmarkManager() {
            return new BookmarkManager();
        }
    }

}
