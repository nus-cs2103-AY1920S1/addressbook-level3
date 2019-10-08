package calofit.storage;

import calofit.commons.core.GuiSettings;
import calofit.model.DishDatabase;
import calofit.model.ReadOnlyDishDatabase;
import calofit.model.UserPrefs;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonDishDatabaseStorage dishDatabaseStorage = new JsonDishDatabaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(dishDatabaseStorage, userPrefsStorage);
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
    public void dishDatabaseReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonDishDatabaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonDishDatabaseStorageTest} class.
         */
        DishDatabase original = TypicalDishes.getTypicalDishDatabase();
        storageManager.saveDishDatabase(original);
        ReadOnlyDishDatabase retrieved = storageManager.readDishDatabase().get();
        assertEquals(original, new DishDatabase(retrieved));
    }

    @Test
    public void getDishDatabaseFilePath() {
        assertNotNull(storageManager.getDishDatabaseFilePath());
    }

}
