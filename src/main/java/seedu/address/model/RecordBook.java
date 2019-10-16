package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSameRecord comparison)
 */
public class RecordBook implements ReadOnlyRecordBook {

    private final UniqueRecordList records;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new UniqueRecordList();
    }

    public RecordBook() {
    }

    /**
     * Creates an RecordBook using the Records in the {@code toBeCopied}
     */
    public RecordBook(ReadOnlyRecordBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the record list with {@code records}. {@code records} must not contain duplicate
     * records.
     */
    public void setRecords(List<Record> records) {
        this.records.setRecords(records);
    }

    /**
     * Resets the existing data of this {@code RecordBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRecordBook newData) {
        requireNonNull(newData);

        setRecords(newData.getRecordList());
    }

    //// record-level operations

    /**
     * Returns true if a record with the same identity as {@code record} exists in the address book.
     */
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the address book. The record must not already exist in the address book.
     */
    public void addRecord(Record p) {
        records.add(p);
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}. {@code target} must exist in the
     * address book. The record identity of {@code editedRecord} must not be the same as another existing record in the
     * address book.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code RecordBook}. {@code key} must exist in the address book.
     */
    public void removeRecord(Record key) {
        records.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
        // TODO: refine later
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return records.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RecordBook // instanceof handles nulls
            && records.equals(((RecordBook) other).records));
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }
}
