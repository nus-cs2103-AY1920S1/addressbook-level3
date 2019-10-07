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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.trip.Trip;
import seedu.address.testutil.PersonBuilder;

public class TravelPalTest {

    private final TravelPal travelPal = new TravelPal();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), travelPal.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> travelPal.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TravelPal newData = getTypicalAddressBook();
        travelPal.resetData(newData);
        assertEquals(newData, travelPal);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TravelPalStub newData = new TravelPalStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> travelPal.resetData(newData));
    }

    @Test
    public void

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> travelPal.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(travelPal.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        travelPal.addPerson(ALICE);
        assertTrue(travelPal.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        travelPal.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(travelPal.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> travelPal.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyTravelPal whose persons list can violate interface constraints.
     */
    @Disabled
    private static class TravelPalStub implements ReadOnlyTravelPal {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Trip> trips = FXCollections.observableArrayList();

        TravelPalStub(Collection<Person> persons, Collection<Trip> trips) {
            this.persons.setAll(persons);
            this.trips.setAll(trips);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Trip> getTripList() {
            return trips;
        }
    }

}
