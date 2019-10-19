package seedu.address.model.earnings;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Earnings}'s {@code ClassId} matches any of the keywords given.
 */
public class ClassIdContainKeywordPredicate implements Predicate<Earnings> {

    private final List<String> keywords;

    public ClassIdContainKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Earnings earnings) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(earnings.getClassId().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassIdContainKeywordPredicate // instanceof handles nulls
                && keywords.equals(((ClassIdContainKeywordPredicate) other).keywords)); // state check
    }


}
