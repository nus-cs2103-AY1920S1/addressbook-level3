package seedu.address.model.question;

/**
 * Represents an MCQ question in the question list.
 */
public class McqQuestion extends Question {

    /**
     * Creates a new question.
     *
     * @param question to set.
     * @param answer   to the question.
     */
    public McqQuestion(String question, String answer) {
        super(question, answer);
    }
}
