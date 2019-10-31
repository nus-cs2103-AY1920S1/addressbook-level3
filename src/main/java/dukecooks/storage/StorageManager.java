package dukecooks.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.ReadOnlyUserPrefs;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.WorkoutCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.storage.dashboard.DashboardStorage;
import dukecooks.storage.diary.DiaryStorage;
import dukecooks.storage.workout.WorkoutCatalogueStorage;
import dukecooks.storage.workout.exercise.ExerciseCatalogueStorage;
import dukecooks.storage.health.HealthRecordsStorage;
import dukecooks.storage.mealplan.MealPlanBookStorage;
import dukecooks.storage.profile.UserProfileStorage;
import dukecooks.storage.recipe.RecipeBookStorage;

/**
 * Manages storage of DukeCooks data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserProfileStorage userProfileStorage;
    private HealthRecordsStorage healthRecordsStorage;
    private RecipeBookStorage recipeBookStorage;
    private MealPlanBookStorage mealPlanBookStorage;
    private ExerciseCatalogueStorage exerciseCatalogueStorage;
    private WorkoutCatalogueStorage workoutCatalogueStorage;
    private DiaryStorage diaryStorage;
    private DashboardStorage dashboardStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(UserProfileStorage userProfileStorage, HealthRecordsStorage healthRecordsStorage,
                          RecipeBookStorage recipeBookStorage, MealPlanBookStorage mealPlanBookStorage,
                          ExerciseCatalogueStorage exerciseCatalogueStorage,
                          WorkoutCatalogueStorage workoutCatalogueStorage,
                          DiaryStorage diaryStorage, DashboardStorage dashboardStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.userProfileStorage = userProfileStorage;
        this.healthRecordsStorage = healthRecordsStorage;
        this.recipeBookStorage = recipeBookStorage;
        this.mealPlanBookStorage = mealPlanBookStorage;
        this.exerciseCatalogueStorage = exerciseCatalogueStorage;
        this.workoutCatalogueStorage = workoutCatalogueStorage;
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

    // ================ MealPlanBook methods ==============================

    @Override
    public Path getMealPlansFilePath() {
        return mealPlanBookStorage.getMealPlansFilePath();
    }

    @Override
    public Optional<ReadOnlyMealPlanBook> readMealPlanBook() throws DataConversionException, IOException {
        return readMealPlanBook(mealPlanBookStorage.getMealPlansFilePath());
    }

    @Override
    public Optional<ReadOnlyMealPlanBook> readMealPlanBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mealPlanBookStorage.readMealPlanBook(filePath);
    }

    @Override
    public void saveMealPlanBook(ReadOnlyMealPlanBook recipeBook) throws IOException {
        saveMealPlanBook(recipeBook, mealPlanBookStorage.getMealPlansFilePath());
    }

    @Override
    public void saveMealPlanBook(ReadOnlyMealPlanBook recipeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mealPlanBookStorage.saveMealPlanBook(recipeBook, filePath);
    }

    // ================ Exercise Catalogue methods ==============================

    @Override
    public Path getExerciseFilePath() {
        return exerciseCatalogueStorage.getExerciseFilePath();
    }

    @Override
    public Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue() throws DataConversionException, IOException {
        return readExerciseCatalogue(exerciseCatalogueStorage.getExerciseFilePath());
    }

    public Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue(Path exerciseFilePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + exerciseFilePath);
        return exerciseCatalogueStorage.readExerciseCatalogue(exerciseFilePath);
    }

    @Override
    public void saveExerciseCatalogue(ReadOnlyExerciseCatalogue workoutPlanner) throws IOException {
        saveExerciseCatalogue(workoutPlanner, exerciseCatalogueStorage.getExerciseFilePath());
    }

    @Override
    public void saveExerciseCatalogue(ReadOnlyExerciseCatalogue workoutPlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        exerciseCatalogueStorage.saveExerciseCatalogue(workoutPlanner, filePath);
    }

    // ================ Workout Catalogue methods ==============================

    @Override
    public Path getWorkoutFilePath() {
        return workoutCatalogueStorage.getWorkoutFilePath();
    }

    @Override
    public Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue() throws DataConversionException, IOException {
        return readWorkoutCatalogue(workoutCatalogueStorage.getWorkoutFilePath());
    }

    public Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue(Path workoutFilePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + workoutFilePath);
        return workoutCatalogueStorage.readWorkoutCatalogue(workoutFilePath);
    }

    @Override
    public void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue) throws IOException {
        saveWorkoutCatalogue(workoutCatalogue, workoutCatalogueStorage.getWorkoutFilePath());
    }

    @Override
    public void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        workoutCatalogueStorage.saveWorkoutCatalogue(workoutCatalogue, filePath);
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
