package seedu.address.model.health;

import javafx.collections.ObservableList;
import seedu.address.model.health.components.Record;

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
