package seedu.address.model.member;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class Member {
    // Identity fields
    private final MemberName name;
    private final MemberID id;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Member(MemberName name, MemberID id, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.id = id;
        this.tags.addAll(tags);
    }

    // TODO add multiple constructors so that users can add additional info later

    public MemberID getID() {
        return id;
    }

    public MemberName getName() {
        return name;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return otherMember != null
                && otherMember.getName().equals(getName());
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

        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return otherMember.getName().equals(getName())
                && (otherMember.getID() == getID())
                && otherMember.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("Member ID: " + getID())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
