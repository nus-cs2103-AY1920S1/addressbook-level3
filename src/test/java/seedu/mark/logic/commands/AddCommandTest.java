package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.commons.core.Messages.MESSAGE_FOLDER_NOT_FOUND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalFolderStructure;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Mark;
import seedu.mark.model.ModelStub;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

public class AddCommandTest {

    @Test
    public void constructor_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_bookmarkAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded();
        Bookmark validBookmark = new BookmarkBuilder().build();

        CommandResult commandResult = new AddCommand(validBookmark)
                .execute(modelStub, new StorageStub());

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
                addCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_nonexistentFolder_throwsCommandException() {
        Folder nonexistentFolder = new Folder("nonexistent");
        assertFalse(getTypicalFolderStructure().hasFolder(nonexistentFolder));
        Bookmark nonexistentFolderBookmark = new BookmarkBuilder().withFolder(nonexistentFolder.folderName).build();

        AddCommand addCommand = new AddCommand(nonexistentFolderBookmark);
        ModelStub modelStub = new ModelStubWithoutFolderAndBookmarks();

        assertThrows(CommandException.class, String.format(MESSAGE_FOLDER_NOT_FOUND, nonexistentFolder), () ->
                addCommand.execute(modelStub, new StorageStub()));
    }

    // TODO: bookmark gets tagged by autotagController

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
        public void applyAllTaggers() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void saveMark(String record) {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyMark getMark() {
            return new Mark();
        }

        @Override
        public boolean hasFolder(Folder folder) {
            return true;
        }
    }

    /**
     * A Model stub that contains no folders and bookmarks.
     */
    private class ModelStubWithoutFolderAndBookmarks extends ModelStub {

        @Override
        public boolean hasFolder(Folder folder) {
            return false;
        }

        @Override
        public boolean hasBookmark(Bookmark bookmark) {
            return false;
        }
    }

}
