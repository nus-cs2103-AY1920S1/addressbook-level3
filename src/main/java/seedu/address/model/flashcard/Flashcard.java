package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.StudyBuddyItem;
import seedu.address.model.tag.Tag;

/**
 * Represents a flashcard in the StudyBuddy application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Flashcard extends StudyBuddyItem {

    private final Question question;
    private final Answer answer;
    private final Title title;
    private final Statistics statistics;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Question question, Answer answer, Title title, Set<Tag> tags) {
        super(tags);
        requireAllNonNull(question, answer, title, tags);
        this.question = question;
        this.answer = answer;
        this.title = title;
        this.statistics = new Statistics();
    }

    /**
     * Every field must be present and not null. For use when converting from JSON.
     */
    public Flashcard(Question question, Answer answer, Title title, Statistics statistics, Set<Tag> tags) {
        super(tags);
        requireAllNonNull(question, answer, statistics, tags);
        this.question = question;
        this.answer = answer;
        this.title = title;
        this.statistics = statistics;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Title getTitle() {
        return title;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    /**
     *
     */
    public void updateStatistics() {
        this.statistics.onView();
    }

    /**
     * Returns true if both flashcards have either the same Question or Title.
     * This defines a weaker notion of equality between two flashcards.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }

        return otherFlashcard != null && (otherFlashcard.getTitle().equals(getTitle())
                || otherFlashcard.getQuestion().equals(getQuestion()));
    }

    /**
     * Returns true if both flashcards have all the same fields.
     * This defines a stronger notion of equality between two flashcards.
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
                && otherFlashcard.getAnswer().equals(getAnswer())
                && otherFlashcard.getTitle().equals(getTitle())
                && otherFlashcard.getTags().equals(getTags())
                && otherFlashcard.getStatistics().equals(getStatistics());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, title, getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\tTitle: ")
                .append(getTitle() + "\n")
                .append("\tTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
