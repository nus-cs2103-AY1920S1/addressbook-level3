package seedu.jarvis.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;
import static seedu.jarvis.testutil.cca.TypicalCcas.getTypicalCcaTracker;
import static seedu.jarvis.testutil.history.TypicalCommands.getTypicalHistoryManager;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.address.JsonAddressBookStorage;
import seedu.jarvis.storage.cca.JsonCcaTrackerStorage;
import seedu.jarvis.storage.history.JsonHistoryManagerStorage;
import seedu.jarvis.storage.userprefs.JsonUserPrefsStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(getTempFilePath("hm"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(getTempFilePath("ct"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, historyManagerStorage,
                ccaTrackerStorage);
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
    public void historyManagerReadSave() throws Exception {
        HistoryManager original = getTypicalHistoryManager();
        storageManager.saveHistoryManager(original);
        HistoryManager retrieved = storageManager.readHistoryManager().get();
        assertEquals(original, new HistoryManager(retrieved));
    }

    @Test
    public void getHistoryManagerFilePath() {
        assertNotNull(storageManager.getHistoryManagerFilePath());
    }

    @Test
    public void ccaTrackerReadSave() throws Exception {
        CcaTracker original = getTypicalCcaTracker();
        storageManager.saveCcaTracker(original);
        CcaTracker retrieved = storageManager.readCcaTracker().get();
        assertEquals(original, new CcaTracker(retrieved));
    }

    @Test
    public void getCcaTrackerFilePath() {
        assertNotNull(storageManager.getCcaTrackerFilePath());
    }
}
