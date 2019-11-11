package seedu.sugarmummy.model.records;

import javafx.collections.ObservableList;
import seedu.sugarmummy.model.ReadOnlyData;

/**
 * Unmodifiable view of a record book
 */
public interface ReadOnlyRecordBook extends ReadOnlyData {

    /**
     * Returns an unmodifiable view of the records list. This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();

}
