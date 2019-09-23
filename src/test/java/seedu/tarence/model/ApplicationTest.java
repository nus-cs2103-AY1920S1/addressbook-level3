package seedu.tarence.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalPersons.ALICE;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.person.exceptions.DuplicatePersonException;
import seedu.tarence.testutil.PersonBuilder;

public class ApplicationTest {

    private final Application application = new Application();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), application.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyApplication_replacesData() {
        Application newData = getTypicalApplication();
        application.resetData(newData);
        assertEquals(newData, application);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ApplicationStub newData = new ApplicationStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> application.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> application.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInApplication_returnsFalse() {
        assertFalse(application.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInApplication_returnsTrue() {
        application.addPerson(ALICE);
        assertTrue(application.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInApplication_returnsTrue() {
        application.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(application.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> application.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyApplication whose persons list can violate interface constraints.
     */
    private static class ApplicationStub implements ReadOnlyApplication {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ApplicationStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
