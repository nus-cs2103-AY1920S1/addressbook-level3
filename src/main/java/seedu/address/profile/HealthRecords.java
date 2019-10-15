package seedu.address.profile;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.profile.records.Record;
import seedu.address.profile.records.UniqueRecord;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecord comparison)
 */
public class HealthRecords implements ReadOnlyHealthRecords {

    private final UniqueRecord healthrecords;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        healthrecords = new UniqueRecord();
    }

    public HealthRecords() {}

    /**
     * Creates a DukeCooks using the Persons in the {@code toBeCopied}
     */
    public HealthRecords(ReadOnlyHealthRecords healthRecords) {
        this();
        resetData(healthRecords);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the record list with {@code healthRecords}.
     * {@code healthRecords} must not contain duplicate healthRecords.
     */
    public void setHealthRecords(List<Record> healthRecords) {
        this.healthrecords.setRecords(healthRecords);
    }

    /**
     * Resets the existing data of this {@code DukeCooks} with {@code newData}.
     */
    public void resetData(ReadOnlyHealthRecords newData) {
        requireNonNull(newData);

        setHealthRecords(newData.getHealthRecordsList());
    }

    //// record-level operations
    /**
     * Adds a record to Duke Cooks.
     * The record must not already exist in Duke Cooks.
     */
    public void addRecord(Record r) {
        healthrecords.add(r);
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in Duke Cooks.
     * The record identity of {@code editedRecord} must not be the same as another existing record in Duke Cooks.
     */
    public void setRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        healthrecords.setRecord(target, editedRecord);
    }

    //// util methods

    @Override
    public String toString() {
        return healthrecords.asUnmodifiableObservableList().size() + " health records";
        // TODO: refine later
    }

    @Override
    public ObservableList<Record> getHealthRecordsList() {
        return healthrecords.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthRecords // instanceof handles nulls
                && healthrecords.equals(((HealthRecords) other).healthrecords));
    }

    @Override
    public int hashCode() {
        return healthrecords.hashCode();
    }
}
