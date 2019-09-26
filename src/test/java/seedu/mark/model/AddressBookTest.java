package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalPersons.ALICE;
import static seedu.mark.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.testutil.PersonBuilder;

public class AddressBookTest {

    private final BookmarkManager addressBook = new BookmarkManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getBookmarkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BookmarkManager newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicateBookmarkException() {
        // Two bookmarks with the same identity fields
        Bookmark editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bookmark> newBookmarks = Arrays.asList(ALICE, editedAlice);
        BookmarkManagerStub newData = new BookmarkManagerStub(newBookmarks);

        assertThrows(DuplicateBookmarkException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasBookmark(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasBookmark(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addBookmark(ALICE);
        assertTrue(addressBook.hasBookmark(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addBookmark(ALICE);
        Bookmark editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasBookmark(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getBookmarkList().remove(0));
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
