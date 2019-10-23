package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.scheduler.Reminder;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;
    Predicate<Student> PREDICATE_SHOW_NO_STUDENTS = used -> false;
    Predicate<Assignment> PREDICATE_SHOW_NO_ASSIGNMENTS = used -> false;

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
     * Returns the user prefs' classroom file path.
     */
    Path getClassroomFilePath();

    /**
     * Sets the user prefs' classroom file path.
     */
    void setClassroomFilePath(Path classroomFilePath);

    /**
     * Replaces classroom data with the data in {@code classroom}.
     */
    void setClassroom(ReadOnlyClassroom classroom);

    /** Returns the Classroom */
    ReadOnlyClassroom getClassroom();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the classroom.
     */
    boolean hasStudent(Student student);
    boolean hasAssignment(Assignment assignment);
    /**
     * Deletes the given student.
     * The student must exist in the classroom.
     */
    void deleteStudent(Student target);
    void deleteAssignment(Assignment target);
    /**
     * Adds the given student.
     * {@code student} must not already exist in the classroom.
     */
    void addStudent(Student student);
    void addAssignment(Assignment assignment);
    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the classroom.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the classroom.
     */
    void setStudent(Student target, Student editedStudent);
    void setAssignment(Assignment target, Assignment editedAssignment);
    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered assignment list */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAssignmentList(Predicate<Assignment> predicate);

    ReadOnlyClassroom undo();

    boolean canUndo();

    ReadOnlyClassroom redo();

    boolean canRedo();

    void saveState();

    /**
     * Returns the boolean Classroom.isDisplayStudents to determine if students should be displayed.
     */
    boolean isDisplayStudents();

    /**
     * Sets the boolean Classroom.isDisplayStudents to true, to display student list.
     */
    void displayStudents();

    /**
     * Sets the boolean Classroom.isDisplayStudents to false, to display assignment list.
     */
    void displayAssignments();

    /**
     * Adds the given lesson.
     * @param lesson Lesson object.
     */
    void addLesson(Lesson lesson);

    /**
     * Returns true if a Lesson with the same identity exists in the classroom.
     * @param lesson Lesson object.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * returns an unmodifiable view of the filtered
     * @return Ob
     */
    ObservableList<Reminder> getFilteredReminderList();
}
