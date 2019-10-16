package seedu.address.model.phone.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.phone.Phone;

/**
 * Tests that a {@code Phone}'s {@code SerialNumber} matches any of the keywords given.
 */
public class SerialNumberContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public SerialNumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(phone.getSerialNumber().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SerialNumberContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SerialNumberContainsKeywordsPredicate) other).keywords)); // state check
    }
}

