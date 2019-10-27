package seedu.jarvis.model.finance;

import java.util.List;
import java.util.function.Predicate;

import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.model.finance.installment.Installment;

/**
 * Tests that a {@code Purchase}'s {@code PurchaseDescription} matches any of the keywords given.
 */
public class InstallmentNameContainsKeywordsPredicate implements Predicate<Installment> {
    private final List<String> keywords;

    public InstallmentNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Installment installment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(installment.getDescription()
                        .getInstallmentDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InstallmentNameContainsKeywordsPredicate)
                && keywords.equals(((InstallmentNameContainsKeywordsPredicate) other).keywords);
    }
}
