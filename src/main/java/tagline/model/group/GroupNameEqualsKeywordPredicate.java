package tagline.model.group;

import java.util.List;
import java.util.function.Predicate;

import tagline.commons.util.StringUtil;

/**
 * Tests that a {@code Group}'s {@code Name} matches any of the keywords given.
 */
public class GroupNameEqualsKeywordPredicate implements Predicate<Group> {
    private final List<String> keywords;

    public GroupNameEqualsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupNameEqualsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((GroupNameEqualsKeywordPredicate) other).keywords)); // state check
    }

}
