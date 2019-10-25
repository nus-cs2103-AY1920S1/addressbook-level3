package dukecooks.model.health;

import dukecooks.model.health.components.Record;
import javafx.collections.ObservableList;

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
