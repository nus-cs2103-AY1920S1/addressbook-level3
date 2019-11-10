package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.UniqueModuleList;
import seedu.address.model.cap.module.UniqueSemesterList;
import seedu.address.model.common.Module;

/**
 * Wraps all data at the Modulo level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class CapLog implements ReadOnlyCapLog {

    private final UniqueSemesterList semesters;
    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        semesters = new UniqueSemesterList();
        modules = new UniqueModuleList();
    }

    public CapLog() {}

    /**
     * Creates an CapLog using the Modules in the {@code toBeCopied}
     */
    public CapLog(ReadOnlyCapLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the semester list with {@code tasks}.
     * {@code tasks} must not contain duplicate semesters.
     */
    public void setSemesters(List<Semester> semesters) {
        this.semesters.setSemesters(semesters);
    }

    /**
     * Resets the existing data of this {@code CapLog} with {@code newData}.
     */
    public void resetData(ReadOnlyCapLog newData) {
        requireNonNull(newData);
        setModules(newData.getModuleList());
    }

    //// task-level operations

    /**
     * Returns true if a semester {@code semester} exists in the modulo.
     */
    public boolean hasSemester(Semester semester) {
        requireNonNull(semester);
        return semesters.contains(semester);
    }

    /**
     * Adds a semester to the modulo.
     * The semester must not already exist in the modulo.
     */
    public void addSemester(Semester p) {
        semesters.add(p);
    }

    /**
     * Replaces the given semester {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the modulo.
     * The semester identity of {@code editedTask} must not
     * be the same as another existing semester in the modulo.
     */
    public void setSemester(Semester target, Semester editedTask) {
        requireNonNull(editedTask);

        semesters.setSemester(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code CapLog}.
     * {@code key} must exist in the modulo.
     */
    public void removeSemester(Semester semester) {
        semesters.remove(semester);
    }

    //// util methods

    @Override
    public String toString() {
        return semesters.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Semester> getSemesterList() {
        return semesters.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CapLog // instanceof handles nulls
                && semesters.equals(((CapLog ) other).semesters));
    }

    @Override
    public int hashCode() {
        return semesters.hashCode();
    }

    ////////// MODULES METHODS /////////////
    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the modulo.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the modulo.
     * The module must not already exist in the modulo.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the modulo.
     * The module identity of {@code editedModule} must not be the same as another existing module in the modulo.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code Modulo}.
     * {@code key} must exist in the modulo.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }
}
