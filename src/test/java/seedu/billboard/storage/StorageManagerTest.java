package seedu.billboard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonBillboardStorage billboardStorage = new JsonBillboardStorage(getTempFilePath("bb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(billboardStorage, userPrefsStorage);
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
    public void billboardReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonBillboardStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonBillboardStorageTest} class.
         */
        Billboard original = getTypicalBillboard();
        storageManager.saveBillboard(original);
        ReadOnlyBillboard retrieved = storageManager.readBillboard().get();
        assertEquals(original, new Billboard(retrieved));
    }

    @Test
    public void getBillboardFilePath() {
        assertNotNull(storageManager.getBillboardFilePath());
    }

}
