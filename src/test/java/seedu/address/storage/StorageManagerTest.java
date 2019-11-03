package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPatientAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalStaffAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.AddressBook;
import seedu.address.model.userprefs.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("prefs"));
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.getUserPrefs();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook patientOriginal = getTypicalPatientAddressBook();
        AddressBook staffOriginal = getTypicalStaffAddressBook();
        storageManager.savePatientAddressBook(patientOriginal);
        storageManager.saveStaffAddressBook(staffOriginal);
        ReadOnlyAddressBook retrievedPatients = storageManager.readPatientAddressBook().get();
        assertEquals(patientOriginal, new AddressBook(retrievedPatients));
        ReadOnlyAddressBook retrievedStaff = storageManager.readStaffAddressBook().get();
        assertEquals(staffOriginal, new AddressBook(retrievedStaff));
    }

}
