package seedu.address.model.finance.logentry;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LogEntry}'s {@code Description}, {@code Place}, {@code TransactionMethod},
 * {@code From}, {@code To} matches any of the keywords given.
 */
public class LogEntryContainsCategoriesPredicate implements Predicate<LogEntry> {
    private final List<String> categories;

    public LogEntryContainsCategoriesPredicate(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public boolean test(LogEntry logEntry) {

        // If not specified, return all entries
        if (categories.size() == 0) {
            return true;
        }

        boolean descriptionMatch = categories.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(logEntry.getDescription().value, keyword));
        boolean tMethodMatch = categories.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(logEntry.getTransactionMethod().value, keyword));
        boolean fromMatch = false;
        boolean toMatch = false;
        boolean placeMatch = false;
        if (logEntry instanceof SpendLogEntry) {
            placeMatch = categories.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (SpendLogEntry) logEntry).getPlace().value, keyword));
        }
        if (logEntry instanceof IncomeLogEntry) {
            fromMatch = categories.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (IncomeLogEntry) logEntry).getFrom().name, keyword));
        }
        if (logEntry instanceof BorrowLogEntry) {
            fromMatch = categories.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (BorrowLogEntry) logEntry).getFrom().name, keyword));
        }
        if (logEntry instanceof LendLogEntry) {
            toMatch = categories.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (LendLogEntry) logEntry).getTo().name, keyword));
        }

        return descriptionMatch || tMethodMatch || placeMatch || fromMatch || toMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryContainsCategoriesPredicate // instanceof handles nulls
                && categories.equals(((LogEntryContainsCategoriesPredicate) other).categories)); // state check
    }

}
