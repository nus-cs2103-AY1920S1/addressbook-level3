package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.testutil.BookmarkBuilder;

public class MarkTest {

    private final Mark mark = new Mark();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mark.getBookmarkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mark.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMark_replacesData() {
        Mark newData = getTypicalMark();
        mark.resetData(newData);
        assertEquals(newData, mark);
    }

    @Test
    public void resetData_withDuplicateBookmarks_throwsDuplicateBookmarkException() {
        // Two bookmarks with the same identity fields
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bookmark> newBookmarks = Arrays.asList(ALICE, editedAlice);
        MarkStub newData = new MarkStub(newBookmarks);

        assertThrows(DuplicateBookmarkException.class, () -> mark.resetData(newData));
    }

    @Test
    public void hasBookmark_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mark.hasBookmark(null));
    }

    @Test
    public void hasBookmark_bookmarkNotInMark_returnsFalse() {
        assertFalse(mark.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkInMark_returnsTrue() {
        mark.addBookmark(ALICE);
        assertTrue(mark.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_bookmarkWithSameIdentityFieldsInMark_returnsTrue() {
        mark.addBookmark(ALICE);
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(mark.hasBookmark(editedAlice));
    }

    @Test
    public void getBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> mark.getBookmarkList().remove(0));
    }

    /**
     * A stub ReadOnlyMark whose bookmarks list can violate interface constraints.
     */
    private static class MarkStub implements ReadOnlyMark {
        private final ObservableList<Bookmark> bookmarks = FXCollections.observableArrayList();

        MarkStub(Collection<Bookmark> bookmarks) {
            this.bookmarks.setAll(bookmarks);
        }

        @Override
        public ObservableList<Bookmark> getBookmarkList() {
            return bookmarks;
        }

        @Override
        public FolderStructure getFolderStructure() {
            return new FolderStructure(Folder.ROOT_FOLDER, FXCollections.observableArrayList());
        }
    }

}
