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


    //=========== Notebook ================================================================================

    /**
     * Replaces notebook data with the data in {@code notebook}.
     */
    void setNotebook(ReadOnlyNotebook notebook);

    /**
     * Returns a ReadOnlyNotebook of the current notebook.
     */
    ReadOnlyNotebook getNotebook();


    //=========== Classroom ================================================================================

    /**
     * Replaces classroom data with the data in {@code classroom}.
     */
    void setClassroom(ReadOnlyClassroom classroom);

    /**
     * Returns true if the notebook contains the {@code classroom}.
     */
    boolean hasClassroom(Classroom classroom);

    /**
     * Gets the current classroom of the notebook.
     */
    Classroom getCurrentClassroom();

    /**
     * Gets the classroom with the same name as {@code classroom} from the notebook.
     */
    Classroom getClassroom(Classroom classroom);

    /**
     * Deletes the given classroom.
     * The classroom must exist in the notebook.
     */
    void deleteClassroom(Classroom target);

    /**
     * Adds the given classroom.
     * {@code classroom} must not already exist in the notebook.
     */
    void addClassroom(Classroom classroom);

    /** Sets {@code classroom} to be the current classroom.
     * :@code classroom} must exist in the notebook.
     */
    void setCurrentClassroom(Classroom classroom);


    //=========== Student ================================================================================

    /**
     * Returns true if a student with the same identity as {@code student} exists in the classroom.
     */
    boolean hasStudent(Student student);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the classroom.
     */
    void addStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the classroom.
     */
    void deleteStudent(Student target);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the classroom.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the classroom.
     */
    void setStudent(Student target, Student editedStudent);


    //=========== Assignment ================================================================================

    /**
     * Returns true if an assignment with the same identity as {@code assignment} exists in the classroom.
     */
    boolean hasAssignment(Assignment assignment);

    /**
     * Adds the given assignment.
     * {@code assignment} must not already exist in the classroom.
     */
    void addAssignment(Assignment assignment);

    /**
     * Deletes the given assignment.
     * The assignment must exist in the classroom.
     */
    void deleteAssignment(Assignment target);

    /**
     * Replaces the given assignment {@code target} with {@code editedAssignment}.
     * {@code target} must exist in the classroom.
     * The assignment identity of {@code editedAssignment} must not be the same as another existing assignment
     * in the classroom.
     */
    void setAssignment(Assignment target, Assignment editedAssignment);



    //=========== Lesson ================================================================================

    /**
     * Adds the given lesson.
     * @param lesson lesson object.
     */
    void addLesson(Lesson lesson);

    /**
     * Takes a Lesson and checks if another lesson exists in the same time period.
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
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     * {@code target} must exist in the classroom.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the address
     * book.
     */
    void setLesson(Lesson target, Lesson editedLesson);



    //=========== Filtered List Accessors ===============================================================

    /** Returns an unmodifiable view of the filtered student list. */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered assignment list. */
    ObservableList<Assignment> getFilteredAssignmentList();

    /** Returns an unmodifiable view of the filtered lesson list. */
    ObservableList<Lesson> getFilteredLessonList();

    /** Returns an unmodifiable view of the filtered lesson week list. */
    ObservableList<UniqueLessonList> getFilteredLessonWeekList();

    /** Returns an unmodifiable view of the classroom list. */
    ObservableList<Classroom> getClassroomList();

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

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * Updates the filter of the filtered lesson week list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonWeekList(Predicate<UniqueLessonList> predicate);


    //=========== Undo and Redo Operations =============================================================

    /** Returns a ReadOnlyNotebook of the previously saved state of the notebook. */
    ReadOnlyNotebook undo();

    /** Returns true if the notebook has previous states to undo to. */
    boolean canUndo();

    /** Returns a ReadOnlyNotebook of the next saved state of the notebook. */
    ReadOnlyNotebook redo();

    /** Returns true if the notebook has next state to redo to. */
    boolean canRedo();

    /** Saves the current state of the notebook. */
    void saveState();


    //=========== Display Operations =============================================================

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
     * Returns lists of lessons as a string.
     */
    String displayLessons();

}
