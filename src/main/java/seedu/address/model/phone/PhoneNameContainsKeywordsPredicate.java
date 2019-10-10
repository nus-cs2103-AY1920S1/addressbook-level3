package seedu.address.model.phone;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Phone}'s {@code PhoneName} matches any of the keywords given.
 */
public class PhoneNameContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public PhoneNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(phone.getPhoneName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}

