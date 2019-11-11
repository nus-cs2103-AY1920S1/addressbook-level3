package seedu.billboard.model.recurrence;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.billboard.model.recurrence.exceptions.DuplicateRecurrenceException;
import seedu.billboard.model.recurrence.exceptions.RecurrenceNotFoundException;

/**
 * A list of recurrences. The recurrences are unique as specified by the {@code equals} method of the
 * {@code Expense} class
 * Supports a minimal set of list operations.
 *
 */
public class RecurrenceList implements Iterable<Recurrence> {

    private final List<Recurrence> internalList = new ArrayList<>();

    public RecurrenceList() {};

    public RecurrenceList(List<Recurrence> recurrences) {
        internalList.addAll(recurrences);
    }

    /**
     * Returns true if the list contains an equivalent recurrence as the given argument.
     */
    public boolean contains(Recurrence toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns number of recurrences.
     *
     * @return number of recurrences
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds a recurrence to the list.
     * The recurrence must not already exist in the list.
     */
    public void add(Recurrence toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecurrenceException();
        }
        internalList.add(toAdd);
    }

    public Recurrence get(int index) throws IndexOutOfBoundsException {
        return internalList.get(index);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecurrence}.
     * {@code target} must exist in the list.
     * The record {@code editedRecurrence} must not be the same as another existing record in the list.
     */
    public void setRecurrence(Recurrence target, Recurrence editedRecurrence) {
        requireAllNonNull(target, editedRecurrence);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecurrenceNotFoundException();
        }

        if (!target.equals(editedRecurrence) && contains(editedRecurrence)) {
            throw new DuplicateRecurrenceException();
        }

        internalList.set(index, editedRecurrence);
    }

    /**
     * Removes the equivalent recurrence from the list.
     * The record must exist in the list.
     */
    public void remove(Recurrence toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecurrenceNotFoundException();
        }
    }

    /**
     * Removes the equivalent recurrence at index from the list.
     * The index must be valid.
     */
    public Recurrence remove(int index) {
        requireNonNull(index);
        try {
            return internalList.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RecurrenceNotFoundException();
        }
    }

    public void setRecurrences(RecurrenceList replacement) {
        requireNonNull(replacement);
        internalList.clear();
        internalList.addAll(replacement.asUnmodifiableList());
    }

    /**
     * Replaces the contents of this list with {@code Recurrence}.
     * {@code Recurrence} must not contain duplicate Recurrence.
     */
    public void setRecurrences(List<Recurrence> recurrences) {
        requireAllNonNull(recurrences);
        if (!recurrencesAreUnique(recurrences)) {
            throw new DuplicateRecurrenceException();
        }

        internalList.clear();
        internalList.addAll(recurrences);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public List<Recurrence> asUnmodifiableList() {
        return Collections.unmodifiableList(internalList);
    }

    @Override
    public Iterator<Recurrence> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurrenceList // instanceof handles nulls
                        && internalList.equals(((RecurrenceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Recurrence} contains only unique Recurrence.
     */
    private boolean recurrencesAreUnique(List<? extends Recurrence> recurrences) {
        for (int i = 0; i < recurrences.size() - 1; i++) {
            for (int j = i + 1; j < recurrences.size(); j++) {
                if (recurrences.get(i).equals(recurrences.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public RecurrenceList getClone() {
        RecurrenceList clonedList = new RecurrenceList();
        for (Recurrence e : this.internalList) {
            clonedList.add(e);
        }
        return clonedList;
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < internalList.size(); i++) {
            output += "[" + internalList.get(i) + "]";
            if (i != internalList.size() - 1) {
                output += ",\n";
            }
        }

        return output;
    }
}
