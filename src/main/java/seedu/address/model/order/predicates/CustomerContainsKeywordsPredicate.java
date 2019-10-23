package seedu.address.model.order.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.order.Order;

/**
 * Tests that a {@code Order}'s {@code Customer} matches any of the keywords given.
 */
public class CustomerContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public CustomerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(order.getCustomer().getCustomerName().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(order.getCustomer().getContactNumber().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CustomerContainsKeywordsPredicate) other).keywords)); // state check
    }

}
