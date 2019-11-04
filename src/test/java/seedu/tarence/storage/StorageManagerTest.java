package seedu.tarence.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tarence.commons.core.GuiSettings;
//import seedu.tarence.model.Application;
//import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonApplicationStorage applicationStorage = new JsonApplicationStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonStateStorage jsonStateStorage = new JsonStateStorage("data", "states");
        storageManager = new StorageManager(applicationStorage, userPrefsStorage, jsonStateStorage);
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
    TODO: Implement integration test for storage manager

    @Test
    public void applicationReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonApplicationStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonApplicationStorageTest} class.
         */
    /*
        Application original = getTypicalApplication();
        storageManager.saveApplication(original);
        ReadOnlyApplication retrieved = storageManager.readApplication().get();
        assertEquals(original, new Application(retrieved));
    }
    */

    @Test
    public void getApplicationFilePath() {
        assertNotNull(storageManager.getApplicationFilePath());
    }

}
