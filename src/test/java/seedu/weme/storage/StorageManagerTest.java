package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.Weme;

public class StorageManagerTest extends ApplicationTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonWemeStorage wemeStorage = new JsonWemeStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(wemeStorage, userPrefsStorage);
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
    public void wemeReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonWemeStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonWemeStorageTest} class.
         */
        Weme original = getTypicalWeme();
        storageManager.saveWeme(original);
        ReadOnlyWeme retrieved = storageManager.readWeme().get();
        assertEquals(original, new Weme(retrieved));
    }

    @Test
    public void getWemeFolderPath() {
        assertNotNull(storageManager.getWemeFolderPath());
    }

}
