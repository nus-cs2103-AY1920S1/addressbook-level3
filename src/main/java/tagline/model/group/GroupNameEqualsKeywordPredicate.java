//@@author e0031374
package tagline.model.group;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Tests that a {@code Group}'s {@code Name} matches any of the keywords given.
 */
public class GroupNameEqualsKeywordPredicate implements Predicate<Group> {
    private final Collection<GroupName> keywords;

    public GroupNameEqualsKeywordPredicate(Collection<GroupName> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
            .anyMatch(keyword -> group.getGroupName().equals(keyword));
        //.anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupNameEqualsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((GroupNameEqualsKeywordPredicate) other).keywords)); // state check
    }

    /**
     * Creates and returns a {@code GroupNameEqualsKeywordPredicate} with the details of {@code name}
     * without any safeguards, only to be used when you are sure input is a valid GroupName and
     * not a user controlled variable, originally meant for testing.
     */
    public static GroupNameEqualsKeywordPredicate generatePredicate(String name) {
        assert GroupName.isValidGroupName(name);
        return GroupNameEqualsKeywordPredicate.generatePredicate(new GroupName(name));
    }

    /**-
     * Creates and returns a {@code GroupNameEqualsKeywordPredicate} with the details of {@code GroupName}
     * without any safeguards, only to be used when you are sure input is a valid GroupName and
     * not a user controlled variable, originally meant for testing.
     */
    public static GroupNameEqualsKeywordPredicate generatePredicate(GroupName name) {
        GroupName[] groupNames = { name };
        return new GroupNameEqualsKeywordPredicate(Arrays.asList(groupNames));
    }

    /**
     * Creates and returns a {@code GroupNameEqualsKeywordPredicate} with the {@GroupName} details of {@code Group}
     * without any safeguards, only to be used when you are sure input is a valid GroupName and
     * not a user controlled variable, originally meant for testing.
     */
    public static GroupNameEqualsKeywordPredicate generatePredicate(Group group) {
        GroupName[] groupNames = { group.getGroupName() };
        return new GroupNameEqualsKeywordPredicate(Arrays.asList(groupNames));
    }
}
