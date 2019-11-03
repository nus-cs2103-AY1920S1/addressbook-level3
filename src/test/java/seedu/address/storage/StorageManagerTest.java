package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ActivityBook;
import seedu.address.model.AddressBook;
import seedu.address.model.InternalState;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonActivityBookStorage activityBookStorage =
                new JsonActivityBookStorage(getTempFilePath("acb"));
        JsonInternalStateStorage internalStateStorage =
                new JsonInternalStateStorage(getTempFilePath("state"));
        storageManager = new StorageManager(
                addressBookStorage, userPrefsStorage, internalStateStorage, activityBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void stateReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInternalStateStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInternalStateStorageTest} class.
         */
        InternalState original = new InternalState();
        storageManager.saveInternalState(original);
        InternalState retrieved = storageManager.readInternalState().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(512.0, 700.0, 12, 31));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getActivityBookFilePath() {
        assertNotNull(storageManager.getActivityBookFilePath());
    }

    @Test
    public void activityBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonActivityBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonActivityBookStorageTest} class.
         */
        ActivityBook original = getTypicalActivityBook();
        storageManager.saveActivityBook(original);
        ActivityBook retrieved = storageManager.readActivityBook().get();
        assertEquals(original, new ActivityBook(retrieved));
    }

}
