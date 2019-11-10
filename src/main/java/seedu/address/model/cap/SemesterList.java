package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.cap.module.UniqueModuleList;
import seedu.address.model.common.Module;

/**
 * Wraps all data at the capLog level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class SemesterList implements ReadOnlySemesterList {

    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    public SemesterList() {}

    /**
     * Creates an CapLog using the Modules in the {@code toBeCopied}
     */
    public SemesterList(ReadOnlySemesterList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the semester list with {@code semesters}.
     * {@code semesters} must not contain duplicate semesters.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code CapLog} with {@code newData}.
     */
    public void resetData(ReadOnlySemesterList newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// semester-level operations

    /**
     * Returns true if a semester with the same identity as {@code semester} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a semester to the cap log.
     * The semester must not already exist in the address book.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given semester {@code target} in the list with {@code editedSemester}.
     * {@code target} must exist in the address book.
     * The semester identity of {@code editedModule} must
     * not be the same as another existing semester in the cap log.
     */
    public void setModule(Module target, Module editedPerson) {
        requireNonNull(editedPerson);

        modules.setModule(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code CapLog}.
     * {@code key} must exist in the capLog.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterList // instanceof handles nulls
                && modules.equals(((SemesterList) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
