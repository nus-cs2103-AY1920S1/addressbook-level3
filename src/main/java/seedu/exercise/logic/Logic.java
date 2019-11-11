package seedu.exercise.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.State;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ExerciseBook.
     *
     * @see seedu.exercise.model.Model#getExerciseBookData()
     */
    ReadOnlyResourceBook<Exercise> getExerciseBook();

    /**
     * Returns an unmodifiable view of the filtered list of exercises.
     */
    ObservableList<Exercise> getSortedExerciseList();

    /**
     * Returns the RegimeBook.
     *
     * @see seedu.exercise.model.Model#getAllRegimeData()
     */
    ReadOnlyResourceBook<Regime> getRegimeBook();

    ObservableList<Regime> getSortedRegimeList();

    /**
     * Returns an unmodifiable view of the filtered list of schedules
     */
    ObservableList<Schedule> getSortedScheduleList();

    /**
     * Returns the user prefs' exercise book file path.
     */
    Path getExerciseBookFilePath();

    /**
     * Returns the user prefs' regime book file path.
     */
    Path getRegimeBookFilePath();

    /**
     * Returns an unmodifiable view of the suggested list of exercises.
     */
    ObservableList<Exercise> getSuggestedExerciseList();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the Statistic object currently in focus.
     */
    Statistic getStatistic();

    /**
     * Returns the conflict that needs to be resolved in {@code Model}.
     *
     * This method should only be called when {@code MainApp}'s state is {@link State#IN_CONFLICT}.
     */
    Conflict getConflict();
}
