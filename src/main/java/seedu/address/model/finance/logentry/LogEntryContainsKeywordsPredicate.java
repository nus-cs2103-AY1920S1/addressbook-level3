package seedu.address.model.finance.logentry;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LogEntry}'s {@code Description}, {@code Place}, {@code TransactionMethod},
 * {@code From}, {@code To} matches any of the keywords given.
 */
public class LogEntryContainsKeywordsPredicate implements Predicate<LogEntry> {
    private final List<String> keywords;

    public LogEntryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(LogEntry logEntry) {
        boolean descriptionMatch = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(logEntry.getDescription().value, keyword));
        boolean tMethodMatch = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(logEntry.getTransactionMethod().value, keyword));
        boolean fromMatch = false;
        boolean toMatch = false;
        boolean placeMatch = false;
        if (logEntry instanceof SpendLogEntry) {
            placeMatch = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (SpendLogEntry) logEntry).getPlace().value, keyword));
        }
        if (logEntry instanceof IncomeLogEntry) {
            fromMatch = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (IncomeLogEntry) logEntry).getFrom().name, keyword));
        }
        if (logEntry instanceof BorrowLogEntry) {
            fromMatch = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (BorrowLogEntry) logEntry).getFrom().name, keyword));
        }
        if (logEntry instanceof LendLogEntry) {
            toMatch = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase((
                            (LendLogEntry) logEntry).getTo().name, keyword));
        }

        return descriptionMatch || tMethodMatch || placeMatch || fromMatch || toMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LogEntryContainsKeywordsPredicate) other).keywords)); // state check
    }

}
