package seedu.address.model.Problem;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Problem in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Problem {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Problem(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both problems of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two problems.
     */
    public boolean isSameProblem(Problem otherProblem) {
        if (otherProblem == this) {
            return true;
        }

        return otherProblem != null
                && otherProblem.getName().equals(getName())
                && (otherProblem.getPhone().equals(getPhone()) || otherProblem.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both problems have the same identity and data fields.
     * This defines a stronger notion of equality between two problems.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Problem)) {
            return false;
        }

        Problem otherProblem = (Problem) other;
        return otherProblem.getName().equals(getName())
                && otherProblem.getPhone().equals(getPhone())
                && otherProblem.getEmail().equals(getEmail())
                && otherProblem.getAddress().equals(getAddress())
                && otherProblem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
