package seedu.planner.model.accommodation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_SIGHTSEEING;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.ALICE;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.BENSON;
import static seedu.planner.testutil.contact.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.planner.testutil.accommodation.AccommodationBuilder;

public class AccommodationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Accommodation accommodation = new AccommodationBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> accommodation.getTags().remove(0));
    }

    @Test
    public void isSameAccommodationTest() {
        // same object -> returns true
        assertTrue(ALICE.isSameAccommodation(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameAccommodation(null));

        // different address -> returns false
        Accommodation editedAlice = new AccommodationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.isSameAccommodation(editedAlice));

        //different name -> returns false
        editedAlice = new AccommodationBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameAccommodation(editedAlice));

        //same name, same address, different attributes -> returns true
        editedAlice = new AccommodationBuilder(ALICE).withContact(BOB).withTags(VALID_TAG_SIGHTSEEING).build();
        assertTrue(ALICE.isSameAccommodation(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Accommodation aliceCopy = new AccommodationBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different accommodations -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        Accommodation editedAlice = new AccommodationBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new AccommodationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different contact -> return false
        editedAlice = new AccommodationBuilder(ALICE).withContact(BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new AccommodationBuilder(ALICE).withTags(VALID_TAG_SIGHTSEEING).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
