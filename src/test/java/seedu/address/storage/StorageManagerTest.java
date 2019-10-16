package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalDiaries.getTypicalDiaryRecords;
import static seedu.address.testutil.TypicalExercises.getTypicalWorkoutPlanner;
import static seedu.address.testutil.TypicalProfiles.getTypicalProfiles;
import static seedu.address.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.DiaryRecords;
import seedu.address.model.ReadOnlyDiary;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.RecipeBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserProfile;
import seedu.address.model.WorkoutPlanner;

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
