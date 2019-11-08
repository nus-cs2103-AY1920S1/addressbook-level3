package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.storage.cca.CcaTrackerStorage;
import seedu.jarvis.storage.course.CoursePlannerStorage;
import seedu.jarvis.storage.finance.FinanceTrackerStorage;
import seedu.jarvis.storage.history.HistoryManagerStorage;
import seedu.jarvis.storage.planner.PlannerStorage;
import seedu.jarvis.storage.userprefs.UserPrefsStorage;

/**
 * Manages storage of Jarvis data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private HistoryManagerStorage historyManagerStorage;
    private CcaTrackerStorage ccaTrackerStorage;
    private CoursePlannerStorage coursePlannerStorage;
    private PlannerStorage plannerStorage;
    private FinanceTrackerStorage financeTrackerStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage, HistoryManagerStorage historyManagerStorage,
                          CcaTrackerStorage ccaTrackerStorage, CoursePlannerStorage coursePlannerStorage,
                          PlannerStorage plannerStorage, FinanceTrackerStorage financeTrackerStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.historyManagerStorage = historyManagerStorage;
        this.ccaTrackerStorage = ccaTrackerStorage;
        this.coursePlannerStorage = coursePlannerStorage;
        this.plannerStorage = plannerStorage;
        this.financeTrackerStorage = financeTrackerStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ HistoryManager methods ===========================


    /**
     * Gets the file path of the data file for {@code HistoryManager}.
     *
     * @return File path of the data file for {@code HistoryManager}.
     */
    @Override
    public Path getHistoryManagerFilePath() {
        return historyManagerStorage.getHistoryManagerFilePath();
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager() throws DataConversionException, IOException {
        return historyManagerStorage.readHistoryManager(historyManagerStorage.getHistoryManagerFilePath());
    }

    /**
     * Returns {@code HistoryManager} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code HistoryManager} data.
     * @return {@code HistoryManager} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<HistoryManager> readHistoryManager(Path filePath) throws DataConversionException, IOException {
        return historyManagerStorage.readHistoryManager(filePath);
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager) throws IOException {
        saveHistoryManager(historyManager, historyManagerStorage.getHistoryManagerFilePath());
    }

    /**
     * Saves the given {@link HistoryManager} to the storage.
     *
     * @param historyManager {@code HistoryManager} to be saved, which cannot be null.
     * @param filePath       {@code Path} to read {@code HistoryManager} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveHistoryManager(HistoryManager historyManager, Path filePath) throws IOException {
        historyManagerStorage.saveHistoryManager(historyManager, filePath);
    }

    // ================ CcaTracker methods ===============================


    /**
     * Gets the file path of the data file for {@code CcaTracker}.
     *
     * @return File path of the data file for {@code CcaTracker}.
     */
    @Override
    public Path getCcaTrackerFilePath() {
        return ccaTrackerStorage.getCcaTrackerFilePath();
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker() throws DataConversionException, IOException {
        return ccaTrackerStorage.readCcaTracker(ccaTrackerStorage.getCcaTrackerFilePath());
    }

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CcaTracker} data.
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CcaTracker> readCcaTracker(Path filePath) throws DataConversionException, IOException {
        return ccaTrackerStorage.readCcaTracker(filePath);
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker) throws IOException {
        ccaTrackerStorage.saveCcaTracker(ccaTracker, ccaTrackerStorage.getCcaTrackerFilePath());
    }

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @param filePath   {@code Path} to read {@code CcaTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException {
        ccaTrackerStorage.saveCcaTracker(ccaTracker, filePath);
    }

    // ================ CoursePlanner methods ============================


    /**
     * Gets the file path of the data file for {@code CoursePlanner}.
     *
     * @return File path of the data file for {@code CoursePlanner}.
     */
    @Override
    public Path getCoursePlannerFilePath() {
        return coursePlannerStorage.getCoursePlannerFilePath();
    }

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CoursePlanner> readCoursePlanner() throws DataConversionException, IOException {
        return coursePlannerStorage.readCoursePlanner(coursePlannerStorage.getCoursePlannerFilePath());
    }

    /**
     * Returns {@code CoursePlanner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CoursePlanner} data.
     * @return {@code CoursePlanner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<CoursePlanner> readCoursePlanner(Path filePath) throws DataConversionException, IOException {
        return coursePlannerStorage.readCoursePlanner(filePath);
    }

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCoursePlanner(CoursePlanner coursePlanner) throws IOException {
        coursePlannerStorage.saveCoursePlanner(coursePlanner, coursePlannerStorage.getCoursePlannerFilePath());
    }

    /**
     * Saves the given {@link CoursePlanner} to the storage.
     *
     * @param coursePlanner {@code CoursePlanner} to be saved, which cannot be null.
     * @param filePath      {@code Path} to read {@code CoursePlanner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveCoursePlanner(CoursePlanner coursePlanner, Path filePath) throws IOException {
        coursePlannerStorage.saveCoursePlanner(coursePlanner, filePath);
    }

    // ================ Planner methods ==================================

    /**
     * Gets the file path of the data file for {@code Planner}.
     *
     * @return File path of the data file for {@code Planner}.
     */
    @Override
    public Path getPlannerFilePath() {
        return plannerStorage.getPlannerFilePath();
    }

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<Planner> readPlanner() throws DataConversionException, IOException {
        return plannerStorage.readPlanner(plannerStorage.getPlannerFilePath());
    }

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code Planner} data.
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<Planner> readPlanner(Path filePath) throws DataConversionException, IOException {
        return plannerStorage.readPlanner(filePath);
    }

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner {@code Planner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void savePlanner(Planner planner) throws IOException {
        plannerStorage.savePlanner(planner, plannerStorage.getPlannerFilePath());
    }

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner  {@code Planner} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code Planner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void savePlanner(Planner planner, Path filePath) throws IOException {
        plannerStorage.savePlanner(planner, filePath);
    }

    // ================ FinanceTracker methods ===========================


    /**
     * Gets the file path of the data file for {@code FinanceTracker}.
     *
     * @return File path of the data file for {@code FinanceTracker}.
     */
    @Override
    public Path getFinanceTrackerFilePath() {
        return financeTrackerStorage.getFinanceTrackerFilePath();
    }

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<FinanceTracker> readFinanceTracker() throws DataConversionException, IOException {
        return financeTrackerStorage.readFinanceTracker(financeTrackerStorage.getFinanceTrackerFilePath());
    }

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code FinanceTracker} data.
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<FinanceTracker> readFinanceTracker(Path filePath) throws DataConversionException, IOException {
        return financeTrackerStorage.readFinanceTracker(filePath);
    }

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveFinanceTracker(FinanceTracker financeTracker) throws IOException {
        financeTrackerStorage.saveFinanceTracker(financeTracker, financeTrackerStorage.getFinanceTrackerFilePath());
    }

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @param filePath       {@code Path} to read {@code FinanceTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void saveFinanceTracker(FinanceTracker financeTracker, Path filePath) throws IOException {
        financeTrackerStorage.saveFinanceTracker(financeTracker, filePath);
    }
}
