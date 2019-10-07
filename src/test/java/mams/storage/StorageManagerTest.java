package mams.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import mams.commons.core.GuiSettings;
import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.UserPrefs;
import mams.testutil.TypicalStudents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonMamsStorage mamsStorage = new JsonMamsStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(mamsStorage, userPrefsStorage);
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
    public void mamsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMamsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMamsStorageTest} class.
         */
        Mams original = TypicalStudents.getTypicalMams();
        storageManager.saveMams(original);
        ReadOnlyMams retrieved = storageManager.readMams().get();
        assertEquals(original, new Mams(retrieved));
    }

    @Test
    public void getMamsFilePath() {
        assertNotNull(storageManager.getMamsFilePath());
    }

}
