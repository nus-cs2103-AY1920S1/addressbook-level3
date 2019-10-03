package seedu.exercise.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.exercise.Exercise;

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
     * Returns the ExerciseBook.
     *
     * @see seedu.exercise.model.Model#getAllData()
     */
    ReadOnlyExerciseBook getExerciseBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Exercise> getFilteredExerciseList();

    ObservableList<Exercise> getSortedExerciseList();

    /**
     * Returns the user prefs' exercise book file path.
     */
    Path getExerciseBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
