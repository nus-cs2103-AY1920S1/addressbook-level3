package seedu.address.model.quiz.exceptions;

/**
 * Signals that modifiable list for quiz questions is empty.
 */
public class EmptyQuizQuestionException extends RuntimeException {
    public EmptyQuizQuestionException() {
        super("All quiz questions have been answered!");
    }
}
