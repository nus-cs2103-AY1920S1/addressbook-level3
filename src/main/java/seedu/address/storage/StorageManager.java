package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.health.ReadOnlyHealthRecords;
import seedu.address.model.profile.ReadOnlyUserProfile;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.storage.dashboard.DashboardStorage;
import seedu.address.storage.diary.DiaryStorage;
import seedu.address.storage.exercise.WorkoutPlannerStorage;
import seedu.address.storage.health.HealthRecordsStorage;
import seedu.address.storage.profile.UserProfileStorage;
import seedu.address.storage.recipe.RecipeBookStorage;

/**
 * Manages storage of DukeCooks data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserProfileStorage userProfileStorage;
    private HealthRecordsStorage healthRecordsStorage;
    private RecipeBookStorage recipeBookStorage;
    private WorkoutPlannerStorage workoutPlannerStorage;
    private DiaryStorage diaryStorage;
    private DashboardStorage dashboardStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(UserProfileStorage userProfileStorage, HealthRecordsStorage healthRecordsStorage,
                          RecipeBookStorage recipeBookStorage, WorkoutPlannerStorage workoutPlannerStorage,
                          DiaryStorage diaryStorage, DashboardStorage dashboardStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.userProfileStorage = userProfileStorage;
        this.healthRecordsStorage = healthRecordsStorage;
        this.recipeBookStorage = recipeBookStorage;
        this.workoutPlannerStorage = workoutPlannerStorage;
        this.diaryStorage = diaryStorage;
        this.dashboardStorage = dashboardStorage;
        this.userPrefsStorage = userPrefsStorage;
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


    // ================ UserProfile methods ==============================

    @Override
    public Path getUserProfileFilePath() {
        return userProfileStorage.getUserProfileFilePath();
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException {
        return readUserProfile(userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userProfileStorage.readUserProfile(filePath);
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile dukeCooks) throws IOException {
        saveUserProfile(dukeCooks, userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile dukeCooks, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userProfileStorage.saveUserProfile(dukeCooks, filePath);
    }

    // ================ Health Records methods ==============================

    @Override
    public Path getHealthRecordsFilePath() {
        return healthRecordsStorage.getHealthRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException {
        return readHealthRecords(healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthRecords> readHealthRecords(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthRecordsStorage.readHealthRecords(filePath);
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException {
        saveHealthRecords(healthRecords, healthRecordsStorage.getHealthRecordsFilePath());
    }

    @Override
    public void saveHealthRecords(ReadOnlyHealthRecords healthRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthRecordsStorage.saveHealthRecords(healthRecords, filePath);
    }

    // ================ RecipeBook methods ==============================

    @Override
    public Path getRecipesFilePath() {
        return recipeBookStorage.getRecipesFilePath();
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException {
        return readRecipeBook(recipeBookStorage.getRecipesFilePath());
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return recipeBookStorage.readRecipeBook(filePath);
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException {
        saveRecipeBook(recipeBook, recipeBookStorage.getRecipesFilePath());
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        recipeBookStorage.saveRecipeBook(recipeBook, filePath);
    }

    // ================ Workout Planner methods ==============================

    @Override
    public Path getWorkoutPlannerFilePath() {
        return workoutPlannerStorage.getWorkoutPlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner() throws DataConversionException, IOException {
        return readWorkoutPlanner(workoutPlannerStorage.getWorkoutPlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return workoutPlannerStorage.readWorkoutPlanner(filePath);
    }

    @Override
    public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner workoutPlanner) throws IOException {
        saveWorkoutPlanner(workoutPlanner, workoutPlannerStorage.getWorkoutPlannerFilePath());
    }

    @Override
    public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner workoutPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        workoutPlannerStorage.saveWorkoutPlanner(workoutPlanner, filePath);
    }

    // ================ DiaryRecords methods ==============================

    @Override
    public Path getDiaryFilePath() {
        return diaryStorage.getDiaryFilePath();
    }

    @Override
    public Optional<ReadOnlyDiary> readDiary() throws DataConversionException, IOException {
        return readDiary(diaryStorage.getDiaryFilePath());
    }

    @Override
    public Optional<ReadOnlyDiary> readDiary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return diaryStorage.readDiary(filePath);
    }

    @Override
    public void saveDiary(ReadOnlyDiary diary) throws IOException {
        saveDiary(diary, diaryStorage.getDiaryFilePath());
    }

    @Override
    public void saveDiary(ReadOnlyDiary diary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        diaryStorage.saveDiary(diary, filePath);
    }

    // ================ DashboardRecords methods ==============================

    @Override
    public Path getDashboardFilePath() {
        return dashboardStorage.getDashboardFilePath();
    }

    @Override
    public Optional<ReadOnlyDashboard> readDashboard() throws DataConversionException, IOException {
        return readDashboard(dashboardStorage.getDashboardFilePath());
    }


    @Override
    public Optional<ReadOnlyDashboard> readDashboard(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read dashboard data from file: " + filePath);
        return dashboardStorage.readDashboard(filePath);
    }

    @Override
    public void saveDashboard(ReadOnlyDashboard dashboard) throws IOException {
        saveDashboard(dashboard, dashboardStorage.getDashboardFilePath());
    }

    @Override
    public void saveDashboard(ReadOnlyDashboard dashboard, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        dashboardStorage.saveDashboard(dashboard, filePath);
    }

}
