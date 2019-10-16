package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.EntryNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class ExpenseTrackerList implements Iterable<ExpenseTracker> {

    private final ObservableList<ExpenseTracker> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ExpenseTracker toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(ExpenseTracker toAdd) {
        requireNonNull(toAdd);

        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setTracker(ExpenseTracker target, ExpenseTracker editedTracker) {
        requireAllNonNull(target, editedTracker);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (target.equals(editedTracker) || !contains(editedTracker)) {
            internalList.set(index, editedTracker);
        }
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(ExpenseTracker toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(ExpenseTrackerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }



    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEntries(List<ExpenseTracker> entries) {
        requireAllNonNull(entries);

        internalList.setAll(entries);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ExpenseTracker> asUnmodifiableObservableList() {
        return internalList;
    }

    @Override
    public Iterator<ExpenseTracker> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseTrackerList // instanceof handles nulls
                && internalList.equals(((ExpenseTrackerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
