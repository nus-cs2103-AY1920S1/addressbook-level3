package thrift.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import thrift.commons.util.StringUtil;

/**
 * Tests that a {@code Transaction}'s {@code Description} or {@code Remark} matches any of the keywords given.
 */
public class DescriptionOrRemarkContainsKeywordsPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public DescriptionOrRemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    if (StringUtil.containsWordIgnoreCase(transaction.getDescription().toString(), keyword)) {
                        return true;
                    } else if (StringUtil.containsWordIgnoreCase(transaction.getRemark().toString(), keyword)) {
                        return true;
                    } else {
                        return false;
                    }
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionOrRemarkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionOrRemarkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
