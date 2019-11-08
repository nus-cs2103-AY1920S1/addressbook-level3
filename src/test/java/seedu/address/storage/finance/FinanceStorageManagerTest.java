package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.UserPrefs;

public class FinanceStorageManagerTest {

    @TempDir
    public Path testFolder;

    private FinanceStorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFinanceStorage financeLogStorage = new JsonFinanceStorage(getTempFilePath("ab"));
        JsonFinanceUserPrefsStorage userPrefsStorage = new JsonFinanceUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new FinanceStorageManager(financeLogStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the FinanceStorageManager is properly wired to the
         * {@link JsonFinanceUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFinanceUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void financeLogReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the FinanceStorageManager is properly wired to the
         * {@link JsonFinanceStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFinanceStorageTest} class.
         */
        FinanceLog original = getTypicalFinanceLog();
        storageManager.saveFinanceLog(original);
        ReadOnlyFinanceLog retrieved = storageManager.readFinanceLog().get();
        assertEquals(original, new FinanceLog(retrieved));
    }

    @Test
    public void getFinanceLogFilePath() {
        assertNotNull(storageManager.getFinanceLogFilePath());
    }

}
