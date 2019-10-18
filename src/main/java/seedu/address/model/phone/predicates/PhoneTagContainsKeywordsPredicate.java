package seedu.address.model.phone.predicates;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.phone.Phone;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Phone}'s {@code Name} matches any of the keywords given.
 */
public class PhoneTagContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public PhoneTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {
        Set<Tag> tags = phone.getTags();
        return keywords.stream()
                .anyMatch(keyword -> phone.getTags().stream().anyMatch(
                        tag -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword)
                ));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
