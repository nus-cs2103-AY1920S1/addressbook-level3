package seedu.address.model.phone.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.phone.Phone;

/**
 * Tests that a {@code Phone}'s data fields matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Phone> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Phone phone) {

        if (keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .allMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(phone.getIdentityNumber().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getSerialNumber().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getPhoneName().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getBrand().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getColour().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getCapacity().toString(), keyword)
                                || StringUtil.containsWordIgnoreCase(phone.getCost().toString(), keyword)
                                || phone.getTags().stream().anyMatch(
                                    tag -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
