package seedu.address.model.customer.predicates;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.customer.Customer;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Customer}'s {@code Name} matches any of the keywords given.
 */
public class CustomerTagContainsKeywordsPredicate implements Predicate<Customer> {
    private final List<String> keywords;

    public CustomerTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Customer customer) {
        Set<Tag> tags = customer.getTags();
        return keywords.stream()
                .anyMatch(keyword -> customer.getTags().stream().anyMatch(
                        tag -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword)
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CustomerTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
