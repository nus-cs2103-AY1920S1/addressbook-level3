package seedu.billboard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.billboard.model.person.Record;
import seedu.billboard.model.person.RecordList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecord comparison)
 */
public class Billboard implements ReadOnlyBillboard {

    private final RecordList records;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new RecordList();
    }

    public Billboard() {}

    /**
     * Creates an Billboard using the Persons in the {@code toBeCopied}
     */
    public Billboard(ReadOnlyBillboard toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        this.records.setPersons(records);
    }

    /**
     * Resets the existing data of this {@code Billboard} with {@code newData}.
     */
    public void resetData(ReadOnlyBillboard newData) {
        requireNonNull(newData);

        setRecords(newData.getPersonList());
    }

    //// expense-level operations

    /**
     * Returns true if a record with the same identity as {@code record} exists in the address book.
     */
    public boolean hasPerson(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the address book.
     * The record must not already exist in the address book.
     */
    public void addPerson(Record p) {
        records.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedRecord} must not be the same as another existing expense in the address book.
     */
    public void setPerson(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        records.setPerson(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code Billboard}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Record key) {
        records.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
        // TODO: refine later
    }

    @Override
    public ObservableList<Record> getPersonList() {
        return records.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Billboard // instanceof handles nulls
                && records.equals(((Billboard) other).records));
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }
}
