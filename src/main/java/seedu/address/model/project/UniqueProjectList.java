package seedu.address.model.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * I just copied from UniquePeronList
 */
public class UniqueProjectList implements Iterable<Project> {

    private final ObservableList<Project> internalList = FXCollections.observableArrayList();
    private final ObservableList<Project> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Project as the given argument.
     */
    public boolean contains(Project toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProject);
    }

    /**
     * Adds a Project to the list.
     * The Project must not already exist in the list.
     */
    public void add(Project toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProjectException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the list.
     * The Project identity of {@code editedProject} must not be the same as another existing Project in the list.
     */
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProjectNotFoundException();
        }

        if (!target.isSameProject(editedProject) && contains(editedProject)) {
            throw new DuplicateProjectException();
        }

        internalList.set(index, editedProject);
    }

    /**
     * Removes the equivalent Project from the list.
     * The Project must exist in the list.
     */
    public void remove(Project toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectNotFoundException();
        }
    }

    public void setProjects(UniqueProjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        requireAllNonNull(projects);
        if (!projectsAreUnique(projects)) {
            throw new DuplicateProjectException();
        }

        internalList.setAll(projects);
    }

    public void deleteMember(String member) {
        for (Project project : internalList) {
            project.deleteMember(member);
        }
    }

    public void editInAllProjects(Person personToEdit, Person editedPerson) {
        String personToEditName = personToEdit.getName().fullName;
        String editedPersonName = editedPerson.getName().fullName;
        List<Project> projectsToEdit = new ArrayList<>();
        List<Project> editedProjects = new ArrayList<>();

        for (Project project : internalList) {
            List<String> memberList = project.getMemberNames();

            if (memberList.contains(personToEditName)) {
                projectsToEdit.add(project);
                memberList.set(memberList.indexOf(personToEditName), editedPersonName);
                List<String> updatedMemberList = memberList;

                Project updatedProject = new Project(project.getTitle(), project.getDescription(), updatedMemberList,
                        project.getTasks(), project.getFinance(), project.getGeneratedTimetable());
                updatedProject.setListOfMeeting(project.getListOfMeeting());
                editedProjects.add(updatedProject);
                updatedProject.setListOfMeeting(project.getListOfMeeting());
            }

            ListIterator<Project> toEditIter = projectsToEdit.listIterator();
            ListIterator<Project> editedIter = editedProjects.listIterator();

            while (toEditIter.hasNext() && editedIter.hasNext()) {
                setProject(toEditIter.next(), editedIter.next());
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Project> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Project> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProjectList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.project.UniqueProjectList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code projects} contains only unique projects.
     */
    private boolean projectsAreUnique(List<Project> projects) {
        for (int i = 0; i < projects.size() - 1; i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                if (projects.get(i).isSameProject(projects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
