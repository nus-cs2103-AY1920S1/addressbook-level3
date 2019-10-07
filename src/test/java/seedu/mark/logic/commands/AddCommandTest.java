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
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.BookmarkBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_bookmarkAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded();
        Bookmark validBookmark = new BookmarkBuilder().build();

        CommandResult commandResult = new AddCommand(validBookmark).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBookmark),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBookmark), modelStub.bookmarksAdded);
    }

    @Test
    public void execute_duplicateBookmark_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().build();
        AddCommand addCommand = new AddCommand(validBookmark);
        ModelStub modelStub = new ModelStubWithBookmark(validBookmark);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_BOOKMARK, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Bookmark alice = new BookmarkBuilder().withName("Alice").build();
        Bookmark bob = new BookmarkBuilder().withName("Bob").build();
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
        public Path getMarkFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMarkFilePath(Path markFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBookmark(Bookmark bookmark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMark(ReadOnlyMark newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMark getMark() {
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

        @Override
        public void createFolder(String folderName, String parentFolderName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single bookmark.
     */
    private class ModelStubWithBookmark extends ModelStub {
        private final Bookmark bookmark;

        ModelStubWithBookmark(Bookmark bookmark) {
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
    private class ModelStubAcceptingBookmarkAdded extends ModelStub {
        public final ArrayList<Bookmark> bookmarksAdded = new ArrayList<>();

        @Override
        public boolean hasBookmark(Bookmark bookmark) {
            requireNonNull(bookmark);
            return bookmarksAdded.stream().anyMatch(bookmark::isSameBookmark);
        }

        @Override
        public void addBookmark(Bookmark bookmark) {
            requireNonNull(bookmark);
            bookmarksAdded.add(bookmark);
        }

        @Override
        public ReadOnlyMark getMark() {
            return new Mark();
        }
    }

}
