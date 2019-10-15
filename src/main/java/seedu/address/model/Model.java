package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.records.Record;

/**
 * The API of the RecipeModel component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;

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
     * Sets the user prefs' Duke Cooks file path.
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

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);
}
