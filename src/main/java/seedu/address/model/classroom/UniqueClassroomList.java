package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.classroom.exceptions.ClassroomNotFoundException;
import seedu.address.model.classroom.exceptions.DuplicateClassroomException;

//@@author weikiat97
/**
 * A list of classrooms that enforces uniqueness between its elements and does not allow nulls.
 * A classroom is considered unique by comparing using {@code classroom#isSameclassroom(classroom)}. As such, adding
 * and updating of classrooms uses classroom#isSameclassroom(classroom) for equality so as to ensure that the
 * classroom being added or updated is unique in terms of identity in the UniqueclassroomList. However, the removal of
 * a classroom uses classroom#equals(Object) so as to ensure that the classroom with exactly the same fields will be
 * removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Classroom#isSameClassroom(Classroom)
 */
public class UniqueClassroomList implements Iterable<Classroom> {

    private final ObservableList<Classroom> internalList = FXCollections.observableArrayList();
    private final ObservableList<Classroom> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent classroom as the given argument.
     */
    public boolean contains(Classroom toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClassroom);
    }

    /**
     * Gets a classroom from the list with a class name toGet in String representation.
     */
    public Classroom get(String toGet) {
        requireNonNull(toGet);
        for (Classroom classroom : internalList) {
            if (classroom.getClassroomName().equals(toGet)) {
                return classroom;
            }
        }
        return null;
    }

    /**
     * Gets a classroom from the list with a class name toGet in Classroom representation.
     */
    public Classroom get(Classroom toGet) {
        requireNonNull(toGet);
        for (Classroom classroom : internalList) {
            if (classroom.getClassroomName().equals(toGet.getClassroomName())) {
                return classroom;
            }
        }
        return null;
    }

    /**
     * Adds a classroom to the list.
     * The classroom must not already exist in the list.
     */
    public void add(Classroom toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClassroomException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the classroom {@code target} in the list with {@code editedClassroom}.
     * {@code target} must exist in the list.
     * The classroom identity of {@code editedClassroom} must not be the same as another existing classroom in the
     * list.
     */
    public void setClassroom(Classroom target, Classroom editedClassroom) {
        requireAllNonNull(target, editedClassroom);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClassroomNotFoundException();
        }

        if (!target.isSameClassroom(editedClassroom) && contains(editedClassroom)) {
            throw new DuplicateClassroomException();
        }

        internalList.set(index, editedClassroom);

    }

    /**
     * Removes the equivalent classroom from the list.
     * The classroom must exist in the list.
     */
    public void remove(Classroom toRemove) {
        requireNonNull(toRemove);
        boolean found = false;
        for (Classroom classroom : internalList) {
            if (classroom.getClassroomName().equals(toRemove.getClassroomName())) {
                internalList.remove(classroom);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ClassroomNotFoundException();
        }
    }

    public void setClassrooms(UniqueClassroomList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code classrooms}.
     * {@code classrooms} must not contain duplicate classrooms.
     */
    public void setClassrooms(List<Classroom> classrooms) {
        requireAllNonNull(classrooms);
        if (!classroomsAreUnique(classrooms)) {
            throw new DuplicateClassroomException();
        }

        List<Classroom> listToAdd = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            Classroom classroomToAdd = new Classroom();
            classroomToAdd.resetData(classroom);
            listToAdd.add(classroomToAdd);
        }
        internalList.setAll(listToAdd);
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Classroom> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Classroom> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClassroomList // instanceof handles nulls
                && internalList.equals(((UniqueClassroomList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code classrooms} contains only unique classrooms.
     */
    private boolean classroomsAreUnique(List<Classroom> classrooms) {
        for (int i = 0; i < classrooms.size() - 1; i++) {
            for (int j = i + 1; j < classrooms.size(); j++) {
                if (classrooms.get(i).isSameClassroom(classrooms.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

