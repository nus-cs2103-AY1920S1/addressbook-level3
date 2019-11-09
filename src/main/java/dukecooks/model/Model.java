package dukecooks.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import dukecooks.commons.core.GuiSettings;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.health.components.Record;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<MealPlan> PREDICATE_SHOW_ALL_MEALPLANS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<MealPlan> PREDICATE_SHOW_NO_MEALPLANS = unused -> false;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISE = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Workout> PREDICATE_SHOW_ALL_WORKOUT = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Diary> PREDICATE_SHOW_ALL_DIARIES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Dashboard> PREDICATE_SHOW_ALL_DASHBOARD = unused -> true;

    /**
     * {@code Predicate} is true if the task is completed
     */
    Predicate<Dashboard> PREDICATE_SHOW_DONE_DASHBOARD = i -> (i.getTaskStatus().getDoneStatus());

    /**
     * {@code Predicate} is true if the task is incomplete
     */
    Predicate<Dashboard> PREDICATE_SHOW_NOT_DONE_DASHBOARD = i -> (i.getTaskStatus().getNotDoneStatus());

    //=========== UserPrefs ==================================================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' UserProfile file path.
     */
    Path getUserProfileFilePath();

    /**
     * Sets the user prefs' UserProfile file path.
     */
    void setUserProfileFilePath(Path userProfileFilePath);

    /**
     * Returns the user prefs' Health Records file path.
     */
    Path getHealthRecordsFilePath();

    /**
     * Sets the user prefs' Health Records file path.
     */
    void setHealthRecordsFilePath(Path healthRecordsFilePath);

    //Recipe Book
    /**
     * Returns the user prefs' RecipeBook file path.
     */
    Path getRecipesFilePath();

    /**
     * Sets the user prefs' Duke Cooks Recipe file path.
     */
    void setRecipesFilePath(Path recipesFilePath);

    //Recipe Book
    /**
     * Returns the user prefs' MealPlanBook file path.
     */
    Path getMealPlansFilePath();

    /**
     * Sets the user prefs' Duke Cooks Meal Plan file path.
     */
    void setMealPlansFilePath(Path recipesFilePath);


    /**
     * Returns the user prefs' Workout Planner file path.
     */
    Path getWorkoutPlannerFilePath();

    /**
     * Sets the user prefs' Workout Planner file path.
     */
    void setWorkoutPlannerFilePath(Path workoutPlannerFilePath);

    /**
     * Returns the user prefs' DiaryRecords file path.
     */
    Path getDiaryFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDiaryFilePath(Path diaryFilePath);

    /**
     * Returns the user prefs' DashboardRecords file path.
     */
    Path getDashboardFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDashboardFilePath(Path dashboardFilePath);

    //=========== User Profile ================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code userProfile}.
     */
    void setUserProfile(ReadOnlyUserProfile userProfile);

    /** Returns UserProfile */
    ReadOnlyUserProfile getUserProfile();

    /**
     * Returns true if a profile exists in Duke Cooks.
     */
    boolean hasProfile();

    /**
     * Adds the given person.
     * {@code person} must not already exist in User Profile.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in User Profile.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the User Profile.
     */
    void setPerson(Person target, Person editedPerson);

    //=========== Health Records ============================================================================

    /**
     * Replaces Health Records data with the data in {@code healthRecords}.
     */
    void setHealthRecords(ReadOnlyHealthRecords healthRecords);

    /** Returns Health Records */
    ReadOnlyHealthRecords getHealthRecords();

    /**
     * Returns true if a record with the same identity as {@code record} exists in Duke Cooks.
     */
    boolean hasRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in Duke Cooks.
     */
    void deleteRecord(Record record);

    /**
     * Adds the given record.
     * {@code record} must not already exist in Health Records.
     */
    void addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in Heath Records.
     * The record identity of {@code editedRecord} must not be the same as another existing record in Health Records.
     */
    void setRecord(Record target, Record editedRecord);

    //=========== Recipe Book  ================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code recipeBook}.
     */
    void setRecipeBook(ReadOnlyRecipeBook recipeBook);

    /** Returns RecipeBook */
    ReadOnlyRecipeBook getRecipeBook();

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in Duke Cooks.
     */
    boolean hasRecipe(Recipe recipe);

    /**
     * Deletes the given recipe.
     * The recipe must exist in Duke Cooks.
     */
    void deleteRecipe(Recipe target);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in Duke Cooks.
     */
    void addRecipe(Recipe recipe);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in Duke Cooks.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the Duke Cooks.
     */
    void setRecipe(Recipe target, Recipe editedRecipe);

    /**
     * Searches the given {@code recipe} within {@code recipeBook} for {@code Recipe} with matching name.
     * {@code recipe} must exist in Duke Cooks.
     * Returns {@code Recipe} found within {@code recipeBook}.
     */
    Recipe retrieveRecipe(Recipe recipe);

    //=========== Meal Plan Book  ================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code mealPlanBook}.
     */
    void setMealPlanBook(ReadOnlyMealPlanBook mealPlanBook);

    /** Returns MealPlanBook */
    ReadOnlyMealPlanBook getMealPlanBook();

    /**
     * Returns true if a meal plan with the same identity as {@code mealPlan} exists in Duke Cooks.
     */
    boolean hasMealPlan(MealPlan mealPlan);

    /**
     * Deletes the given meal plan.
     * The meal plan must exist in Duke Cooks.
     */
    void deleteMealPlan(MealPlan target);

    /**
     * Adds the given meal plan.
     * {@code mealPlan} must not already exist in Duke Cooks.
     */
    void addMealPlan(MealPlan mealPlan);

    /**
     * Replaces the given meal plan {@code target} with {@code editedMealPlan}.
     * {@code target} must exist in Duke Cooks.
     * The meal plan identity of {@code editedMealPlan} must not be the same
     * as another existing recipe in the Duke Cooks.
     */
    void setMealPlan(MealPlan target, MealPlan editedMealPlan);

    //=========== Exercise Catalogue ================================================================================

    /**
     * Replaces Workout Planner Data with {@code workoutPlanner}
     */
    void setExerciseCatalogue(ReadOnlyExerciseCatalogue exerciseCatalogue);

    /** Returns WorkoutPlanner */
    ReadOnlyExerciseCatalogue getExerciseCatalogue();


    /**
     * Returns true if an exercise with the same identity as {@code exercise}
     * exists in Workout Planner.
     */
    boolean hasExercise(Exercise exercise);

    /**
     * Adds the given exercise.
     * {@code exercise} must not already exist in Duke Cooks.
     */
    void addExercise(Exercise exercise);

    /**
     * Deletes the given exercise.
     * The exercise must exist in Duke Cooks.
     */
    void deleteExercise(Exercise target);

    /**
     * Returns exercise in WorkoutPlanner with the ExerciseName.
     */
    Exercise findExercise(ExerciseName name);

    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     * {@code target} must exist in Duke Cooks.
     * The exercise identity of {@code editedExercise} must not be the
     * same as another existing exercise in the Duke Cooks.
     */
    void setExercise(Exercise target, Exercise editedExercise);

    //=========== Workout Catalogue ================================================================================

    /**
     * Replaces Workout Catalogue Data with {@code workoutCatalogue}
     */
    void setWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue);

    /** Returns WorkoutCatalogue */
    ReadOnlyWorkoutCatalogue getWorkoutCatalogue();

    /**
     * Returns true if an workout with the same identity as {@code workout}
     * exists in Workout Planner.
     */
    boolean hasWorkout(Workout workout);

    /**
     * Adds the given workout.
     * {@code workout} must not already exist in Duke Cooks.
     */
    void addWorkout(Workout workout);

    /**
     * Deletes the given exercise.
     * The exercise must exist in Duke Cooks.
     */
    void deleteWorkout(Workout target);

    /**
     * Replaces the given workout {@code target} with {@code editedWorkout}.
     * {@code target} must exist in Duke Cooks.
     * The exercise identity of {@code editedWorkout} must not be the
     * same as another existing exercise in the Duke Cooks.
     */
    void setWorkout(Workout workout, Workout editedWorkout);

    //=========== Diary ======================================================================================

    /** Returns DiaryRecords */
    ReadOnlyDiary getDiaryRecords();

    /**
     * Returns true if a diary with the same identity as {@code diary} exists in Duke Cooks.
     */
    boolean hasDiary(Diary diary);

    /**
     * Deletes the given diary.
     * The diary must exist in Duke Cooks.
     */
    void deleteDiary(Diary target);

    /**
     * Adds the given diary.
     * {@code diary} must not already exist in Duke Cooks.
     */
    void addDiary(Diary diary);

    /**
     * Replaces the given diary {@code target} with {@code editedDiary}.
     * {@code target} must exist in Duke Cooks.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the Duke Cooks.
     */
    void setDiary(Diary target, Diary editedDiary);

    //=========== Dashboard ======================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code dashboardRecords}.
     */
    void setDashboardRecords(ReadOnlyDashboard dashboardRecords);

    /** Returns Dashboard records */
    ReadOnlyDashboard getDashboardRecords();

    /**
     * Returns true if a dashboard with the same identity as {@code dashboard} exists in Duke Cooks.
     */
    boolean hasDashboard(Dashboard dashboard);

    /**
     * Deletes the given dashboard.
     * The dashboard must exist in Duke Cooks.
     */
    void deleteDashboard(Dashboard target);

    /**
     * Adds the given dashboard.
     * {@code dashboard} must not already exist in Duke Cooks.
     */
    void addDashboard(Dashboard dashboard);

    /**
     * Replaces the given dashboard {@code target} with {@code editedDashboard}.
     * {@code target} must exist in Duke Cooks.
     * The dashboard identity of {@code editedDashboard} must not be the same as
     * another existing dashboard in the Duke Cooks.
     */
    void setDashboard(Dashboard target, Dashboard editedDashboard);

    /**
     * Replaces the given dashboard and updates the task as done.
     */
    void doneDashboard(Dashboard key);

    /**
     * Replaces recently completed dashboard and updates them as completed.
     */
    void changeDashboard(List<Dashboard> l);

    /**
     * Returns true if user has 5 new completed task.
     */
    boolean checkForPrize(List<Dashboard> l);

    //=========== Filtered Person List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Filtered Record List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    //=========== Filtered Recipe List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);

    //=========== Filtered Meal Plan List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered meal plan list */
    ObservableList<MealPlan> getFilteredMealPlanList();

    /**
     * Updates the filter of the filtered meal plan list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMealPlanList(Predicate<MealPlan> predicate);

    //=========== Filtered Exercise List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered exercise list */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);

    //=========== Filtered Workout List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered workout list */
    ObservableList<Workout> getFilteredWorkoutList();

    /**
     * Updates the filter of the filtered workout list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkoutList(Predicate<Workout> predicate);

    //=========== Filtered Diary Records List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered diary list */
    ObservableList<Diary> getFilteredDiaryList();

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiaryList(Predicate<Diary> predicate);

    //=========== Filtered Dashboard Records List Accessors =========================================================

    /** Returns an unmodifiable view of the filtered dashboard list */
    ObservableList<Dashboard> getFilteredDashboardList();

    /**
     * Updates the filter of the filtered dashboard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDashboardList(Predicate<Dashboard> predicate);

}
