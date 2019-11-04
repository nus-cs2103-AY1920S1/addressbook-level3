package seedu.moolah.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.model.MooLah;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonMooLahStorage mooLahStorage = new JsonMooLahStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(mooLahStorage, userPrefsStorage);
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
    public void mooLahReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMooLahStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMooLahStorageTest} class.
         */
        MooLah original = getTypicalMooLah();
        storageManager.saveMooLah(original);
        ReadOnlyMooLah retrieved = storageManager.readMooLah().get();
        assertEquals(original, new MooLah(retrieved));
    }

    @Test
    public void getMooLahFilePath() {
        assertNotNull(storageManager.getMooLahFilePath());
    }

}
