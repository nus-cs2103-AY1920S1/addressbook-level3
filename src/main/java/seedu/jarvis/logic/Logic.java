package seedu.jarvis.logic;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.planner.tasks.Task;

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
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns a view of the executed commands.
     */
    ObservableList<Command> getExecutedCommandsList();

    /**
     * Returns a view of the inversely executed commands.
     */
    ObservableList<Command> getInverselyExecutedCommandsList();

    // Planner =============================================================

    /**
     * Returns an unmodifiable view of the filtered list of tasks
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns an unmodifiable view of the list of all the tasks in the planner
     */
    ObservableList<Task> getUnfilteredTaskList();

    /**
     * Returns an unmodifiable view of the list of {@code Task} that coincide with the given day,
     * backed by the internal list of {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    ObservableList<Task> getTasksToday();

    /**
     * Returns an unmodifiable view of the list of {@code Task} that coincide with the given week,
     * backed by the internal list of {@code Planner}
     * @return a list of all the {@code Task} in the {@code Planner}
     */
    ObservableList<Task> getTasksThisWeek();


    // Course Planner ====================================================================

    /**
     * Returns an unmodifiable view of the unfiltered list of courses.
     */
    ObservableList<Course> getUnfilteredCourseList();

    /**
     * Returns the text displayed to the user in the Course Planner.
     */
    ObservableStringValue getCourseTextDisplay();
}
