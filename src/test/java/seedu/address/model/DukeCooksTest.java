package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalDukeCooks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class DukeCooksTest {

    private final DukeCooks dukeCooks = new DukeCooks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dukeCooks.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        DukeCooks newData = getTypicalDukeCooks();
        dukeCooks.resetData(newData);
        assertEquals(newData, dukeCooks);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        DukeCooksStub newData = new DukeCooksStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> dukeCooks.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInDukeCooks_returnsFalse() {
        assertFalse(dukeCooks.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInDukeCooks_returnsTrue() {
        dukeCooks.addPerson(ALICE);
        assertTrue(dukeCooks.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        dukeCooks.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(dukeCooks.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> dukeCooks.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose persons list can violate interface constraints.
     */
    private static class DukeCooksStub implements ReadOnlyDukeCooks {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        DukeCooksStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
