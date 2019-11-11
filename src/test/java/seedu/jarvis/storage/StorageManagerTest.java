package seedu.jarvis.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.jarvis.testutil.cca.TypicalCcas.getTypicalCcaTracker;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCoursePlanner;
import static seedu.jarvis.testutil.finance.TypicalFinances.getTypicalFinanceTracker;
import static seedu.jarvis.testutil.history.TypicalCommands.getTypicalHistoryManager;
import static seedu.jarvis.testutil.planner.TypicalTasks.getTypicalPlanner;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.cca.JsonCcaTrackerStorage;
import seedu.jarvis.storage.course.JsonCoursePlannerStorage;
import seedu.jarvis.storage.finance.JsonFinanceTrackerStorage;
import seedu.jarvis.storage.history.JsonHistoryManagerStorage;
import seedu.jarvis.storage.planner.JsonPlannerStorage;
import seedu.jarvis.storage.userprefs.JsonUserPrefsStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonHistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(getTempFilePath("hm"));
        JsonCcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(getTempFilePath("ct"));
        JsonCoursePlannerStorage coursePlannerStorage = new JsonCoursePlannerStorage(getTempFilePath("cp"));
        JsonPlannerStorage plannerStorage = new JsonPlannerStorage(getTempFilePath("p"));
        JsonFinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(getTempFilePath("ft"));
        storageManager = new StorageManager(userPrefsStorage, historyManagerStorage, ccaTrackerStorage,
                coursePlannerStorage, plannerStorage, financeTrackerStorage);
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
    public void historyManagerReadSave() throws Exception {
        HistoryManager original = getTypicalHistoryManager();
        storageManager.saveHistoryManager(original);
        HistoryManager retrieved = storageManager.readHistoryManager().get();
        assertEquals(original, new HistoryManager(retrieved));
    }

    @Test
    public void getHistoryManagerFilePath() {
        assertNotNull(storageManager.getHistoryManagerFilePath());
    }

    @Test
    public void ccaTrackerReadSave() throws Exception {
        CcaTracker original = getTypicalCcaTracker();
        storageManager.saveCcaTracker(original);
        CcaTracker retrieved = storageManager.readCcaTracker().get();
        assertEquals(original, new CcaTracker(retrieved));
    }

    @Test
    public void getCcaTrackerFilePath() {
        assertNotNull(storageManager.getCcaTrackerFilePath());
    }

    @Test
    public void coursePlannerReadSave() throws Exception {
        CoursePlanner original = getTypicalCoursePlanner();
        storageManager.saveCoursePlanner(original);
        CoursePlanner retrieved = storageManager.readCoursePlanner().get();
        assertEquals(original, new CoursePlanner(retrieved));
    }

    @Test
    public void getCoursePlannerFilePath() {
        assertNotNull(storageManager.getCoursePlannerFilePath());
    }

    @Test
    public void plannerReadSave() throws Exception {
        Planner original = getTypicalPlanner();
        storageManager.savePlanner(original);
        Planner retrieved = storageManager.readPlanner().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void getPlannerFilePath() {
        assertNotNull(storageManager.getPlannerFilePath());
    }

    @Test
    public void financeTrackerReadSave() throws Exception {
        FinanceTracker original = getTypicalFinanceTracker();
        storageManager.saveFinanceTracker(original);
        FinanceTracker retrieved = storageManager.readFinanceTracker().get();
        assertEquals(original.getPurchaseList(), retrieved.getPurchaseList());
        assertEquals(original, retrieved);
    }

    @Test
    public void getFinanceTrackerFilePath() {
        assertNotNull(storageManager.getFinanceTrackerFilePath());
    }

}
