package seedu.algobase.model.Problem;

import seedu.algobase.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Problem in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Problem {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final WebLink webLink;

    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Problem(Name name, Phone phone, WebLink webLink, Description description, Set<Tag> tags) {
        requireAllNonNull(name, phone, webLink, description, tags);
        this.name = name;
        this.phone = phone;
        this.webLink = webLink;
        this.description = description;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public WebLink getWebLink() {
        return webLink;
    }

    public Description getDescription() {
        return description;
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
                && (otherProblem.getPhone().equals(getPhone()) || otherProblem.getWebLink().equals(getWebLink()));
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
                && otherProblem.getWebLink().equals(getWebLink())
                && otherProblem.getDescription().equals(getDescription())
                && otherProblem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, webLink, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" WebLink: ")
                .append(getWebLink())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
