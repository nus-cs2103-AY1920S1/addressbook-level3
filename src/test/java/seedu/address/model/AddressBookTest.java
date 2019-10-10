package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYMENT_STATUS_XENIA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWorkers.XENIA;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.exceptions.DuplicateEntityException;
import seedu.address.model.entity.exceptions.EntityNotFoundException;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;
import seedu.address.testutil.WorkerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructorForWorker() {
        assertEquals(Collections.emptyList(), addressBook.getWorkerList());
    }

    @Test
    public void constructorForBody() {
        assertEquals(Collections.emptyList(), addressBook.getBodyList());
    }

    @Test
    public void constructorForFridge() {
        assertEquals(Collections.emptyList(), addressBook.getFridgeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateWorkers_throwsDuplicateEntityException() {
        // Two workers with the same identity fields
        Worker editedZach = new WorkerBuilder(ZACH).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_XENIA).build();
        List<Worker> newWorkers = Arrays.asList(ZACH, editedZach);
        AddressBookStub newData = new AddressBookStub(newWorkers);

        assertThrows(DuplicateEntityException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEntity(null));
    }

    @Test
    public void hasEntity_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEntity(ZACH));
    }

    @Test
    public void hasEntity_entityInAddressBook_returnsTrue() {
        addressBook.addEntity(ZACH);
        assertTrue(addressBook.hasEntity(ZACH));
    }

    @Test
    public void hasEntity_entityWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEntity(ZACH);
        Worker editedZach = new WorkerBuilder(ZACH).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_XENIA).build();
        assertTrue(addressBook.hasEntity(editedZach));
    }

    @Test
    public void remove_entityDoesNotExist_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> addressBook.removeEntity(ZACH));
    }

    @Test
    public void remove_existingEntity_success() {
        addressBook.addEntity(ZACH);
        addressBook.removeEntity(ZACH);
        AddressBook expectedAddressBook = new AddressBook();
        assertEquals(expectedAddressBook, addressBook);
    }

    @Test
    public void setEntity_editedEntityIsDifferentEntity_success() {
        addressBook.addEntity(ZACH);
        addressBook.setEntity(ZACH, XENIA);
        assertTrue(addressBook.hasEntity(XENIA));
        assertFalse(addressBook.hasEntity(ZACH));
    }

    @Test
    public void setEntity_editedEntityIsSameEntity_success() {
        addressBook.addEntity(ZACH);
        addressBook.setEntity(ZACH, ZACH);
        assertTrue(addressBook.hasEntity(ZACH));
        assertFalse(addressBook.hasEntity(XENIA));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void hashcode_sameAddressBook_equals() {
        AddressBook anotherAddressBook = new AddressBook();
        assertEquals(addressBook.hashCode(), anotherAddressBook.hashCode());
    }

    @Test
    public void toString_emptyAddressBook() {
        assertEquals("0 workers\n" + "0 bodies\n" + "0 fridges", addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Worker> workers = FXCollections.observableArrayList();
        private final ObservableList<Body> bodies = FXCollections.observableArrayList();
        private final ObservableList<Fridge> fridges = FXCollections.observableArrayList();

        AddressBookStub(Collection<Worker> workers) {
            this.workers.setAll(workers);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Worker> getWorkerList() {
            return workers;
        }

        @Override
        public ObservableList<Body> getBodyList() {
            return bodies;
        }

        @Override
        public ObservableList<Fridge> getFridgeList() {
            return fridges;
        }
    }

}
