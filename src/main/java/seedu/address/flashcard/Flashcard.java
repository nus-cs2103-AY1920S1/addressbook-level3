package seedu.address.flashcard;

import java.util.HashSet;

/**
 * A flashcard must contain the following components
 * 1. Question on the card
 * 2. Answer on the card
 * 3. A unique card ID to recognize itself to other cards
 * 4. A score class to record how many correct and wrong answers from the user
 * 5. A list of tags
 */
public abstract class Flashcard {

    private Question question;
    private Answer answer;
    private Score score;
    private CardId id;
    private HashSet<Tag> tags;

    /**
     * Question and Answer must be specified.
     */
    public Flashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Score getScore() {
        return score;
    }

    public CardId getId() {
        return id;
    }

    public HashSet<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public String deleteTag(Tag t) {
        tags.deleteTag(t);
        return "You've deleted this tag.";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Flashcard)) {
            return false;
        }

        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getId() == this.getId();
    }

    @Override
    public String toString() {
        return String.format("Question: %s, id: %d", this.getQuestion(), this.Id.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
