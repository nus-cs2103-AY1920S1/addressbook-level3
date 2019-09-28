package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import thrift.commons.core.GuiSettings;
//import thrift.model.Thrift;
//import thrift.model.ReadOnlyThrift;
import thrift.model.UserPrefs;
//import thrift.testutil.TypicalTransactions;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonThriftStorage thriftStorage = new JsonThriftStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(thriftStorage, userPrefsStorage);
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

    /* TODO: Fix the test case when it is possible to read and parse from json file correctly.
    @Test
    public void thriftReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonThriftStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonThriftStorageTest} class.
         */ /*
        Thrift original = TypicalTransactions.getTypicalThrift();
        storageManager.saveThrift(original);
        ReadOnlyThrift retrieved = storageManager.readThrift().get();
        assertEquals(original, new Thrift(retrieved));
    }
     */

    @Test
    public void getThriftFilePath() {
        assertNotNull(storageManager.getThriftFilePath());
    }

}
