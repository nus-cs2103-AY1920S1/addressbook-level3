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
 * Represents the in-memory model of the classroom data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Classroom classroom;
    private final Caretaker caretaker;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private FilteredList<Reminder> filteredReminders;
    private final FilteredList<Assignment> filteredAssignments;

    /**
     * Initializes a ModelManager with the given classroom and userPrefs.
     */
    public ModelManager(ReadOnlyClassroom classroom, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(classroom, userPrefs);

        logger.fine("Initializing with classroom: " + classroom + " and user prefs " + userPrefs);

        this.classroom = new Classroom(classroom);
        this.caretaker = new Caretaker(new Memento(classroom), this.classroom);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.classroom.getStudentList());
        filteredReminders = new FilteredList<>(this.classroom.getReminderList());
        filteredAssignments = new FilteredList<>(this.classroom.getAssignmentList());
    }

    public ModelManager() {
        this(new Classroom(), new UserPrefs());
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
    public Path getClassroomFilePath() {
        return userPrefs.getClassroomFilePath();
    }

    @Override
    public void setClassroomFilePath(Path classroomFilePath) {
        requireNonNull(classroomFilePath);
        userPrefs.setClassroomFilePath(classroomFilePath);
    }

    //=========== Classroom ================================================================================

    @Override
    public void setClassroom(ReadOnlyClassroom classroom) {
        this.classroom.resetData(classroom);
    }

    @Override
    public ReadOnlyClassroom getClassroom() {
        return classroom;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return classroom.hasStudent(student);
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return classroom.hasAssignment(assignment);
    }

    @Override
    public void deleteStudent(Student target) {
        classroom.removeStudent(target);
    }

    @Override
    public void deleteAssignment(Assignment target) {
        classroom.removeAssignment(target);
    }

    @Override
    public void addStudent(Student student) {
        classroom.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        classroom.addAssignment(assignment);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        classroom.setStudent(target, editedStudent);
    }

    @Override
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);

        classroom.setAssignment(target, editedAssignment);
    }

    public boolean isDisplayStudents() {
        return classroom.isDisplayStudents();
    }

    public void displayStudents() {
        classroom.displayStudents();
    }

    public void displayAssignments() {
        classroom.displayAssignments();
    }

    public void addLesson(Lesson lesson) {
        classroom.addLesson(lesson);
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return classroom.hasLesson(lesson);
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code Caretaker}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return filteredAssignments;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    //=========== Undo and Redo Operations =============================================================

    @Override
    public ReadOnlyClassroom undo() {
        return caretaker.undo();
    }

    @Override
    public boolean canUndo() {
        return caretaker.canUndo();
    }

    @Override
    public ReadOnlyClassroom redo() {
        return caretaker.redo();
    }

    @Override
    public boolean canRedo() {
        return caretaker.canRedo();
    }

    @Override
    public void saveState() {
        caretaker.saveState();
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
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
        return classroom.equals(other.classroom)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

}
