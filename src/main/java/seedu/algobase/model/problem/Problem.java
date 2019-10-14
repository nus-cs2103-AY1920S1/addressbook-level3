package seedu.algobase.model.problem;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.algobase.model.tag.Tag;

/**
 * Represents a Problem in the algobase.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Problem {

    // Identity fields
    private final Name name;
    private final Author author;
    private final WebLink webLink;

    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();
    private final Difficulty difficulty;
    private final Remark remark;
    private final Source source;

    /**
     * Every field must be present and not null.
     */
    public Problem(Name name, Author author, WebLink webLink, Description description, Set<Tag> tags,
                   Difficulty difficulty, Remark remark, Source source) {
        requireAllNonNull(name, author, webLink, description, tags, difficulty, remark, source);
        this.name = name;
        this.author = author;
        this.webLink = webLink;
        this.description = description;
        this.tags.addAll(tags);
        this.difficulty = difficulty;
        this.remark = remark;
        this.source = source;
    }


    public Name getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
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

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Remark getRemark() {
        return remark;
    }

    public Source getSource() {
        return source;
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
                && (otherProblem.getAuthor().equals(getAuthor()) || otherProblem.getWebLink().equals(getWebLink()));
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
                && otherProblem.getAuthor().equals(getAuthor())
                && otherProblem.getWebLink().equals(getWebLink())
                && otherProblem.getDescription().equals(getDescription())
                && otherProblem.getTags().equals(getTags())
                && otherProblem.getRemark().equals(getRemark())
                && otherProblem.getSource().equals(getSource());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, author, webLink, description, tags, remark, source);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Author: ")
                .append(getAuthor())
                .append(" WebLink: ")
                .append(getWebLink())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Remark: ")
                .append(getRemark())
                .append(" Source: ")
                .append(getSource());
        return builder.toString();
    }

}
