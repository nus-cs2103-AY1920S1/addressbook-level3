package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
//import seedu.address.model.ModulePlanner;
//import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonModulePlannerStorage modulePlannerStorage = new JsonModulePlannerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(getTempFilePath("modsInfo"));
        storageManager = new StorageManager(modulePlannerStorage, userPrefsStorage, modulesInfoStorage);
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

    //TODO implement
    @Test
    public void modulePlannerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModulePlannerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonModulePlannerStorageTest} class.
         */
        /*
        ModulePlanner original = getTypicalModulePlanner();
        storageManager.saveModulePlanner(original);
        ReadOnlyModulePlanner retrieved = storageManager.readModulePlanner().get();
        assertEquals(original, new ModulePlanner(retrieved));
        */
    }

    @Test
    public void getModulePlannerFilePath() {
        assertNotNull(storageManager.getModulePlannerFilePath());
    }

}
