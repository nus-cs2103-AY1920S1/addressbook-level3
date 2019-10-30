package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.scheduler.Reminder;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the notebook data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Notebook notebook;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given notebook and userPrefs.
     */
    public ModelManager(Notebook notebook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(notebook, userPrefs);

        logger.fine("Initializing with notebook: " + notebook + " and user prefs " + userPrefs);
        this.notebook = new Notebook(notebook);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager() {
        this(new Notebook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNotebookFilePath() {
        return userPrefs.getNotebookFilePath();
    }

    @Override
    public void setNotebookFilePath(Path notebookFilePath) {
        requireNonNull(notebookFilePath);
        userPrefs.setNotebookFilePath(notebookFilePath);
    }

    @Override
    public Notebook getNotebook() {
        return this.notebook;
    }

    //=========== Classroom ================================================================================

    @Override
    public void setClassroom(ReadOnlyClassroom classroom) {
        notebook.setClassroom(classroom);
    }

    @Override
    public ReadOnlyClassroom getClassroom() {
        return notebook.getClassroom();
    }

    @Override
    public boolean hasClassroom(Classroom classroom) {
        return notebook.hasClassroom(classroom);
    }

    @Override
    public void addClassroom(Classroom classroom) {
        notebook.addClassroom(classroom);
    }

    @Override
    public void setCurrentClassroom(Classroom classroom) {
        notebook.setCurrentClassroom(classroom.getClassroomName());
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return notebook.hasStudent(student);
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return notebook.hasAssignment(assignment);
    }

    @Override
    public void deleteStudent(Student target) {
        notebook.deleteStudent(target);
    }

    @Override
    public void deleteAssignment(Assignment target) {
        notebook.deleteAssignment(target);
    }

    @Override
    public void addStudent(Student student) {
        notebook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        notebook.addAssignment(assignment);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        notebook.setStudent(target, editedStudent);
    }

    @Override
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);
        notebook.setAssignment(target, editedAssignment);
    }

    public boolean isDisplayStudents() {
        return notebook.isDisplayStudents();
    }

    public void displayStudents() {
        notebook.displayStudents();
    }

    public void displayAssignments() {
        notebook.displayAssignments();
    }

    @Override
    public void addLesson(Lesson lesson) {
        notebook.addLesson(lesson);
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return notebook.hasLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson target) {
        notebook.removeLesson(target);
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);
        notebook.setLesson(target, editedLesson);
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code Caretaker}
     */
    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code Caretaker}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return notebook.getFilteredStudentList();
    }

    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return notebook.getFilteredAssignmentList();
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return notebook.getFilteredLessonList();
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList(Predicate<Reminder> predicate) {
        return notebook.getFilteredReminderList();
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        notebook.updateFilteredStudentList(predicate);
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        notebook.updateFilteredAssignmentList(predicate);
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        notebook.updateFilteredLessonList(predicate);
    }




    //=========== Undo and Redo Operations =============================================================

    @Override
    public ReadOnlyClassroom undo() {
        return notebook.undo();
    }

    @Override
    public boolean canUndo() {
        return notebook.canUndo();
    }

    @Override
    public ReadOnlyClassroom redo() {
        return notebook.redo();
    }

    @Override
    public boolean canRedo() {
        return notebook.canRedo();
    }

    @Override
    public void saveState() {
        notebook.saveState();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return notebook.equals(other.notebook)
                && userPrefs.equals(other.userPrefs);
    }

}
