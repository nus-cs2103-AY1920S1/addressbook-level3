package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalPersons.ALICE;
import static seedu.mark.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.model.bookmark.exceptions.BookmarkNotFoundException;
import seedu.mark.testutil.PersonBuilder;

public class UniqueBookmarkListTest {

    private final UniqueBookmarkList uniqueBookmarkList = new UniqueBookmarkList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueBookmarkList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueBookmarkList.add(ALICE);
        assertTrue(uniqueBookmarkList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookmarkList.add(ALICE);
        Bookmark editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBookmarkList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicateBookmarkException() {
        uniqueBookmarkList.add(ALICE);
        assertThrows(DuplicateBookmarkException.class, () -> uniqueBookmarkList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmark(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmark(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsBookmarkNotFoundException() {
        assertThrows(BookmarkNotFoundException.class, () -> uniqueBookmarkList.setBookmark(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.setBookmark(ALICE, ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(ALICE);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueBookmarkList.add(ALICE);
        Bookmark editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBookmarkList.setBookmark(ALICE, editedAlice);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(editedAlice);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.setBookmark(ALICE, BOB);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicateBookmarkException() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.add(BOB);
        assertThrows(DuplicateBookmarkException.class, () -> uniqueBookmarkList.setBookmark(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsBookmarkNotFoundException() {
        assertThrows(BookmarkNotFoundException.class, () -> uniqueBookmarkList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueBookmarkList.add(ALICE);
        uniqueBookmarkList.remove(ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmarks((UniqueBookmarkList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueBookmarkList.add(ALICE);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        uniqueBookmarkList.setBookmarks(expectedUniqueBookmarkList);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookmarkList.setBookmarks((List<Bookmark>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueBookmarkList.add(ALICE);
        List<Bookmark> bookmarkList = Collections.singletonList(BOB);
        uniqueBookmarkList.setBookmarks(bookmarkList);
        UniqueBookmarkList expectedUniqueBookmarkList = new UniqueBookmarkList();
        expectedUniqueBookmarkList.add(BOB);
        assertEquals(expectedUniqueBookmarkList, uniqueBookmarkList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicateBookmarkException() {
        List<Bookmark> listWithDuplicateBookmarks = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateBookmarkException.class, () -> uniqueBookmarkList.setBookmarks(listWithDuplicateBookmarks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBookmarkList.asUnmodifiableObservableList().remove(0));
    }
}
