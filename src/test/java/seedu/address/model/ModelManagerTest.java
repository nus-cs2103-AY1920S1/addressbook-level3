package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.person.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getPatientAddressBook()));
        assertEquals(new AddressBook(), new AddressBook(modelManager.getStaffAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPatientAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setStaffAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setPatientAppointmentBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setDutyRosterBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPatientAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
        userPrefs.setStaffAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
        userPrefs.setPatientAppointmentBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
        userPrefs.setDutyRosterBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAppointmentBookFilePath_nullPath_throwsNullPointerException() {
        //assertThrows(NullPointerException.class, () -> modelManager.set
        //assertThrows(NullPointerException.class, () -> modelManager.setPatientAddressBook(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPatientAddressBook(null));
        assertThrows(NullPointerException.class, () -> modelManager.setStaffAddressBook(null));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson((Person) null));
    }

    @Test
    public void hasPerson_nullReferenceId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPatient((ReferenceId) null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
        assertFalse(modelManager.hasPatient(ALICE.getReferenceId()));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
        assertTrue(modelManager.hasPatient(ALICE.getReferenceId()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook patientAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook staffAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        AppointmentBook appointmentBook = new AppointmentBook();
        AppointmentBook dutyRosterBook = new AppointmentBook();
        QueueManager queueManager = new QueueManager();

        //appointmentBook.addEvent();

        // same values -> returns true
        modelManager = new ModelManager(patientAddressBook, staffAddressBook,
                appointmentBook, dutyRosterBook,
            userPrefs, queueManager);
        ModelManager modelManagerCopy = new ModelManager(patientAddressBook, staffAddressBook,
                appointmentBook, dutyRosterBook,
                userPrefs, queueManager);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different patient addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                new AddressBook(), staffAddressBook,
                appointmentBook, dutyRosterBook,
                userPrefs, queueManager)));

        // different staff addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                patientAddressBook, new AddressBook(),
                appointmentBook, dutyRosterBook,
                userPrefs, queueManager)));

        // different appointment Book -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                patientAddressBook, staffAddressBook,
                appointmentBook, new AppointmentBook(),
                userPrefs, queueManager)));

        // different queueManager -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                patientAddressBook, staffAddressBook,
                appointmentBook, dutyRosterBook,
                new UserPrefs(), queueManager)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientAddressBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setStaffAddressBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setPatientAppointmentBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setDutyRosterBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(patientAddressBook, staffAddressBook,
                appointmentBook, dutyRosterBook,
                differentUserPrefs, queueManager)));
    }
}
