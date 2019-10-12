package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonMemeBookStorage memeBookStorage = new JsonMemeBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(memeBookStorage, userPrefsStorage);
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
    public void memeBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMemeBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMemeBookStorageTest} class.
         */
        MemeBook original = getTypicalMemeBook();
        storageManager.saveMemeBook(original);
        ReadOnlyMemeBook retrieved = storageManager.readMemeBook().get();
        assertEquals(original, new MemeBook(retrieved));
    }

    @Test
    public void getMemeBookFilePath() {
        assertNotNull(storageManager.getMemeBookFilePath());
    }

}
