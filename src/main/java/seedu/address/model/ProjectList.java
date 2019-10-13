package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.project.Project;
import seedu.address.model.project.UniqueProjectList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProject comparison)
 */
public class ProjectList implements ReadOnlyProjectList {

    private final UniqueProjectList projects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        projects = new UniqueProjectList();
    }

    public ProjectList() {}

    /**
     * Creates an AddressBook using the Projects in the {@code toBeCopied}
     */
    public ProjectList(ReadOnlyProjectList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Project list with {@code Projects}.
     * {@code Projects} must not contain duplicate Projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setProjects(projects);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyProjectList newData) {
        requireNonNull(newData);

        setProjects(newData.getProjectList());
    }

    //// Project-level operations

    /**
     * Returns true if a Project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Adds a Project to the address book.
     * The Project must not already exist in the address book.
     */
    public void addProject(Project p) {
        projects.add(p);
    }

    /**
     * Replaces the given Project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the address book.
     * The Project identity of {@code editedProject} must not be the same as another existing Project in the address book.
     */
    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);

        projects.setProject(target, editedProject);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProject(Project key) {
        projects.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return projects.asUnmodifiableObservableList().size() + " Projects";
        // TODO: refine later
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectList // instanceof handles nulls
                && projects.equals(((ProjectList) other).projects));
    }

    @Override
    public int hashCode() {
        return projects.hashCode();
    }
}
