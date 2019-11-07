package seedu.planner.model.accommodation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.ALICE;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.BENSON;
import static seedu.planner.testutil.contact.TypicalContacts.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.planner.model.accommodation.exceptions.AccommodationNotFoundException;
import seedu.planner.model.accommodation.exceptions.DuplicateAccommodationException;
import seedu.planner.testutil.accommodation.AccommodationBuilder;

public class UniqueAccommodationListTest {

    private final UniqueAccommodationList uniqueAccommodationList = new UniqueAccommodationList();

    @Test
    public void contains_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAccommodationList.contains(null));
    }

    @Test
    public void contains_accommodationNotInList_returnsFalse() {
        assertFalse(uniqueAccommodationList.contains(ALICE));
    }

    @Test
    public void contains_accommodationInList_returnsTrue() {
        uniqueAccommodationList.add(ALICE);
        assertTrue(uniqueAccommodationList.contains(ALICE));
    }

    @Test
    public void contains_accommodationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAccommodationList.add(ALICE);
        Accommodation editedAlice = new AccommodationBuilder(ALICE).withContact(BOB).build();
        assertTrue(uniqueAccommodationList.contains(editedAlice));
    }

    @Test
    public void add_nullAccommodation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAccommodationList.add(null));
    }

    @Test
    public void add_duplicateAccommodation_throwsDuplicateAccommodationException() {
        uniqueAccommodationList.add(ALICE);
        assertThrows(DuplicateAccommodationException.class, () -> uniqueAccommodationList.add(ALICE));
    }

    @Test
    public void setAccommodation_nullTargetAccommodation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAccommodationList.setAccommodation(null, ALICE));
    }

    @Test
    public void setAccommodation_nullEditedAccommodation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAccommodationList.setAccommodation(ALICE, null));
    }

    @Test
    public void setAccommodation_targetAccommodationNotInList_throwsAccommodationNotFoundException() {
        assertThrows(AccommodationNotFoundException.class, () ->
            uniqueAccommodationList.setAccommodation(ALICE, ALICE));
    }

    @Test
    public void setAccommodation_editedAccommodationIsSameAccommodation_success() {
        uniqueAccommodationList.add(ALICE);
        uniqueAccommodationList.setAccommodation(ALICE, ALICE);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        expectedUniqueAccommodationList.add(ALICE);
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodation_editedAccommodationHasSameIdentity_success() {
        uniqueAccommodationList.add(ALICE);
        Accommodation editedAlice = new AccommodationBuilder(ALICE).withContact(BOB).build();
        uniqueAccommodationList.setAccommodation(ALICE, editedAlice);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        expectedUniqueAccommodationList.add(editedAlice);
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodation_editedAccommodationHasDifferentIdentity_success() {
        uniqueAccommodationList.add(ALICE);
        uniqueAccommodationList.setAccommodation(ALICE, BENSON);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        expectedUniqueAccommodationList.add(BENSON);
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodation_editedAccommodationHasNonUniqueIdentity_throwsDuplicateAccommodationException() {
        uniqueAccommodationList.add(ALICE);
        uniqueAccommodationList.add(BENSON);
        assertThrows(DuplicateAccommodationException.class, () ->
            uniqueAccommodationList.setAccommodation(ALICE, BENSON));
    }

    @Test
    public void remove_nullAccommodation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAccommodationList.remove(null));
    }

    @Test
    public void remove_accommodationDoesNotExist_throwsAccommodationNotFoundException() {
        assertThrows(AccommodationNotFoundException.class, () -> uniqueAccommodationList.remove(ALICE));
    }

    @Test
    public void remove_existingAccommodation_removesAccommodation() {
        uniqueAccommodationList.add(ALICE);
        uniqueAccommodationList.remove(ALICE);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodations_nullUniqueAccommodationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueAccommodationList.setAccommodations((UniqueAccommodationList) null));
    }

    @Test
    public void setAccommodations_uniqueAccommodationList_replacesOwnListWithProvidedUniqueAccommodationList() {
        uniqueAccommodationList.add(ALICE);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        expectedUniqueAccommodationList.add(BENSON);
        uniqueAccommodationList.setAccommodations(expectedUniqueAccommodationList);
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodations_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniqueAccommodationList.setAccommodations((List<Accommodation>) null));
    }

    @Test
    public void setAccommodations_list_replacesOwnListWithProvidedList() {
        uniqueAccommodationList.add(ALICE);
        List<Accommodation> accommodationList = Collections.singletonList(BENSON);
        uniqueAccommodationList.setAccommodations(accommodationList);
        UniqueAccommodationList expectedUniqueAccommodationList = new UniqueAccommodationList();
        expectedUniqueAccommodationList.add(BENSON);
        assertEquals(expectedUniqueAccommodationList, uniqueAccommodationList);
    }

    @Test
    public void setAccommodations_listWithDuplicateAccommodations_throwsDuplicateAccommodationException() {
        List<Accommodation> listWithDuplicateAccommodations = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateAccommodationException.class, () ->
            uniqueAccommodationList.setAccommodations(listWithDuplicateAccommodations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueAccommodationList.asUnmodifiableObservableList().remove(0));
    }
}
