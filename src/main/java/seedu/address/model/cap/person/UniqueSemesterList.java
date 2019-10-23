package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cap.person.exceptions.DuplicateModuleException;
import seedu.address.model.cap.person.exceptions.ModuleNotFoundException;


/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
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
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Semester toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSemester);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Semester toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
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
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
     * Returns true if {@code persons} contains only unique persons.
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
