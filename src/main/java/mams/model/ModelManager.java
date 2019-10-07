package mams.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import mams.commons.core.GuiSettings;
import mams.commons.core.LogsCenter;
import mams.commons.util.CollectionUtil;
import mams.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Mams mams;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given MAMS and userPrefs.
     */
    public ModelManager(ReadOnlyMams mams, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(mams, userPrefs);

        logger.fine("Initializing with address book: " + mams + " and user prefs " + userPrefs);

        this.mams = new Mams(mams);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.mams.getStudentList());
    }

    public ModelManager() {
        this(new Mams(), new UserPrefs());
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
    public Path getMamsFilePath() {
        return userPrefs.getMamsFilePath();
    }

    @Override
    public void setMamsFilePath(Path mamsFilePath) {
        requireNonNull(mamsFilePath);
        userPrefs.setMamsFilePath(mamsFilePath);
    }

    //=========== Mams ================================================================================

    @Override
    public void setMams(ReadOnlyMams mams) {
        this.mams.resetData(mams);
    }

    @Override
    public ReadOnlyMams getMams() {
        return mams;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return mams.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        mams.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        mams.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        CollectionUtil.requireAllNonNull(target, editedStudent);

        mams.setStudent(target, editedStudent);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedMams}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
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
        return mams.equals(other.mams)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

}
