package seedu.address.model.order.predicates;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Order}'s {@code Name} matches any of the keywords given.
 */
public class OrderTagContainsKeywordsPredicate implements Predicate<Order> {
    private final List<String> keywords;

    public OrderTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Order order) {
        Set<Tag> tags = order.getTags();
        return keywords.stream()
                .anyMatch(keyword -> order.getTags().stream().anyMatch(
                    tag -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword)
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((OrderTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
