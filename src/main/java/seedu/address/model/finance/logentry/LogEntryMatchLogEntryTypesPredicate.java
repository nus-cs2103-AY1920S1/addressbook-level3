package seedu.address.model.finance.logentry;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code LogEntry}'s type matches any of the log entry types specified by user.
 */
public class LogEntryMatchLogEntryTypesPredicate implements Predicate<LogEntry> {
    private final List<String> entryTypes;

    public LogEntryMatchLogEntryTypesPredicate(List<String> entryTypes) {
        this.entryTypes = entryTypes;
    }

    @Override
    public boolean test(LogEntry logEntry) {
        return entryTypes.stream()
                .anyMatch(type -> logEntry.getLogEntryType().equalsIgnoreCase(type));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryMatchLogEntryTypesPredicate // instanceof handles nulls
                && entryTypes.equals(((LogEntryMatchLogEntryTypesPredicate) other).entryTypes)); // state check
    }

}
