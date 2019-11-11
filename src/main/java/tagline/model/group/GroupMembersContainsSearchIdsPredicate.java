//@@author e0031374
package tagline.model.group;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Tests that a {@code Contact}'s {@code Name} matches any of the keywords given.
 */
public class GroupMembersContainsSearchIdsPredicate implements Predicate<Group> {
    private final Collection<MemberId> keywords;

    public GroupMembersContainsSearchIdsPredicate(Collection<MemberId> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> group.getMemberIds().contains(keyword));
        //.anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupMembersContainsSearchIdsPredicate // instanceof handles nulls
                && keywords.equals(((GroupMembersContainsSearchIdsPredicate) other).keywords)); // state check
    }

}
