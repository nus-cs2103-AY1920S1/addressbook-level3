package seedu.address.model.phone.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.phone.Phone;

/**
 * Tests that a {@code Phone}'s {@code Colour} matches any of the keywords given.
 */
public class ColourContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public ColourContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(phone.getColour().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ColourContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ColourContainsKeywordsPredicate) other).keywords)); // state check
    }
}

