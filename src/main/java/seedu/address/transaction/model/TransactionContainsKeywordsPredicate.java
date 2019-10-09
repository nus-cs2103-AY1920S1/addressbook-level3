package seedu.address.transaction.model;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.person.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TransactionContainsKeywordsPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getName(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getDate(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getCategory(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil
                                .containsWordIgnoreCase(transaction.getDescription(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil
                                .containsWordIgnoreCase(String.valueOf(transaction.getAmount()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TransactionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
