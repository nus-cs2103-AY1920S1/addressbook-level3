package seedu.address.model.finance;

import javafx.collections.ObservableList;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * Unmodifiable view of an finance log
 */
public interface ReadOnlyFinanceLog {

    /**
     * Returns an unmodifiable view of the list of finance log entries.
     */
    ObservableList<LogEntry> getLogEntryList();

}
