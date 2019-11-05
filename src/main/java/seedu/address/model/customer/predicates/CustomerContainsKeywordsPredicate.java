package seedu.address.model.customer.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.customer.Customer;

/**
 * Tests that a {@code Customer}'s data fields matches any of the keywords given.
 */
public class CustomerContainsKeywordsPredicate implements Predicate<Customer> {
    private final List<String> keywords;

    public CustomerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Customer customer) {

        if (keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .allMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(customer.getCustomerName().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(customer.getContactNumber().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(customer.getEmail().toString(), keyword)
                                || customer.getTags().stream().anyMatch(
                                    tag -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CustomerContainsKeywordsPredicate) other).keywords)); // state check
    }

}
