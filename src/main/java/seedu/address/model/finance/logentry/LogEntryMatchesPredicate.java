package seedu.address.model.finance.logentry;

import java.util.function.Predicate;

/**
 * Tests that a {@code LogEntry} matches all predicates from
 * {@LogEntryMatchesLogEntryTypesPredicate}, {@LogEntryContainsKeywordsPredicate},
 * {@LogEntryContainsCategoriesPredicate} if user has specified them.
 */
public class LogEntryMatchesPredicate implements Predicate<LogEntry> {

    private final LogEntryMatchesAmountPredicate amountPredicate;
    private final LogEntryMatchesLogEntryTypesPredicate logEntryTypesPredicate;
    private final LogEntryContainsKeywordsPredicate keywordsPredicate;
    private final LogEntryContainsCategoriesPredicate categoriesPredicate;

    public LogEntryMatchesPredicate(LogEntryMatchesAmountPredicate amountPredicate,
                                    LogEntryMatchesLogEntryTypesPredicate logEntryTypesPredicate,
                                    LogEntryContainsKeywordsPredicate keywordsPredicate,
                                    LogEntryContainsCategoriesPredicate categoriesPredicate) {
        this.amountPredicate = amountPredicate;
        this.logEntryTypesPredicate = logEntryTypesPredicate;
        this.keywordsPredicate = keywordsPredicate;
        this.categoriesPredicate = categoriesPredicate;
    }

    @Override
    public boolean test(LogEntry logEntry) {
        return amountPredicate.test(logEntry)
               && logEntryTypesPredicate.test(logEntry)
               && keywordsPredicate.test(logEntry)
               && categoriesPredicate.test(logEntry);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntryMatchesPredicate // instanceof handles nulls
                && amountPredicate.equals(((LogEntryMatchesPredicate) other).amountPredicate)
                && logEntryTypesPredicate.equals(((LogEntryMatchesPredicate) other).logEntryTypesPredicate)
                && keywordsPredicate.equals(((LogEntryMatchesPredicate) other).keywordsPredicate)
                && categoriesPredicate.equals(((LogEntryMatchesPredicate) other).categoriesPredicate)); // state check
    }

}
