package seedu.address.model.question;

/**
 * Represents an open ended question in the question list.
 */
public class OpenEndedQuestion extends Question {

    /**
     * Creates a new question.
     *
     * @param question to set.
     * @param answer   to the question.
     */
    public OpenEndedQuestion(String question, String answer) {
        super(question, answer);
    }
}
