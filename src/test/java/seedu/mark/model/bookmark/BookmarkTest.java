package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BOB;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.BookmarkBuilder;

public class BookmarkTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Bookmark bookmark = new BookmarkBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> bookmark.getTags().remove(0));
    }

    @Test
    public void isSameBookmark() {
        // same object -> returns true
        assertTrue(ALICE.isSameBookmark(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBookmark(null));

        // different name, different url -> returns false
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withName(VALID_NAME_BOB)
                .withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.isSameBookmark(editedAlice));

        // same name, different url -> returns true
        editedAlice = new BookmarkBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));

        // different name, same url -> returns true
        editedAlice = new BookmarkBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));

        // same name, same url, different attributes -> returns true
        editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bookmark aliceCopy = new BookmarkBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different bookmark -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different url -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different remark -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different folder -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withFolder(VALID_FOLDER_CS2103T).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
