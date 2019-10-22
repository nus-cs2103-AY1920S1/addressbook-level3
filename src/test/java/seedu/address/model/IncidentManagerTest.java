package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.testutil.PersonBuilder;

public class IncidentManagerTest {

    private final IncidentManager incidentManager = new IncidentManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), incidentManager.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> incidentManager.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        IncidentManager newData = getTypicalAddressBook();
        incidentManager.resetData(newData);
        assertEquals(newData, incidentManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        IncidentManagerStub newData = new IncidentManagerStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> incidentManager.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> incidentManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(incidentManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        incidentManager.addPerson(ALICE);
        assertTrue(incidentManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        incidentManager.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(incidentManager.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> incidentManager.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyIncidentManager whose persons list can violate interface constraints.
     */
    private static class IncidentManagerStub implements ReadOnlyIncidentManager {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Incident> incidents = FXCollections.observableArrayList();
        private final ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

        IncidentManagerStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Incident> getIncidentList() {
            return incidents;
        }

        @Override
        public ObservableList<Vehicle> getVehicleList() {
            return vehicles;
        }
    }

}
