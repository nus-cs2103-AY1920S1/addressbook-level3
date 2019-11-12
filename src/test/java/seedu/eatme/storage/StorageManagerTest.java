package seedu.eatme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEateryListStorage eateryListStorage = new JsonEateryListStorage(getTempFilePath("el"));
        JsonFeedListStorage feedListStorage = new JsonFeedListStorage(getTempFilePath("fl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(eateryListStorage, feedListStorage, userPrefsStorage);
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
    public void eateryListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEateryListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEateryListStorageTest} class.
         */
        EateryList original = getTypicalOpenEateryList();
        storageManager.saveEateryList(original);
        ReadOnlyEateryList retrieved = storageManager.readEateryList().get();
        assertEquals(original, new EateryList(retrieved));
    }

    @Test
    public void getEateryListFilePath() {
        assertNotNull(storageManager.getEateryListFilePath());
    }

}
