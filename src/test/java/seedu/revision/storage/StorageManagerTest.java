package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.revision.testutil.TypicalMcqs.getTypicalRevisionTool;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonRevisionToolStorage revisionToolStorage = new JsonRevisionToolStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonHistoryStorage historyStorage = new JsonHistoryStorage(getTempFilePath("hist"));
        storageManager = new StorageManager(revisionToolStorage, userPrefsStorage, historyStorage);
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonRevisionToolStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonRevisionToolStorageTest} class.
         */
        RevisionTool original = getTypicalRevisionTool();
        storageManager.saveRevisionTool(original);
        ReadOnlyRevisionTool retrieved = storageManager.readRevisionTool().get();
        assertEquals(original, new RevisionTool(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getRevisionToolFilePath());
    }

}
