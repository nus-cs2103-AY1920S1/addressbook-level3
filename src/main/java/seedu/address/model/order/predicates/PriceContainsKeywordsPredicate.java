package seedu.address.model.order.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.order.Order;

/**
 * Tests that a {@code Order}'s {@code Price} matches any of the keywords given.
 */
public class PriceContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public PriceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(order.getPrice().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PriceContainsKeywordsPredicate) other).keywords)); // state check
    }

}
