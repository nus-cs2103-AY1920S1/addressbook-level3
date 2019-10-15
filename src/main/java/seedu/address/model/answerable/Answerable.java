package seedu.address.model.answerable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.category.Category;
/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Answerable {

    // Identity fields
    private final Question question;
    private final Difficulty difficulty;

    // Data fields
    private final AnswerSet answerSet;
    private final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Answerable(Question question, AnswerSet answerSet, Difficulty difficulty, Set<Category> categories) {
        requireAllNonNull(question, difficulty, categories);
        this.question = question;
        this.answerSet = answerSet;
        this.difficulty = difficulty;
        this.categories.addAll(categories);
    }

    public Question getQuestion() {
        return question;
    }

    public AnswerSet getAnswerSet() {
        return answerSet;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

  
    /**
     * Returns true if both answerables with the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two answerables.
     */
    public boolean isSameAnswerable(Answerable otherAnswerable) {
        if (otherAnswerable == this) {
            return true;
        }

        return otherAnswerable != null
                && otherAnswerable.getQuestion().equals(getQuestion())
                && otherAnswerable.getAnswerSet().equals(getAnswerSet())
                && otherAnswerable.getDifficulty().equals(getDifficulty());
    }

    /**
     * Returns true if both Answerables have the same identity and data fields.
     * This defines a stronger notion of equality between two Answerables.
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
                && otherAnswerable.getAnswerSet().equals(getAnswerSet())
                && otherAnswerable.getDifficulty().equals(getDifficulty())
                && otherAnswerable.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answerSet, difficulty, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
                .append(getQuestion())
                .append(" Answers: ")
                .append(getAnswerSet())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
