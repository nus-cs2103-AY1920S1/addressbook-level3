package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static seedu.address.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static seedu.address.testutil.profile.TypicalProfiles.getTypicalProfiles;
import static seedu.address.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.exercise.WorkoutPlanner;
import seedu.address.model.profile.ReadOnlyUserProfile;
import seedu.address.model.profile.UserProfile;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.model.recipe.RecipeBook;
import seedu.address.storage.diary.JsonDiaryStorage;
import seedu.address.storage.exercise.JsonWorkoutPlannerStorage;
import seedu.address.storage.health.JsonHealthRecordsStorage;
import seedu.address.storage.profile.JsonUserProfileStorage;
import seedu.address.storage.recipe.JsonRecipeBookStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserProfileStorage userProfileStorage = new JsonUserProfileStorage(getTempFilePath("ab"));
        JsonHealthRecordsStorage healthRecordsStorage = new JsonHealthRecordsStorage(getTempFilePath("hr"));
        JsonRecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(getTempFilePath("ab"));
        JsonWorkoutPlannerStorage workoutPlannerStorage = new JsonWorkoutPlannerStorage(getTempFilePath("ab"));
        JsonDiaryStorage diaryStorage = new JsonDiaryStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(userProfileStorage, healthRecordsStorage,
                recipeBookStorage, workoutPlannerStorage, diaryStorage, userPrefsStorage);
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
        assertEquals(original, new RecipeBook(retrievedRecipeBook));

        WorkoutPlanner originalWorkoutPlanner = getTypicalWorkoutPlanner();
        storageManager.saveWorkoutPlanner(originalWorkoutPlanner);
        ReadOnlyWorkoutPlanner retrievedWorkoutPlanner = storageManager
                .readWorkoutPlanner().get();
        assertEquals(original, new WorkoutPlanner(retrievedWorkoutPlanner));

        DiaryRecords originalDiaryRecord = getTypicalDiaryRecords();
        storageManager.saveDiary(originalDiaryRecord);
        ReadOnlyDiary retrievedDiaryRecord = storageManager.readDiary().get();
        assertEquals(original, new DiaryRecords(retrievedDiaryRecord));
    }

    @Test
    public void getDukeCooksFilePath() {
        assertNotNull(storageManager.getUserProfileFilePath());
    }

    @Test
    public void getRecipesFilePath() {
        assertNotNull(storageManager.getRecipesFilePath());
    }

}
