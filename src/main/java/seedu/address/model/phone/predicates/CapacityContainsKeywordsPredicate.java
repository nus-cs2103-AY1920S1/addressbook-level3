package seedu.address.model.phone.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.phone.Phone;

/**
 * Tests that a {@code Phone}'s {@code Capacity} matches any of the keywords given.
 */
public class CapacityContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public CapacityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(phone.getCapacity().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CapacityContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CapacityContainsKeywordsPredicate) other).keywords)); // state check
    }
}

