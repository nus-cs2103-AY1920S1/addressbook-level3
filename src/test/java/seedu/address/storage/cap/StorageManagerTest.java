package seedu.address.storage.cap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.ReadOnlyCapLog;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private CapStorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCapStorage capLogStorage = new JsonCapStorage(getTempFilePath("ab"));
        JsonCapUserPrefsStorage userPrefsStorage = new JsonCapUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new CapStorageManager(capLogStorage, userPrefsStorage);
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
        CapUserPrefs original = new CapUserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        CapUserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void capLogReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModuleCapLogStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonModuleCapLogStorageTest} class.
         */
        CapLog original = getTypicalCapLog();
        storageManager.saveCapLog(original);
        ReadOnlyCapLog retrieved = storageManager.readCapLog().get();
        assertEquals(original, new CapLog(retrieved));
    }

    @Test
    public void getModuleCapLogFilePath() {
        assertNotNull(storageManager.getCapLogFilePath());
    }

}
