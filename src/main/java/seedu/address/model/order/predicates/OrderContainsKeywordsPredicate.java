package seedu.address.model.order.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.order.Order;

/**
 * Tests that a {@code Order}'s data fields matches any of the keywords given.
 */
public class OrderContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public OrderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {

        if (keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .allMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(order.getCustomer().getCustomerName().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(
                                order.getCustomer().getContactNumber().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(
                                order.getCustomer().getEmail().toString(), keyword)

                        || StringUtil.containsWordIgnoreCase(order.getPhone().getCapacity().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getColour().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getIdentityNumber().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getPhoneName().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getBrand().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getSerialNumber().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPhone().getCost().toString(), keyword)

                        || StringUtil.containsWordIgnoreCase(order.getId().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getPrice().toString(), keyword)
                        || StringUtil.containsWordIgnoreCase(order.getStatus().toString(), keyword)
                        || order.getTags().stream().anyMatch(tag ->
                                StringUtil.containsWordIgnoreCase(tag.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OrderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
