package seedu.address.model.answerable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Answerable in the category book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Answerable {

    // Identity fields
    private final Question question;
    private final Difficulty difficulty;

    // Data fields
    private final Answer answer;
    private final Category category;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Answerable(Question question, Answer answer, Difficulty difficulty, Category category, Set<Tag> tags) {
        requireAllNonNull(question, difficulty, category, tags);
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        this.category = category;
        this.tags.addAll(tags);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        if (otherAnswerable == this) {
            return true;
        }

        return otherAnswerable != null
                && otherAnswerable.getQuestion().equals(getQuestion()) && (otherAnswerable.getDifficulty().equals(getDifficulty()));
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

        if (!(other instanceof Answerable)) {
            return false;
        }

        Answerable otherAnswerable = (Answerable) other;
        return otherAnswerable.getQuestion().equals(getQuestion())
                && otherAnswerable.getDifficulty().equals(getDifficulty())
                && otherAnswerable.getCategory().equals(getCategory())
                && otherAnswerable.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, difficulty, category, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Category: ")
                .append(getCategory())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
