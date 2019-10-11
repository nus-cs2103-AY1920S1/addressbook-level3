package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * A list of Students that enforces uniqueness between its elements and does not allow nulls.
 * A Student is considered unique by comparing using {@code Student#isSameStudent(Student)}. As such, adding and
 * updating of Students uses Student#isSameStudent(Student) for equality so as to ensure that the
 * Student being added or updated is unique in terms of identity in the UniqueStudentList. However, the removal of
 * a Student uses Student#equals(Object) so as to ensure that the Student with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Student#isSameStudent(Student)
 */
public class UniqueStudentList implements Iterable<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();
    private final ObservableList<Student> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Student as the given argument.
     */
    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudent);
    }

    /**
     * Adds a Student to the list.
     * The Student must not already exist in the list.
     */
    public void add(Student toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the list.
     * The Student identity of {@code editedStudent} must not be the same as another existing Student in the list.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!target.isSameStudent(editedStudent) && contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        internalList.set(index, editedStudent);
    }

    public Student remove(Index index) {
        return internalList.remove(index.getZeroBased());
    }

    /**
     * Removes the equivalent Student from the list.
     * The Student must exist in the list.
     */
    public void remove(Student toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudentNotFoundException();
        }
    }

    public void setStudent(Index index, Student student) {
        internalList.set(index.getZeroBased(), student);
    }

    public void setStudents(UniqueStudentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Students}.
     * {@code Students} must not contain duplicate Students.
     */
    public void setStudents(List<Student> students) {
        requireAllNonNull(students);
        if (!studentsAreUnique(students)) {
            throw new DuplicateStudentException();
        }

        internalList.setAll(students);
    }

    public Student getStudent(Index index) {
        return internalList.get(index.getZeroBased());
    }

    public String getStudentList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < internalList.size(); i++) {
            sb.append(internalList.get(i));
        }
        return sb.toString();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Student> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Student> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentList // instanceof handles nulls
                        && internalList.equals(((UniqueStudentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Students} contains only unique Students.
     */
    private boolean studentsAreUnique(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).isSameStudent(students.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
