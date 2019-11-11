package seedu.system.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.system.testutil.Assert.assertThrows;
import static seedu.system.testutil.TypicalPersons.ALICE;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.system.model.exceptions.DuplicateElementException;
import seedu.system.model.person.Person;
import seedu.system.testutil.PersonBuilder;

public class DataTest {

    private final Data<Person> persons = new Data<>();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), persons.getListOfElements());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> persons.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlySystem_replacesData() {
        Data newData = getTypicalPersonData();
        persons.resetData(newData);
        assertEquals(newData, persons);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withDateOfBirth(VALID_DOB_BOB)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        DataStub newData = new DataStub(newPersons);

        assertThrows(DuplicateElementException.class, () -> persons.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> persons.hasUniqueElement(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(persons.hasUniqueElement(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        persons.addUniqueElement(ALICE);
        assertTrue(persons.hasUniqueElement(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        persons.addUniqueElement(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withDateOfBirth(VALID_DOB_BOB).build();
        assertTrue(persons.hasUniqueElement(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> persons.getListOfElements().remove(0));
    }

    /**
     * A stub ReadOnlyData whose persons list can violate interface constraints.
     */
    private static class DataStub implements ReadOnlyData {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        DataStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getListOfElements() {
            return persons;
        }
    }

}
