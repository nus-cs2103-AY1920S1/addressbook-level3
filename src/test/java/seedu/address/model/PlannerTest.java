package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalPlanner;

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

public class PlannerTest {

    private final Planner planner = new Planner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), planner.getContactList());
    }

    @Test
    public void resetDataContact_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> planner.resetDataContact(null));
    }

    @Test
    public void resetDataContact_withValidReadOnlyPlanner_replacesData() {
        Planner newData = getTypicalPlanner();
        planner.resetDataContact(newData);
        assertEquals(newData, planner);
    }

    @Test
    public void resetDataContact_withDuplicateContacts_throwsDuplicateContactException() {
        // Two contacts with the same identity fields
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        PlannerStub newData = new PlannerStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> planner.resetDataContact(newData));
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> planner.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInPlanner_returnsFalse() {
        assertFalse(planner.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInPlanner_returnsTrue() {
        planner.addContact(ALICE);
        assertTrue(planner.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInPlanner_returnsTrue() {
        planner.addContact(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(planner.hasContact(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> planner.getContactList().remove(0));
    }

    /**
     * A stub ReadOnlyPlanner whose contacts list can violate interface constraints.
     */
    private static class PlannerStub implements ReadOnlyPlanner {
        private final ObservableList<Accommodation> accommodations = FXCollections.observableArrayList();
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

        PlannerStub(Collection<Contact> contacts) {
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
