package seedu.address.model.finance.logentry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.finance.logentry.exceptions.LogEntryNotFoundException;


/**
 * A list of finance log entries that does not allow nulls.
 * The removal of a log entry uses LogEntry#equals(Object) so
 * as to ensure that the log entry with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class LogEntryList implements Iterable<LogEntry> {

    private final ObservableList<LogEntry> internalList = FXCollections.observableArrayList();
    private final ObservableList<LogEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent log entry as the given argument.
     */
    public boolean contains(LogEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLogEntry);
    }

    /**
     * Adds a log entry to the list.
     */
    public void add(LogEntry toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the log entry {@code target} in the list with {@code editedLogEntry}.
     * {@code target} must exist in the list.
     */
    public void setLogEntry(LogEntry target, LogEntry editedLogEntry) {
        requireAllNonNull(target, editedLogEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LogEntryNotFoundException();
        }
        internalList.set(index, editedLogEntry);
    }

    /**
     * Removes the equivalent log entry from the list.
     * The log entry must exist in the list.
     */
    public void remove(LogEntry toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LogEntryNotFoundException();
        }
    }

    public void setLogEntries(LogEntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code logEntries}.
     * {@code logEntries} must not contain duplicate persons.
     */
    public void setLogEntries(List<LogEntry> logEntries) {
        requireAllNonNull(logEntries);

        internalList.setAll(logEntries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<LogEntry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<LogEntry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryList // instanceof handles nulls
                        && internalList.equals(((LogEntryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
