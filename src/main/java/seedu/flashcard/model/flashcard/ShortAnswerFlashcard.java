package seedu.flashcard.model.flashcard;

/**
 * Represent the model of a short answer.
 */
public class ShortAnswerFlashcard extends Flashcard {

    public ShortAnswerFlashcard(Question question, Answer answer) {
        super(question, answer);
    }
    public ShortAnswerFlashcard(CardId id, Question question, Answer answer) {
        super(id, question, answer);
    }
}
