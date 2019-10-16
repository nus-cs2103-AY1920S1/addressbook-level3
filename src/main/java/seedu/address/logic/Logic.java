package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDiary;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.diary.Diary;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.person.Person;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.records.Record;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns RecipeBook.
     *
     * @see RecipeModel#getRecipeBook()
     */
    ReadOnlyRecipeBook getRecipeBook();

    /**
     * Returns DiaryRecords.
     *
     * @see seedu.address.model.Model#getDiaryRecords()
     */
    ReadOnlyDiary getDiaryRecords();

    /**
     * Returns UserProfile.
     *
     * @see Model#getUserProfile()
     */
    ReadOnlyUserProfile getUserProfile();

    /**
     * Returns DukeCooks.
     *
     * @see seedu.address.model.Model#getWorkoutPlanner()
     */
    ReadOnlyWorkoutPlanner getDukeCooks();

    /** Returns an unmodifiable view of the filtered list of recipes */
    ObservableList<Recipe> getFilteredRecipeList();

    /** Returns an unmodifiable view of the filtered list of diaries */
    ObservableList<Diary> getFilteredDiaryList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Exercise> getFilteredExerciseList();

    /** Returns an unmodifiable view of the filtered list of records */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Returns the user prefs' Recipe Book file path.
     */
    Path getRecipesFilePath();

    /**
     * Returns the user prefs' User Profile file path.
     */
    Path getUserProfileFilePath();

    /**
     * Returns the user prefs' Duke Cooks file path.
     */
    Path getDukeCooksFilePath();

    /**
     * Returns the user prefs' Health Records file path.
     */
    Path getHealthRecordsFilePath();

    /**
     * Returns the user prefs' Duke Cooks file path.
     */
    Path getDiaryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
