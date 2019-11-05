package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserState;


public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserStateStorage userStateStorage = new JsonUserStateStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(userStateStorage, userPrefsStorage);
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

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserStateStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserStateStorageTest} class.
     */
    @Test
    public void userStateReadSave() throws Exception {

        UserState original = getTypicalUserState();
        storageManager.saveUserState(original);
        ReadOnlyUserState retrieved = storageManager.readUserState().get();
        assertEquals(original, new UserState(retrieved));
    }


    @Test
    public void getUserStateFilePath() {
        assertNotNull(storageManager.getUserStateFilePath());
    }

}
