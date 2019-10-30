package dukecooks.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.ReadOnlyUserPrefs;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;
import dukecooks.storage.dashboard.DashboardStorage;
import dukecooks.storage.diary.DiaryStorage;
import dukecooks.storage.exercise.WorkoutPlannerStorage;
import dukecooks.storage.health.HealthRecordsStorage;
import dukecooks.storage.mealplan.MealPlanBookStorage;
import dukecooks.storage.profile.UserProfileStorage;
import dukecooks.storage.recipe.RecipeBookStorage;

/**
 * API of the Storage component
 */
public interface Storage extends RecipeBookStorage, MealPlanBookStorage, UserPrefsStorage,
        UserProfileStorage, WorkoutPlannerStorage, HealthRecordsStorage, DiaryStorage, DashboardStorage {

    // ================ UserPrefs methods ==============================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // ================ UserProfile methods ==============================

    @Override
    Path getUserProfileFilePath();

    Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException;

    @Override
    void saveUserProfile(ReadOnlyUserProfile dukeCooks) throws IOException;

    // ================ Health Records methods ==============================

    @Override
    Path getHealthRecordsFilePath();

    @Override
    Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException;

    @Override
    void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException;

    // ================ Diary Records methods ==============================

    @Override
    Path getDiaryFilePath();

    @Override
    Optional<ReadOnlyDiary> readDiary() throws DataConversionException, IOException;

    @Override
    void saveDiary(ReadOnlyDiary diary) throws IOException;

    // ================ Recipe Book methods ==============================

    @Override
    Path getMealPlansFilePath();

    Optional<ReadOnlyMealPlanBook> readMealPlanBook() throws DataConversionException, IOException;

    void saveMealPlanBook(ReadOnlyMealPlanBook recipeBook) throws IOException;



    // ================ Meal Plan Book methods ==============================

    @Override
    Path getRecipesFilePath();

    Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException;

    void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException;

    // ================ Exercise methods ==============================

    @Override
    Path getWorkoutPlannerFilePath();

    @Override
    Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner() throws DataConversionException, IOException;

    @Override
    void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks) throws IOException;

    // ================ Dashboard methods ==============================

    @Override
    Path getDashboardFilePath();

    @Override
    Optional<ReadOnlyDashboard> readDashboard() throws DataConversionException, IOException;

    @Override
    void saveDashboard(ReadOnlyDashboard dukeCooks) throws IOException;
}
