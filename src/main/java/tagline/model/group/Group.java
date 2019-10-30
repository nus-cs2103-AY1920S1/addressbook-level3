//@@author e0031374
package tagline.model.group;

import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    // Identity fields
    private final GroupName groupName;

    // Data fields
    private final GroupDescription description;
    private final Set<MemberId> memberIds = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Group(GroupName groupName, GroupDescription description , Set<MemberId> memberIds) {
        requireAllNonNull(groupName, description, memberIds);
        //private final MemberModel members;
        this.groupName = groupName;
        this.description = description;
        this.memberIds.addAll(memberIds);
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupDescription getGroupDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MemberId> getMemberIds() {
        return Collections.unmodifiableSet(memberIds);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName());
    }

    /**
     * Returns a short version of the Group omitting Description
     * @return
     */
    public String toShortString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGroupName());
        addMembersToStringBuilder(builder);
        builder.append("\n");
        return builder.toString();
    }

    /**
     * Adds memberIds to {@code StringBuilder} if there are members in the group, otherwise
     * adds "None" to prompt that members are missing.
     */
    private void addMembersToStringBuilder(StringBuilder builder) {
        builder.append(" Members: ");
        if (getMemberIds().size() <= 0) {
            builder.append("None");
        } else {
            getMemberIds().forEach(builder::append);
        }
    }

    /**
     * Adds description to {@code StringBuilder} if there {@code GroupDescription} does not contain an empty string,
     * otherwise does not include " Description: " header to make formatting neater.
     */
    private void addGroupDescriptionToStringBuilder(StringBuilder builder) {
        if (getGroupDescription().value.length() <= 0) {
        } else {
            builder.append(" Description: ")
                    .append(getGroupDescription());
        }
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getGroupName().equals(getGroupName())
                && otherGroup.getMemberIds().equals(getMemberIds())
                && otherGroup.getGroupDescription().equals(getGroupDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(groupName, memberIds, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getGroupName());
        addGroupDescriptionToStringBuilder(builder);
        addMembersToStringBuilder(builder);
        return builder.toString();
    }
}
