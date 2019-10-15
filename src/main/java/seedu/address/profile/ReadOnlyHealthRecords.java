package seedu.address.profile;

import javafx.collections.ObservableList;
import seedu.address.profile.records.Record;

/**
 * Unmodifiable view of Health Records
 */
public interface ReadOnlyHealthRecords {
    /**
     * Returns an unmodifiable view of the Health Records in the list.
     * This list will only contain one instance of record.
     */
    ObservableList<Record> getHealthRecordsList();
}
