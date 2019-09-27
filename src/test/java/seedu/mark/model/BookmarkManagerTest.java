package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarkManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.testutil.BookmarkBuilder;

public class BookmarkManagerTest {

    private final BookmarkManager bookmarkManager = new BookmarkManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bookmarkManager.getBookmarkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyBookmarkManager_replacesData() {
        BookmarkManager newData = getTypicalBookmarkManager();
        bookmarkManager.resetData(newData);
        assertEquals(newData, bookmarkManager);
    }

    @Test
    public void resetData_withDuplicateBookmarks_throwsDuplicateBookmarkException() {
        // Two bookmarks with the same identity fields
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bookmark> newBookmarks = Arrays.asList(ALICE, editedAlice);
        BookmarkManagerStub newData = new BookmarkManagerStub(newBookmarks);

        assertThrows(DuplicateBookmarkException.class, () -> bookmarkManager.resetData(newData));
    }

    @Test
    public void hasBookmark_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookmarkManager.hasBookmark(null));
    }

    @Test
    public void hasBookmark_bookmarkNotInBookmarkManager_returnsFalse() {
        assertFalse(bookmarkManager.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkInBookmarkManager_returnsTrue() {
        bookmarkManager.addBookmark(ALICE);
        assertTrue(bookmarkManager.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkWithSameIdentityFieldsInBookmarkManager_returnsTrue() {
        bookmarkManager.addBookmark(ALICE);
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(bookmarkManager.hasBookmark(editedAlice));
    }

    @Test
    public void getBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> bookmarkManager.getBookmarkList().remove(0));
    }

    /**
     * A stub ReadOnlyBookmarkManager whose bookmarks list can violate interface constraints.
     */
    private static class BookmarkManagerStub implements ReadOnlyBookmarkManager {
        private final ObservableList<Bookmark> bookmarks = FXCollections.observableArrayList();

        BookmarkManagerStub(Collection<Bookmark> bookmarks) {
            this.bookmarks.setAll(bookmarks);
        }

        @Override
        public ObservableList<Bookmark> getBookmarkList() {
            return bookmarks;
        }
    }

}
