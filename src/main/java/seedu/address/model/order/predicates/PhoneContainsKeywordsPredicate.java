package seedu.address.model.order.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.order.Order;

/**
 * Tests that a {@code Order}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(order.getPhone().getBrand().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getCapacity().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getColour().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getIdentityNumber().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getPhoneName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
