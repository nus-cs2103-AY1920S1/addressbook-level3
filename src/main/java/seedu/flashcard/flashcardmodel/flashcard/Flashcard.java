package seedu.flashcard.flashcardmodel.flashcard;

import java.util.ArrayList;

import seedu.flashcard.flashcardmodel.Tag;
import seedu.flashcard.flashcardmodel.exceptions.DuplicateTagException;
import seedu.flashcard.flashcardmodel.exceptions.TagNotFoundException;

/**
 * A flashcardmodel must contain the following components
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
    private ArrayList<Tag> tags;

    /**
     * Question and Answer must be specified.
     */
    public Flashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
        this.score = new Score();
        this.id = new CardId();
        this.tags = new ArrayList<>();
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setQuestion(String newQuestion) {
        question.setQuestion(newQuestion);
    }

    public void setAnswer(String newAnswer) {
        answer.setAnswer(newAnswer);
    }

    /**
     * Add a new tag to the flash card
     * Guarantees there are no duplicate tags
     * @param t the tag to be added to the card
     * @throws DuplicateTagException if this card already has this tag
     */
    public void addTag(Tag t) throws DuplicateTagException {
        if (tags.contains(t)) {
            throw new DuplicateTagException();
        }
        tags.add(t);
    }

    /**
     * Delete the given tag from the flash card
     * Guarantees non-existing tags cannot be deleted
     * @param t the tag to be deleted from the card
     * @throws TagNotFoundException if this flashcardmodel does not have the given tag
     */
    public void deleteTag(Tag t) throws TagNotFoundException {
        if (!tags.contains(t)) {
            throw new TagNotFoundException();
        }
        tags.remove(t);
    }

    /**
     * When the user make an answer to a question, change the score of this flashcardmodel
     */
    public void isAnswerCorrect(boolean correct) {
        if (correct) {
            score.addCorrectAnswerNumber();
        } else {
            score.addWrongAnswerNumber();
        }
    };

    /**
     * While searching for a flashcardmodel, decide that whether this flashcardmodel contains the keyword or not.
     * @param s the keyword we are looking for
     * @return true if question, answer or the id contains the keyword, false otherwise
     */
    public boolean contains(String s) {
        return question.contains(s) || answer.contains(s) || id.contains(s);
    }

    /**
     * comparing whether two flash cards are the same or not.
     * Since each flashcardmodel has a unique id number, only comparing this id is enough
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
        return otherFlashcard.getId() == this.getId();
    }

    /**
     * While showing a card in the form of a string, we show its id number and its question.
     */
    @Override
    public String toString() {
        return String.format("Question: %s, id: %d", getQuestion(), id.getIdentityNumber());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
