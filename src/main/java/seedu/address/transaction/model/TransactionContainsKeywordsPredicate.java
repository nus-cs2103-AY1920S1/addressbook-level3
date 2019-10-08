package seedu.address.transaction.model;

import java.util.List;
import java.util.function.Predicate;
import seedu.address.person.commons.util.StringUtil;

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
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getDescription(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getCategory(), keyword)) ||
                keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getDate(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.transaction.model.TransactionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((seedu.address.transaction.model.TransactionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
