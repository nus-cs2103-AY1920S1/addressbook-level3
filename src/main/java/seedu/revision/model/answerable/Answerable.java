package seedu.revision.model.answerable;

import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.revision.model.category.Category;
/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Answerable {

    // Identity fields
    private final Question question;
    private final Difficulty difficulty;

    private final Set<Answer> correctAnswerSet;
    private final Set<Answer> wrongAnswerSet;
    private final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Answerable(Question question, Set<Answer> correctAnswerSet, Set<Answer> wrongAnswerSet,
              Difficulty difficulty, Set<Category> categories) {
        requireAllNonNull(question, difficulty, categories);
        this.question = question;
        this.correctAnswerSet = correctAnswerSet;
        this.wrongAnswerSet = wrongAnswerSet;
        this.difficulty = difficulty;
        this.categories.addAll(categories);
    }

    public Question getQuestion() {
        return question;
    }

    public Set<Answer> getCorrectAnswerSet() {
        return correctAnswerSet;
    }

    public Set<Answer> getWrongAnswerSet() {
        return wrongAnswerSet;
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
                && otherAnswerable.getCorrectAnswerSet().equals(getCorrectAnswerSet())
                && otherAnswerable.getWrongAnswerSet().equals(getWrongAnswerSet())
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
                && otherAnswerable.getCorrectAnswerSet().equals(getCorrectAnswerSet())
                && otherAnswerable.getWrongAnswerSet().equals(getWrongAnswerSet())
                && otherAnswerable.getDifficulty().equals(getDifficulty())
                && otherAnswerable.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, correctAnswerSet, wrongAnswerSet, difficulty, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Question: ")
                .append(getQuestion())
                .append(" Answers: ")
                .append("Correct Answers: " + getCorrectAnswerSet())
                .append("Wrong Answers: " + getWrongAnswerSet())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
