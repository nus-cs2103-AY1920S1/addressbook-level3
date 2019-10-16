package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXERCISE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.ABS_ROLLOUT;
import static seedu.address.testutil.TypicalExercises.BURPEES;
import static seedu.address.testutil.TypicalProfiles.ALICE;
import static seedu.address.testutil.TypicalProfiles.BENSON;
import static seedu.address.testutil.TypicalRecipes.MILO;
import static seedu.address.testutil.TypicalRecipes.OMELETTE;
import static seedu.address.testutil.TypicalDiaries.ALL_MEAT;
import static seedu.address.testutil.TypicalDiaries.BOB_DIARY;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;
import seedu.address.model.diary.DiaryNameContainsKeywordsPredicate;
import seedu.address.testutil.DiaryRecordBuilder;
import seedu.address.testutil.DukeCooksBuilder;
import seedu.address.testutil.RecipeBookBuilder;
import seedu.address.testutil.WorkoutPlannerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
        assertEquals(new UserProfile(), new UserProfile(modelManager.getUserProfile()));
        assertEquals(new WorkoutPlanner(), new WorkoutPlanner(modelManager.getWorkoutPlanner()));
        assertEquals(new DiaryRecords(), new DiaryRecords(modelManager.getDiaryRecords()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setRecipesFilePath(Paths.get("address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("address/book/file/path"));
        userPrefs.setDiaryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setRecipesFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setExercisesFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setDiaryFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserProfileFilePath(null));
    }

    @Test
    public void setRecipesFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setRecipesFilePath(null));
    }

    @Test
    public void setDiaryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDiaryFilePath(null));
    }

    @Test
    public void setDukeCooksFilePath_validPath_setsDukeCooksFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setUserProfileFilePath(path);
        assertEquals(path, modelManager.getUserProfileFilePath());
    }

    @Test
    public void setRecipesFilePath_validPath_setsRecipesFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRecipesFilePath(path);
        assertEquals(path, modelManager.getRecipesFilePath());
    }

    @Test
    public void setDiaryFilePath_validPath_setsDiaryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDiaryFilePath(path);
        assertEquals(path, modelManager.getDiaryFilePath());
    }

    @Test
    public void hasDiary_nullDiary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDiary(null));
    }

    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasExercise(null));
    }

    @Test
    public void hasPerson_personNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasDiary_diaryNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasDiary(ALL_MEAT));
    }

    @Test
    public void hasPerson_personInDukeCooks_returnsTrue() {
        modelManager.addExercise(ABS_ROLLOUT);
        assertTrue(modelManager.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasDiary_diaryInDukeCooks_returnsTrue() {
        modelManager.addDiary(ALL_MEAT);
        assertTrue(modelManager.hasDiary(ALL_MEAT));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void getFilteredDiaryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDiaryList().remove(0));
    }

    @Test
    public void equals() {
        UserProfile userProfile = new DukeCooksBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserProfile differentUserProfile = new UserProfile();

        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(MILO).withRecipe(OMELETTE).build();
        RecipeBook differentRecipeBook = new RecipeBook();

        DiaryRecords diaryRecords = new DiaryRecordBuilder().withDiary(BOB_DIARY).build();
        DiaryRecords differentDiaryRecords = new DiaryRecords();

        UserPrefs userPrefs = new UserPrefs();

        WorkoutPlanner workoutPlanner = new WorkoutPlannerBuilder()
                .withExercise(ABS_ROLLOUT).withExercise(BURPEES).build();
        WorkoutPlanner differentPlanner = new WorkoutPlanner();

        // same values -> returns true
        modelManager = new ModelManager(recipeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(recipeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different recipeBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRecipeBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = MILO.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecipeList(new RecipeNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, userPrefs)));

        // different diaryRecords -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDiaryRecords, userPrefs)));

        // different DiaryRecordsFilteredList -> returns false
        String[] diaryKeywords = ALL_MEAT.getDiaryName().fullName.split("\\s+");
        modelManager.updateFilteredDiaryList(new DiaryNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(diaryRecords, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISE);
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        modelManager.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRecipesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, differentUserPrefs)));

        UserPrefs differentWorkoutPlannerUserPrefs = new UserPrefs();
        differentWorkoutPlannerUserPrefs.setExercisesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(workoutPlanner, differentWorkoutPlannerUserPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentDiaryUserPrefs = new UserPrefs();
        differentDiaryUserPrefs.setDiaryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(diaryRecords, differentDiaryUserPrefs)));
    }
}
