package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    public void editTaskInAllPersons(Task task, Task editedTask, Project currWorkingProject) {
        requireNonNull(task);
        List<Person> personsToEdit = new ArrayList<>();
        List<Person> editedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person person : internalList) {

            if (!person.getPerformance().getTaskAssignment().containsKey(projectTitle)) {
                continue;
            }

            List<Task> tasksAssigned = person.getPerformance().getTaskAssignment().get(projectTitle);
            if (tasksAssigned.contains(task)) {
                personsToEdit.add(person);
                person.getPerformance().setTask(task, editedTask, projectTitle);
                Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getProfilePicture(),
                        person.getAddress(), person.getTags(), person.getTimetable(), person.getPerformance());
                editedPerson.getProjects().addAll(person.getProjects());
                editedPersons.add(editedPerson);
            }
        }

        ListIterator<Person> toEditIter = personsToEdit.listIterator();
        ListIterator<Person> editedIter = editedPersons.listIterator();

        while (toEditIter.hasNext() && editedIter.hasNext()) {
            setPerson(toEditIter.next(), editedIter.next());
        }
    }

    public void deleteTaskInAllPersons(Task task, Project currWorkingProject) {
        requireNonNull(task);
        List<Person> personsToEdit = new ArrayList<>();
        List<Person> editedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person person : internalList) {

            if (!person.getPerformance().getTaskAssignment().containsKey(projectTitle)) {
                continue;
            }

            List<Task> tasksAssigned = person.getPerformance().getTaskAssignment().get(projectTitle);
            if (tasksAssigned.contains(task)) {
                personsToEdit.add(person);
                person.getPerformance().deleteTask(task, projectTitle);
                Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getProfilePicture(),
                        person.getAddress(), person.getTags(), person.getTimetable(), person.getPerformance());
                editedPerson.getProjects().addAll(person.getProjects());
                editedPersons.add(editedPerson);
            }
        }

        ListIterator<Person> toEditIter = personsToEdit.listIterator();
        ListIterator<Person> editedIter = editedPersons.listIterator();

        while (toEditIter.hasNext() && editedIter.hasNext()) {
            setPerson(toEditIter.next(), editedIter.next());
        }
    }

    public void deleteMeetingInAllPersons(Meeting meeting, Project currWorkingProject) {
        requireNonNull(meeting);
        List<Person> personsToEdit = new ArrayList<>();
        List<Person> editedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person person : internalList) {

            if (!person.getPerformance().getMeetingsAttended().containsKey(projectTitle)) {
                continue;
            }

            List<Meeting> meetingsAttended = person.getPerformance().getMeetingsAttended().get(projectTitle);
            if (meetingsAttended.contains(meeting)) {
                personsToEdit.add(person);
                person.getPerformance().deleteMeeting(meeting, projectTitle);
                Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getProfilePicture(),
                        person.getAddress(), person.getTags(), person.getTimetable(), person.getPerformance());
                editedPerson.getProjects().addAll(person.getProjects());
                editedPersons.add(editedPerson);
            }
        }

        ListIterator<Person> toEditIter = personsToEdit.listIterator();
        ListIterator<Person> editedIter = editedPersons.listIterator();

        while (toEditIter.hasNext() && editedIter.hasNext()) {
            setPerson(toEditIter.next(), editedIter.next());
        }
    }

    public List<Person> getMembersOf(Project project) {
        List<Person> memberList = new ArrayList<>();
        List<String> memberNameList = project.getMemberNames();

        for (Person person : internalList) {
            String name = person.getName().fullName;
            if (memberNameList.contains(name)) {
                memberList.add(person);
            }
        }

        return memberList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
