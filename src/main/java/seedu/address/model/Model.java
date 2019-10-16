package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.diary.Diary;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.person.Person;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.records.Record;

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
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISE = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Diary> PREDICATE_SHOW_ALL_DIARIES = unused -> true;

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

    //=========== User Profile ================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code userProfile}.
     */
    void setUserProfile(ReadOnlyUserProfile userProfile);

    /** Returns UserProfile */
    ReadOnlyUserProfile getUserProfile();

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

    //=========== Workout Planner ================================================================================

    /**
     * Replaces Workout Planner Data with {@code workoutPlanner}
     */
    void setWorkoutPlanner(ReadOnlyWorkoutPlanner workoutPlanner);

    /** Returns WorkoutPlanner */
    ReadOnlyWorkoutPlanner getWorkoutPlanner();


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
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     * {@code target} must exist in Duke Cooks.
     * The exercise identity of {@code editedExercise} must not be the
     * same as another existing exercise in the Duke Cooks.
     */
    void setExercise(Exercise target, Exercise editedExercise);

    //=========== Diary ======================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code diaryRecords}.
     */
    void setDiaryRecords(ReadOnlyDiary diaryRecords);

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

    //=========== Filtered Exercise List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered exercise list */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
    * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
    * @throws NullPointerException if {@code predicate} is null.
    */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);

    //=========== Filtered Diary Records List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered diary list */
    ObservableList<Diary> getFilteredDiaryList();

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiaryList(Predicate<Diary> predicate);
}
