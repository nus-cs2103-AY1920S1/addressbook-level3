package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Order}'s {@code Id} matches any of the keywords given.
 */
public class IdContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public IdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(order.getId().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IdContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IdContainsKeywordsPredicate) other).keywords)); // state check
    }

}
