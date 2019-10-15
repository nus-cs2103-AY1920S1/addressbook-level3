package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.records.Record;

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
