package seedu.address.model.finance.logentry;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code LogEntry}'s associated {@code Category}, matches the
 * categories given.
 * Not case-sensitive.
 */
public class LogEntryMatchesAmountPredicate implements Predicate<LogEntry> {
    private final List<String> amount;

    public LogEntryMatchesAmountPredicate(List<String> amounts) {
        this.amount = amounts;
    }

    @Override
    public boolean test(LogEntry logEntry) {

        // If not specified, return all entries
        if (amount.size() == 0) {
            return true;
        }
        // TODO
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryMatchesAmountPredicate // instanceof handles nulls
                && amount.equals(((LogEntryMatchesAmountPredicate) other).amount)); // state check
    }

}
