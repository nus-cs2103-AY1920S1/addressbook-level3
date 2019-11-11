package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.module.commons.core.GuiSettings;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.UserPrefs;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.testutil.ArchivedModuleBuilder;

public class StorageManagerTest {
    private static final ArchivedModule ARCHIVED_MODULE = new ArchivedModuleBuilder().build();
    private static final ArchivedModuleList ARCHIVED_MODULE_LIST = new ArchivedModuleList();

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeAll
    public static void beforeAll() {
        ARCHIVED_MODULE_LIST.add(ARCHIVED_MODULE);
    }

    @BeforeEach
    public void setUp() {
        JsonModuleBookStorage moduleBookStorage = new JsonModuleBookStorage(getTempFilePath("mb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(moduleBookStorage, userPrefsStorage);
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
    public void moduleBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModuleBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonModuleBookStorageTest} class.
         */
        ModuleBook original = new ModuleBook(ARCHIVED_MODULE_LIST);
        storageManager.saveModuleBook(original);
        ReadOnlyModuleBook retrieved = storageManager.readModuleBook();
        assertEquals(original, new ModuleBook(retrieved));
    }

    @Test
    public void getModuleBookFilePath() {
        assertNotNull(storageManager.getModuleBookFilePath());
    }

}
