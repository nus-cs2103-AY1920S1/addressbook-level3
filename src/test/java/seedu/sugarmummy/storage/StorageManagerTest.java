package seedu.sugarmummy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.storage.bio.JsonUserListStorage;
import sugarmummy.recmfood.storage.JsonFoodListStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonUserListStorage userListStorage = new JsonUserListStorage(getTempFilePath("userList"));
        JsonFoodListStorage jsonFoodListStorage = new JsonFoodListStorage(getTempFilePath("fl"));
        JsonRecordListStorage jsonRecordListStorage = new JsonRecordListStorage(getTempFilePath("rl"));
        JsonCalendarStorage jsonCalendarStorage = new JsonCalendarStorage(getTempFilePath("el"), getTempFilePath("rl"));
        storageManager = new StorageManager(userPrefsStorage, userListStorage, jsonFoodListStorage,
                jsonRecordListStorage, jsonCalendarStorage);
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


}
