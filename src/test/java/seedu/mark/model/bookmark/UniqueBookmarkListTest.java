package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.exceptions.BookmarkNotFoundException;
import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.testutil.BookmarkBuilder;

public class UniqueBookmarkListTest {

    private final UniqueBookmarkList uniqueBookmarkList = new UniqueBookmarkList();

    @Test
    public void contains_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.contains(null));
    }

    @Test
    public void contains_bookmarkNotInList_returnsFalse() {
        assertFalse(uniqueBookmarkList.contains(ALICE));
    }

    @Test
    public void contains_bookmarkInList_returnsTrue() {
        uniqueBookmarkList.add(ALICE);
        assertTrue(uniqueBookmarkList.contains(ALICE));
    }

    @Test
    public void contains_bookmarkWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookmarkList.add(ALICE);
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBookmarkList.contains(editedAlice));
    }

    @Test
    public void add_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.add(null));
    }

    @Test
    public void add_duplicateBookmark_throwsDuplicateBookmarkException() {
        uniqueBookmarkList.add(ALICE);
        assertThrows(DuplicateBookmarkException.class, () -> uniqueBookmarkList.add(ALICE));
    }

    @Test
    public void setBookmark_nullTargetBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmark(null, ALICE));
    }

    @Test
    public void setBookmark_nullEditedBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmark(ALICE, null));
    }

    @Test
    public void setBookmark_targetBookmarkNotInList_throwsBookmarkNotFoundException() {
        assertThrows(BookmarkNotFoundException.class, () -> uniqueBookmarkList.setBookmark(ALICE, ALICE));
    }

    @Test
    public void setBookmark_editedBookmarkIsSameBookmark_success() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.setBookmark(ALICE, ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(ALICE);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmark_editedBookmarkHasSameIdentity_success() {
        uniqueBookmarkList.add(ALICE);
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBookmarkList.setBookmark(ALICE, editedAlice);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(editedAlice);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmark_editedBookmarkHasDifferentIdentity_success() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.setBookmark(ALICE, BOB);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmark_editedBookmarkHasNonUniqueIdentity_throwsDuplicateBookmarkException() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.add(BOB);
        assertThrows(DuplicateBookmarkException.class, () -> uniqueBookmarkList.setBookmark(ALICE, BOB));
    }

    @Test
    public void remove_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.remove(null));
    }

    @Test
    public void remove_bookmarkDoesNotExist_throwsBookmarkNotFoundException() {
        assertThrows(BookmarkNotFoundException.class, () -> uniqueBookmarkList.remove(ALICE));
    }

    @Test
    public void remove_existingBookmark_removesBookmark() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.remove(ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmarks_nullUniqueBookmarkList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmarks((UniqueBookmarkList) null));
    }

    @Test
    public void setBookmarks_uniqueBookmarkList_replacesOwnListWithProvidedUniqueBookmarkList() {
        uniqueBookmarkList.add(ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        uniqueBookmarkList.setBookmarks(expectedUniqueBookmarkList);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmarks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmarks((List<Bookmark>) null));
    }

    @Test
    public void setBookmarks_list_replacesOwnListWithProvidedList() {
        uniqueBookmarkList.add(ALICE);
        List<Bookmark> bookmarkList = Collections.singletonList(BOB);
        uniqueBookmarkList.setBookmarks(bookmarkList);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setBookmarks_listWithDuplicateBookmarks_throwsDuplicateBookmarkException() {
        List<Bookmark> listWithDuplicateBookmarks = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateBookmarkException.class, () ->
                uniqueBookmarkList.setBookmarks(listWithDuplicateBookmarks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBookmarkList.asUnmodifiableObservableList().remove(0));
    }
}
