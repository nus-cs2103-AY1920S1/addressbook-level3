package seedu.address.model.member;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Mamber}'s {@code MemberName} matches any of the keywords given.
 */
public class MemberNameContainsKeywordsPredicate implements Predicate<Member> {
    private final List<String> keywords;

    public MemberNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Member member) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(member.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MemberNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
