package seedu.algobase.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAlgoBaseStorage algoBaseStorage = new JsonAlgoBaseStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(algoBaseStorage, userPrefsStorage);
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
    public void algoBaseReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAlgoBaseStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAlgoBaseStorageTest} class.
         */
        AlgoBase original = getTypicalAlgoBase();
        storageManager.saveAlgoBase(original);
        ReadOnlyAlgoBase retrieved = storageManager.readAlgoBase().get();
        assertEquals(original, new AlgoBase(retrieved));
    }

    @Test
    public void getAlgoBaseFilePath() {
        assertNotNull(storageManager.getAlgoBaseFilePath());
    }

}
