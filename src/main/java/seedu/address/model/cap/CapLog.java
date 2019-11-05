package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.UniqueModuleList;
import seedu.address.model.cap.person.UniqueSemesterList;
import seedu.address.model.common.Module;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
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
     * Creates an CapLog using the Persons in the {@code toBeCopied}
     */
    public CapLog(ReadOnlyCapLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setSemesters(List<Semester> semesters) {
        this.semesters.setSemesters(semesters);
    }

    /**
     * Resets the existing data of this {@code CalendarAddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCapLog newData) {
        requireNonNull(newData);
        setModules(newData.getModuleList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean hasSemester(Semester semester) {
        requireNonNull(semester);
        return semesters.contains(semester);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addSemester(Semester p) {
        semesters.add(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    public void setSemester(Semester target, Semester editedTask) {
        requireNonNull(editedTask);

        semesters.setSemester(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code CalendarAddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSemester(Semester semester) {
        semesters.remove(semester);
    }

    //// util methods

    @Override
    public String toString() {
        return semesters.asUnmodifiableObservableList().size() + " persons";
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
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setModule(Module target, Module editedPerson) {
        requireNonNull(editedPerson);

        modules.setModule(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }
}
