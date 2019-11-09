package dukecooks.model;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;
import static dukecooks.testutil.dashboard.TypicalDashboard.TASK2;
import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.BOB_DIARY;
import static dukecooks.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static dukecooks.testutil.exercise.TypicalExercises.BURPEES;
import static dukecooks.testutil.profile.TypicalProfiles.ALICE;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static dukecooks.testutil.recipe.TypicalRecipes.OMELETTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.GuiSettings;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.testutil.Assert;
import dukecooks.testutil.dashboard.DashboardRecordBuilder;
import dukecooks.testutil.diary.DiaryRecordBuilder;
import dukecooks.testutil.exercise.WorkoutPlannerBuilder;
import dukecooks.testutil.profile.UserProfileBuilder;
import dukecooks.testutil.recipe.RecipeBookBuilder;
import dukecooks.testutil.recipe.RecipeBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DashboardRecords(), new DashboardRecords(modelManager.getDashboardRecords()));
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
        assertEquals(new MealPlanBook(), new MealPlanBook(modelManager.getMealPlanBook()));
        assertEquals(new UserProfile(), new UserProfile(modelManager.getUserProfile()));
        assertEquals(new ExerciseCatalogue(), new ExerciseCatalogue(modelManager.getExerciseCatalogue()));
        assertEquals(new DiaryRecords(), new DiaryRecords(modelManager.getDiaryRecords()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDashboardFilePath(Paths.get("address/book/file/path"));
        userPrefs.setRecipesFilePath(Paths.get("address/book/file/path"));
        userPrefs.setMealPlansFilePath(Paths.get("address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("address/book/file/path"));
        userPrefs.setDiaryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDashboardFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setRecipesFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setMealPlansFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setExercisesFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setDiaryFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserProfileFilePath(null));
    }

    @Test
    public void setDashboardFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setDashboardFilePath(null));
    }

    @Test
    public void setRecipesFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setRecipesFilePath(null));
    }

    @Test
    public void retrieveRecipe_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.retrieveRecipe(null));
    }

    @Test
    public void retrieveRecipesFilePathSuccess() {
        modelManager.addRecipe(new RecipeBuilder().build());
        Recipe recipe = new RecipeBuilder().build();
        modelManager.retrieveRecipe(recipe);
        assertEquals(recipe, modelManager.retrieveRecipe(recipe));
    }

    @Test
    public void setMealPlansFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setMealPlansFilePath(null));
    }

    @Test
    public void setDiaryFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setDiaryFilePath(null));
    }

    @Test
    public void setDukeCooksFilePath_validPath_setsDukeCooksFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setUserProfileFilePath(path);
        assertEquals(path, modelManager.getUserProfileFilePath());
    }

    @Test
    public void setDashboardFilePath_validPath_setsDashboardFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDashboardFilePath(path);
        assertEquals(path, modelManager.getDashboardFilePath());
    }

    @Test
    public void setRecipesFilePath_validPath_setsRecipesFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRecipesFilePath(path);
        assertEquals(path, modelManager.getRecipesFilePath());
    }

    @Test
    public void setMealPlansFilePath_validPath_setsMealPlansFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setMealPlansFilePath(path);
        assertEquals(path, modelManager.getMealPlansFilePath());
    }

    @Test
    public void setDiaryFilePath_validPath_setsDiaryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDiaryFilePath(path);
        assertEquals(path, modelManager.getDiaryFilePath());
    }

    @Test
    public void hasDashboard_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasDashboard(null));
    }

    @Test
    public void hasDiary_nullDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasDiary(null));
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasRecipe(null));
    }

    @Test
    public void hasMealPlan_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasMealPlan(null));
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
    public void hasRecipe_recipeNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasRecipe(OMELETTE));
    }

    @Test
    public void hasMealPlan_mealPlanNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasRecipe(OMELETTE));
    }
    //TODO: finish mealplan testutil

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
    public void hasRecipe_recipeInDukeCooks_returnsTrue() {
        modelManager.addRecipe(OMELETTE);
        assertTrue(modelManager.hasRecipe(OMELETTE));
    }

    @Test
    public void hasMealPlan_mealPlanInDukeCooks_returnsTrue() {
        modelManager.addRecipe(OMELETTE);
        assertTrue(modelManager.hasRecipe(OMELETTE));
    }

    @Test
    public void hasDashboard_dashboardInDukeCooks_returnsTrue() {
        modelManager.addDashboard(TASK1);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void getFilteredMealPlanList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMealPlanList()
                .remove(0));
    }

    @Test
    public void getFilteredDiaryList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDiaryList().remove(0));
    }

    @Test
    public void equals() {
        UserProfile userProfile = new UserProfileBuilder().withPerson(ALICE).build();
        UserProfile differentUserProfile = new UserProfile();

        DashboardRecords dashboardRecords = new DashboardRecordBuilder()
                .withDashboard(TASK1).withDashboard(TASK2).build();
        DashboardRecords differentDashboardRecords = new DashboardRecords();

        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(MILO).withRecipe(OMELETTE).build();
        RecipeBook differentRecipeBook = new RecipeBook();

        //TODO: finish mealplan testutil

        DiaryRecords diaryRecords = new DiaryRecordBuilder().withDiary(BOB_DIARY).build();
        DiaryRecords differentDiaryRecords = new DiaryRecords();

        UserPrefs userPrefs = new UserPrefs();

        ExerciseCatalogue workoutPlanner = new WorkoutPlannerBuilder()
                .withExercise(ABS_ROLLOUT).withExercise(BURPEES).build();
        ExerciseCatalogue differentPlanner = new ExerciseCatalogue();

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

        // different dashboardRecords -> returns false
        assertNotEquals(modelManager, new ModelManager(differentDashboardRecords, userPrefs));

        // different DashboardRecordsFilteredList -> returns false
        String[] dashboardKeywords = TASK1.getDashboardName().fullName.split("\\s+");
        modelManager.updateFilteredDashboardList(new
            DashboardNameContainsKeywordsPredicate(Arrays.asList(dashboardKeywords)));
        assertNotEquals(modelManager, new ModelManager(dashboardRecords, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExerciseList(Model.PREDICATE_SHOW_ALL_EXERCISE);
        modelManager.updateFilteredRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);
        modelManager.updateFilteredDiaryList(Model.PREDICATE_SHOW_ALL_DIARIES);
        modelManager.updateFilteredDashboardList(Model.PREDICATE_SHOW_ALL_DASHBOARD);

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

        // different userPrefs -> returns false
        UserPrefs differentDashboardUserPrefs = new UserPrefs();
        differentDashboardUserPrefs.setDashboardFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(dashboardRecords, differentDashboardUserPrefs)));
    }
}
