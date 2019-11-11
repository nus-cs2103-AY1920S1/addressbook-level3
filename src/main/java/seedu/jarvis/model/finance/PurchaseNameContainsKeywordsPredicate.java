package seedu.jarvis.model.finance;

import java.util.List;
import java.util.function.Predicate;

import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.model.finance.purchase.Purchase;

/**
 * Tests that a {@code Purchase}'s {@code PurchaseDescription} matches any of the keywords given.
 */
public class PurchaseNameContainsKeywordsPredicate implements Predicate<Purchase> {

    private final List<String> keywords;

    public PurchaseNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Purchase purchase) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(purchase.getDescription()
                        .getPurchaseDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PurchaseNameContainsKeywordsPredicate)
                && keywords.equals(((PurchaseNameContainsKeywordsPredicate) other).keywords);
    }
}
