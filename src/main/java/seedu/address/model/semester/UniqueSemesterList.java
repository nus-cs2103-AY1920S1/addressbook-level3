package seedu.address.model.semester;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.semester.exceptions.DuplicateSemesterException;
import seedu.address.model.semester.exceptions.SemesterNotFoundException;

/**
 * A list of semesters that enforces uniqueness between its elements and does not allow nulls.
 * A {@code Semester} is considered unique by comparing using {@code Semester#equals(Semester)}.
 * As such, adding, updating and removing of semesters uses Semester#equals(Semester) for equality so as to ensure
 * that the semester is unique in terms of identity in the UniqueSemesterList.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueSemesterList implements Iterable<Semester>, Cloneable {
    private final ObservableList<Semester> internalList = FXCollections.observableArrayList();
    private final ObservableList<Semester> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent semester as the given argument.
     */
    public boolean contains(Semester toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a semester to the list.
     * The semester must not already exist in the list.
     */
    public void add(Semester toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSemesterException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Semester {@code target} in the list with {@code editedSemester}.
     * {@code target} must exist in the list.
     * The semester identity of {@code editedSemester} must not be the same as another existing Semester in the list.
     */
    public void setSemester(Semester target, Semester editedSemester) {
        requireAllNonNull(target, editedSemester);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SemesterNotFoundException();
        }

        if (!target.equals(editedSemester) && contains(editedSemester)) {
            throw new DuplicateSemesterException();
        }

        internalList.set(index, editedSemester);
    }

    /**
     * Removes the equivalent semester from the list.
     * The semester must exist in the list.
     */
    public void remove(Semester toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SemesterNotFoundException();
        }
    }

    public void setSemesters(UniqueSemesterList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Semesters}.
     * {@code Semesters} must not contain duplicate semesters.
     */
    public void setSemesters(List<Semester> semesters) {
        requireAllNonNull(semesters);
        if (!semestersAreUnique(semesters)) {
            throw new DuplicateSemesterException();
        }

        internalList.setAll(semesters);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Semester> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Semester> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSemesterList // instanceof handles nulls
                && internalList.equals(((UniqueSemesterList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public UniqueSemesterList clone() throws CloneNotSupportedException {
        return (UniqueSemesterList) super.clone();
    }

    /**
     * Returns true if {@code Semesters} contains only unique Semesters.
     */
    private boolean semestersAreUnique(List<Semester> semesters) {
        for (int i = 0; i < semesters.size() - 1; i++) {
            for (int j = i + 1; j < semesters.size(); j++) {
                if (semesters.get(i).equals(semesters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
