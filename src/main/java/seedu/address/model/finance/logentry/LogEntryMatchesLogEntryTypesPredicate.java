package seedu.address.model.finance.logentry;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code LogEntry}'s type matches any of the log entry types specified by user.
 */
public class LogEntryMatchesLogEntryTypesPredicate implements Predicate<LogEntry> {

    private final List<String> entryTypes;

    public LogEntryMatchesLogEntryTypesPredicate(List<String> entryTypes) {
        this.entryTypes = entryTypes;
    }

    @Override
    public boolean test(LogEntry logEntry) {

        // If not specified, return all entries
        if (entryTypes.size() == 0) {
            return true;
        }
        String currType = logEntry.getLogEntryType();

        return entryTypes.stream()
                .anyMatch(type -> logEntry.getLogEntryType().equalsIgnoreCase(type));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryMatchesLogEntryTypesPredicate // instanceof handles nulls
                && entryTypes.equals(((LogEntryMatchesLogEntryTypesPredicate) other).entryTypes)); // state check
    }

}
