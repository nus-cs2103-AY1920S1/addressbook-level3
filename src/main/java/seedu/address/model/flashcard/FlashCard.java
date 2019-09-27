package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.category.Category;

/**
 * Represents a FlashCard in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class FlashCard {

    // Identity fields
    private final Question question;
    private final Answer answer;

    // Data fields
    private final Rating rating;
    private final Set<Category> categories = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public FlashCard(Question question, Answer answer, Rating rating, Set<Category> categories) {
        requireAllNonNull(question, answer, rating, categories);
        this.question = question;
        this.answer = answer;
        this.rating = rating;
        this.categories.addAll(categories);
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Rating getRating() {
        return rating;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns true if both persons of the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(FlashCard otherFlashCard) {
        if (otherFlashCard == this) {
            return true;
        }
        return otherFlashCard != null
                && otherFlashCard.getQuestion().equals(getQuestion())
                && (otherFlashCard.getAnswer().equals(getAnswer()));
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

        if (!(other instanceof FlashCard)) {
            return false;
        }

        FlashCard otherFlashCard = (FlashCard) other;
        return otherFlashCard.getQuestion().equals(getQuestion())
                && otherFlashCard.getAnswer().equals(getAnswer())
                && otherFlashCard.getRating().equals(getRating())
                && otherFlashCard.getCategories().equals(getCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, rating, categories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Rating: ")
                .append(getRating())
                .append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
