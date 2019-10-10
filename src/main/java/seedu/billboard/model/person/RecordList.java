package seedu.billboard.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.billboard.model.person.exceptions.DuplicatePersonException;
import seedu.billboard.model.person.exceptions.PersonNotFoundException;

/**
 * A list of records. The records are unique as specified by the {@code equals} method of the {@code Record} class
 * Supports a minimal set of list operations.
 *
 */
public class RecordList implements Iterable<Record> {

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent expense as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a expense to the list.
     * The expense must not already exist in the list.
     */
    public void add(Record toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setPerson(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(RecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setPersons(List<Record> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(records);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordList // instanceof handles nulls
                        && internalList.equals(((RecordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code expenses} contains only unique expenses.
     */
    private boolean recordsAreUnique(List<? extends Record> expenses) {
        for (int i = 0; i < expenses.size() - 1; i++) {
            for (int j = i + 1; j < expenses.size(); j++) {
                if (expenses.get(i).isSameRecord(expenses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
