package seedu.flashcard.model.flashcard;

import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.flashcard.model.tag.Tag;

/**
 * Represents a Flashcard in the flashcard list.
 * Guarantees: details are present and not null, field values are validated, immutable
 */
public abstract class Flashcard {

    // Identity fields
    protected final Question question;

    // Data fields
    protected final Definition definition;
    protected final Set<Tag> tags = new HashSet<>();
    protected final Answer answer;
    protected final Score score;

    /**
     * This initializer is used when the user is creating a new flashcard
     */
    public Flashcard(Question question, Definition definitions, Set<Tag> tags, Answer answer) {
        requireAllNonNull(question, definitions, tags);
        this.question = question;
        this.definition = definitions;
        this.tags.addAll(tags);
        this.answer = answer;
        this.score = new Score();
    }

    /**
     * Creates a deep copy of a given flashcard.
     * @param toClone the flashcard to be cloned.
     */
    public Flashcard(Flashcard toClone) {
        this.question = toClone.getQuestion();
        this.definition = toClone.getDefinition();
        this.tags.addAll(toClone.getTags());
        this.answer = toClone.getAnswer();
        this.score = toClone.getScore();
    }

    /**
     * This initializer should only be accessed by the storage package, because this initializer ensures the
     * flashcard scores can be reloaded
     */
    public Flashcard(Question question, Definition definition, Set<Tag> tags, Answer answer, Score score) {
        requireAllNonNull(question, definition, tags, answer, score);
        this.question = question;
        this.definition = definition;
        this.tags.addAll(tags);
        this.answer = answer;
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    /**
     * Returns an immutable definition, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Returns an immutable answer, which throws {@code UnsupportedOperationException}
     * if modification attempted
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * Returns an immutable score, which throws {@code UnsupportedOperationException}
     * if modification attempted
     */
    public Score getScore() {
        return score;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if this flashcard has the following tag.
     */
    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    /**
     * Returns true if this flashcard has any one of the tags in the given tag sets.
     */
    public boolean hasAnyTag(Set<Tag> tags) {
        for (Tag tag : tags) {
            if (getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the tag from this flashcard.
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Defines that if and only if two flashcards containing the same question can be considered as the same.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }
        return otherFlashcard != null
                && otherFlashcard.getQuestion().equals(getQuestion());
    }

    /**
     * Checks that an input answer is the same as the correct answer.
     * @param inputAnswer The answer that the user input.
     * @return Returns true if the answer is correct.
     */
    public boolean checkAnswer(Answer inputAnswer) {
        if (inputAnswer.equals(answer)) {
            score.incrementCorrectAnswer();
            return true;
        } else {
            score.incrementWrongAnswer();
            return false;
        }
    }

    public void skipAnswer() {
        score.incrementWrongAnswer();
    }

    /**
     * Checks whether the card is a MCQ Flashcard.
     * @return Returns true if the card is a McqFlashcard.
     */
    public abstract boolean isMcq();
    /**
     * Get the representation of this flashcard containing the answer.
     */
    public String fullString() {
        return toString() + "\nAnswer:\n" + answer;
    }


    /**
     * Check if this flashcard's answer is in choice or not. Only applies for MCQ flashcard.
     */
    public abstract boolean isValidFlashcard();

    /**
     * Returns true if both the question and the definitions and the tags are the same.
     * This is stronger than {@code isSameFlashCard}
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Flashcard)) {
            return false;
        }
        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getQuestion().equals(getQuestion())
                && otherFlashcard.getDefinition().equals(getDefinition())
                && otherFlashcard.getAnswer().equals(getAnswer())
                && otherFlashcard.getTags().equals(getTags())
                && otherFlashcard.getScore().equals(getScore());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion()).append("\n")
                .append("\nDefinitions:").append("\n")
                .append(getDefinition()).append("\n");
        if (!tags.isEmpty()) {
            builder.append("\nTags:").append("\n");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, definition, tags);
    }
}
