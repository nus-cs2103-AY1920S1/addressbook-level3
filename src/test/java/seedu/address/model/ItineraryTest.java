package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.testutil.ContactBuilder;

public class ItineraryTest {

    private final Itinerary itinerary = new Itinerary();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), itinerary.getContactList());
    }

    @Test
    public void resetDataContact_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> itinerary.resetDataContact(null));
    }

    @Test
    public void resetDataContact_withValidReadOnlyItinerary_replacesData() {
        Itinerary newData = getTypicalAddressBook();
        itinerary.resetDataContact(newData);
        assertEquals(newData, itinerary);
    }

    @Test
    public void resetDataContact_withDuplicateContacts_throwsDuplicateContactException() {
        // Two contacts with the same identity fields
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        ItineraryStub newData = new ItineraryStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> itinerary.resetDataContact(newData));
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> itinerary.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInItinerary_returnsFalse() {
        assertFalse(itinerary.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInItinerary_returnsTrue() {
        itinerary.addContact(ALICE);
        assertTrue(itinerary.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInItinerary_returnsTrue() {
        itinerary.addContact(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(itinerary.hasContact(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> itinerary.getContactList().remove(0));
    }

    /**
     * A stub ReadOnlyItinerary whose contacts list can violate interface constraints.
     */
    private static class ItineraryStub implements ReadOnlyItinerary {
        private final ObservableList<Accommodation> accommodations = FXCollections.observableArrayList();
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

        ItineraryStub(Collection<Contact> contacts) {
            this.contacts.setAll(contacts);
        }

        @Override
        public ObservableList<Accommodation> getAccommodationList() {
            return accommodations; }

        @Override
        public ObservableList<Activity> getActivityList() {
            return activities; }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }



    }

}
