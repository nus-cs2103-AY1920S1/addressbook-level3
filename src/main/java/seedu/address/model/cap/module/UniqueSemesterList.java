package seedu.address.model.cap.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cap.module.exceptions.DuplicateModuleException;
import seedu.address.model.cap.module.exceptions.ModuleNotFoundException;


/**
 * A list of semesters that enforces uniqueness between its elements and does not allow nulls.
 * A semester is considered unique by comparing using {@code Person#isSamePerson(Person)}.
 * As such, adding and updating of
 * semesters uses Person#isSamePerson(Person) for equality so as to ensure that the semester being added or updated is
 * unique in terms of identity in the UniquePersonList.
 * However, the removal of a semester uses Person#equals(Object) so
 * as to ensure that the semester with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see UniqueSemesterList#isSameSemester(Semester)
 */
public class UniqueSemesterList implements Iterable<Semester> {

    private final ObservableList<Semester> internalList = FXCollections.observableArrayList();
    private final ObservableList<Semester> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(Semester toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSemester);
    }

    /**
     * Adds a semester to the list.
     * The semester must not already exist in the list.
     */
    public void add(Semester toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }

    /**
     * Replaces the semester {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The semester identity of {@code editedPerson} must not be the same as another existing semester in the list.
     */
    public void setSemester(Semester targetSemester, Semester editedSemester) {
        requireAllNonNull(targetSemester, editedSemester);

        int index = internalList.indexOf(targetSemester);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!targetSemester.isSameSemester(editedSemester) && contains(editedSemester)) {
            throw new DuplicateModuleException();
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
            throw new ModuleNotFoundException();
        }
    }

    public void setSemesters(List<Semester> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
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

    /**
     * Returns true if {@code semesters} contains only unique semesters.
     */
    private boolean semestersAreUnique(List<Semester> semesters) {
        for (int i = 0; i < semesters.size() - 1; i++) {
            for (int j = i + 1; j < semesters.size(); j++) {
                if (semesters.get(i).isSameSemester(semesters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
