package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class IDContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public IDContainsKeywordsPredicate(List<String> keywords) {
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
                || (other instanceof IDContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IDContainsKeywordsPredicate) other).keywords)); // state check
    }

}
