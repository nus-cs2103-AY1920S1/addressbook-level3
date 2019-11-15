package dukecooks.model.health.components;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.parser.health.TimestampComparator;
import dukecooks.model.health.exceptions.DuplicateRecordException;
import dukecooks.model.health.exceptions.RecordNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A list of records that enforces uniqueness between its elements and does not allow nulls.
 * A record is considered unique by comparing using {@code Record#isSameRecord(Record)}. As such, adding and updating of
 * records uses Record#isSameRecord(Record) for equality so as to ensure that the record being added or updated is
 * unique in terms of identity in the UniqueUserProfile. However, the removal of a record uses Record#equals(Object) so
 * as to ensure that the record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Record#isSameRecord(Record)
 */
public class UniqueRecord implements Iterable<Record> {

    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecord);
    }

    /**
     * Adds a record to the list.
     * The record must not already exist in the list.
     */
    public void add(Record toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
        internalList.sort(new TimestampComparator().reversed());
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setRecord(Record target, Record editedRecord) {
        CollectionUtil.requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecordNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
        internalList.sort(new TimestampComparator().reversed());
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Record toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecordNotFoundException();
        }
    }

    public void setRecords(UniqueRecord replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        internalList.sort(new TimestampComparator().reversed());
    }

    /**
     * Replaces the contents of this list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        CollectionUtil.requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(records);
        internalList.sort(new TimestampComparator().reversed());
    }

    /**
     * Retrieves the equivalent record from the list according to {@code RecordName} of the Record.
     * The record must exist in the list.
     */
    public Record retrieve(Record record) {
        requireNonNull(record);

        int i = 0;
        boolean found = false;
        while (i < internalList.size()) {
            Record curr = internalList.get(i);
            if (curr.isSameRecord(record)) {
                found = true;
                break;
            }
            i++;
        }

        if (!found) {
            throw new RecordNotFoundException();
        }

        return internalList.get(i);
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
                || (other instanceof UniqueRecord // instanceof handles nulls
                        && internalList.equals(((UniqueRecord) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code records} contains only unique records.
     */
    private boolean recordsAreUnique(List<Record> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).isSameRecord(records.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
