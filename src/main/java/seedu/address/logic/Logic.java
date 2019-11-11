package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.student.Student;

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

    /** Returns a ReadOnlyClassroom view of the current classroom. */
    ReadOnlyClassroom getClassroom();

    /** Returns a ReadOnlyNotebook view of the notebook. */
    ReadOnlyNotebook getNotebook();

    /** Returns an unmodifiable view of the filtered list of students. */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered list of assignments. */
    ObservableList<Assignment> getFilteredAssignmentList();

    /** Returns an unmodifiable view of the filtered list of lessons in the day. */
    ObservableList<Lesson> getFilteredLessonList();

    /** Returns an unmodifiable view of the filtered list of lessons in all days. */
    ObservableList<UniqueLessonList> getFilteredLessonWeekList();

    /** Returns an unmodifiable view of the classrooms. */
    ObservableList<Classroom> getClassroomList();

    /** Returns the user prefs' notebook file path. */
    Path getNotebookFilePath();

    /** Returns the user prefs' GUI settings. */
    GuiSettings getGuiSettings();

    /** Set the user prefs' GUI settings. */
    void setGuiSettings(GuiSettings guiSettings);

    /** Returns true if the current notebook is set to display students. */
    boolean isDisplayStudents();

}
