package dukecooks.storage;

import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.core.GuiSettings;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.storage.dashboard.JsonDashboardStorage;
import dukecooks.storage.diary.JsonDiaryStorage;
import dukecooks.storage.health.JsonHealthRecordsStorage;
import dukecooks.storage.mealplan.JsonMealPlanBookStorage;
import dukecooks.storage.profile.JsonUserProfileStorage;
import dukecooks.storage.recipe.JsonRecipeBookStorage;
import dukecooks.storage.workout.JsonWorkoutCatalogueStorage;
import dukecooks.storage.workout.exercise.JsonExerciseCatalogueStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserProfileStorage userProfileStorage = new JsonUserProfileStorage(getTempFilePath("ab"));
        JsonHealthRecordsStorage healthRecordsStorage = new JsonHealthRecordsStorage(getTempFilePath("hr"));
        JsonRecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(getTempFilePath("ab"));
        JsonMealPlanBookStorage mealPlanBookStorage = new JsonMealPlanBookStorage(getTempFilePath("ab"));
        JsonExerciseCatalogueStorage workoutPlannerStorage = new JsonExerciseCatalogueStorage(getTempFilePath("ab"));
        JsonWorkoutCatalogueStorage workoutCatalogueStorage = new JsonWorkoutCatalogueStorage(getTempFilePath("ab"));
        JsonDiaryStorage diaryStorage = new JsonDiaryStorage(getTempFilePath("ab"));
        JsonDashboardStorage dashboardStorage = new JsonDashboardStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(userProfileStorage, healthRecordsStorage,
                recipeBookStorage, mealPlanBookStorage, workoutPlannerStorage, workoutCatalogueStorage, diaryStorage,
                dashboardStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void userPrefsReadSave() throws Exception {
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
    public void dukeCooksReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonDukeCooksStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonDukeCooksStorageTest} class.
         */
        UserProfile original = getTypicalProfiles();
        storageManager.saveUserProfile(original);
        ReadOnlyUserProfile retrieved = storageManager.readUserProfile().get();
        assertEquals(original, new UserProfile(retrieved));

        RecipeBook originalRecipeBook = getTypicalRecipeBook();
        storageManager.saveRecipeBook(originalRecipeBook);
        ReadOnlyRecipeBook retrievedRecipeBook = storageManager.readRecipeBook().get();
        assertEquals(originalRecipeBook, new RecipeBook(retrievedRecipeBook));

        MealPlanBook originalMealPlanBook = getTypicalMealPlanBook();
        storageManager.saveMealPlanBook(originalMealPlanBook);
        ReadOnlyMealPlanBook retrievedMealPlanBook = storageManager.readMealPlanBook().get();
        assertEquals(originalMealPlanBook, new MealPlanBook(retrievedMealPlanBook));

        HealthRecords originalHealthRecords = getTypicalHealthRecords();
        storageManager.saveHealthRecords(originalHealthRecords);
        ReadOnlyHealthRecords retrievedHealthRecords = storageManager.readHealthRecords().get();
        assertEquals(originalHealthRecords, new HealthRecords(retrievedHealthRecords));

        ExerciseCatalogue originalWorkoutPlanner = getTypicalWorkoutPlanner();
        storageManager.saveExerciseCatalogue(originalWorkoutPlanner);
        ReadOnlyExerciseCatalogue retrievedWorkoutPlanner = storageManager
                .readExerciseCatalogue().get();
        //assertEquals(originalWorkoutPlanner, new ExerciseCatalogue(retrievedWorkoutPlanner));

        DiaryRecords originalDiaryRecord = getTypicalDiaryRecords();
        storageManager.saveDiary(originalDiaryRecord);
        ReadOnlyDiary retrievedDiaryRecord = storageManager.readDiary().get();
        assertEquals(originalDiaryRecord, new DiaryRecords(retrievedDiaryRecord));
    }

    @Test
    public void getUserProfileFilePath() {
        assertNotNull(storageManager.getUserProfileFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getRecipesFilePath() {
        assertNotNull(storageManager.getRecipesFilePath());
    }

    @Test
    public void getHealthRecordsFilePath() {
        assertNotNull(storageManager.getHealthRecordsFilePath());
    }

    @Test
    public void getMealPlansFilePath() {
        assertNotNull(storageManager.getMealPlansFilePath());
    }

    @Test
    public void getExerciseFilePath() {
        assertNotNull(storageManager.getExerciseFilePath());
    }

    @Test
    public void getWorkoutFilePath() {
        assertNotNull(storageManager.getWorkoutFilePath());
    }

    @Test
    public void getDiaryFilePath() {
        assertNotNull(storageManager.getDiaryFilePath());
    }

    @Test
    public void getDashboardFilePath() {
        assertNotNull(storageManager.getDashboardFilePath());
    }

}
