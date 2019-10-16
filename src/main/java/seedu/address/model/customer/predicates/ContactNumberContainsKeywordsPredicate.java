package seedu.address.model.customer.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.customer.Customer;

/**
 * Tests that a {@code Customer}'s {@code Name} matches any of the keywords given.
 */
public class ContactNumberContainsKeywordsPredicate implements Predicate<Customer> {
    private final List<String> keywords;

    public ContactNumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Customer customer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(customer.getContactNumber().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactNumberContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactNumberContainsKeywordsPredicate) other).keywords)); // state check
    }

}
