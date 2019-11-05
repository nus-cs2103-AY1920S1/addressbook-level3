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

    public OpenEndedQuestion duplicate() {
        return new OpenEndedQuestion(question, answer);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OpenEndedQuestion)) {
            return false;
        }

        OpenEndedQuestion e = (OpenEndedQuestion) other;
        return question.equals(e.question)
            && answer.equals(e.answer);
    }
}
