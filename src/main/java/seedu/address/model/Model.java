package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;
    Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;
    Predicate<UniqueLessonList> PREDICATE_SHOW_ALL_LESSONLISTS = unused -> true;
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
     * Returns the user prefs' notebook file path.
     */
    Path getNotebookFilePath();

    /**
     * Sets the user prefs' classroom file path.
     */
    void setNotebookFilePath(Path classroomFilePath);

    /**
     * Replaces classroom data with the data in {@code classroom}.
     */
    void setClassroom(ReadOnlyClassroom classroom);

    /**
     * Gets the current classroom of the notebook.
     * @return current classroom
     */
    Classroom getCurrentClassroom();

    /**
     * Finds the classroom with the same name from the notebook.
     * @param classroom class room to find
     * @return classroom from notebook
     */
    Classroom getClassroom(Classroom classroom);

    /**
     * Replaces notebook data with the data in {@code notebook}.
     */
    void setNotebook(ReadOnlyNotebook notebook);

    /** Returns the Notebook */
    ReadOnlyNotebook getNotebook();

    boolean hasClassroom(Classroom classroom);

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
    ObservableList<Lesson> getFilteredLessonList();
    ObservableList<UniqueLessonList> getFilteredLessonWeekList();

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

    void updateFilteredLessonList(Predicate<Lesson> predicate);

    void updateFilteredLessonWeekList(Predicate<UniqueLessonList> predicate);

    ReadOnlyNotebook undo();

    boolean canUndo();

    ReadOnlyNotebook redo();

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
     * returns lists of lessons as a string.
     * @return list of lessons.
     */
    String displayLessons();

    /**
     * Adds the given lesson.
     * @param lesson lesson object.
     */
    void addLesson(Lesson lesson);

    /**
     * takes a Lesson and checks if another lesson exists in the same time period.
     * @param toCheck Lesson object.
     * @return boolean.
     */
    boolean checkTimingExist(Lesson toCheck);

    /**
     * Returns true if a Lesson with the same identity exists in the classroom.
     * @param lesson Lesson object.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Deletes the given lesson.
     * The lesson must exist in the classroom.
     */
    void deleteLesson(Lesson target);

    /**
     * Deletes the given classroom.
     * The classroom must exist in the notebook.
     */
    void deleteClassroom(Classroom target);

    /**
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     * {@code target} must exist in the classroom.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the address
     * book.
     */
    void setLesson(Lesson target, Lesson editedLesson);


    void addClassroom(Classroom classroom);

    void setCurrentClassroom(Classroom classroom);

    ObservableList<Classroom> getClassroomList();
}
