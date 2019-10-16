package seedu.address.model.customer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Customer}'s {@code Name} matches any of the keywords given.
 */
public class CustomerNameContainsKeywordsPredicate implements Predicate<Customer> {
    private final List<String> keywords;

    public CustomerNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Customer customer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(customer.getCustomerName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CustomerNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
