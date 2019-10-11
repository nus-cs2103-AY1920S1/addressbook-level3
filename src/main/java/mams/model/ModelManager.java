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
import mams.model.module.Module;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Mams mams;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Module> filteredModules;

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
        filteredModules = new FilteredList<>(this.mams.getModuleList());
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

    /**
     * Returns true if a module with the same identity as {@code module} exists in MAMS.
     *
     * @param module
     */
    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return mams.hasModule(module);
    }

    @Override
    public void deleteStudent(Student target) {
        mams.removeStudent(target);
    }

    @Override
    public void deleteModule(Module target) {
        mams.removeModule(target);
    }

    @Override
    public void addStudent(Student student) {
        mams.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void addModule(Module module) {
        mams.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULE);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        CollectionUtil.requireAllNonNull(target, editedStudent);

        mams.setStudent(target, editedStudent);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        CollectionUtil.requireAllNonNull(target, editedModule);

        mams.setModule(target, editedModule);
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
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
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
