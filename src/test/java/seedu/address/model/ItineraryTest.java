package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class ItineraryTest {

    private final Itinerary itinerary = new Itinerary();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), itinerary.getPersonList());
    }

    @Test
    public void resetDataPerson_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> itinerary.resetDataPerson(null));
    }

    @Test
    public void resetDataPerson_withValidReadOnlyAddressBook_replacesData() {
        Itinerary newData = getTypicalAddressBook();
        itinerary.resetDataPerson(newData);
        assertEquals(newData, itinerary);
    }

    @Test
    public void resetDataPerson_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ItineraryStub newData = new ItineraryStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> itinerary.resetDataPerson(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> itinerary.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(itinerary.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        itinerary.addPerson(ALICE);
        assertTrue(itinerary.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        itinerary.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(itinerary.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> itinerary.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyItinerary whose persons list can violate interface constraints.
     */
    private static class ItineraryStub implements ReadOnlyItinerary {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();

        ItineraryStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Activity> getActivityList() {
            return activities; }

    }

}
