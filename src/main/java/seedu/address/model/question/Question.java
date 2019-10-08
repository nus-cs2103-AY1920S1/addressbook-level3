package seedu.address.model.question;

/**
 * Represents a question in the question list.
 */
public abstract class Question {

    protected String question;
    protected String answer;

    /**
     * Creates a new question.
     *
     * @param question to set.
     * @param answer   to the question.
     */
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the question.
     *
     * @return Question string
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Returns the answer.
     *
     * @return Answer string
     */
    public String getAnswer() {
        return this.answer;
    }

    /**
     * Sets the question.
     *
     * @param question to set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the answer.
     *
     * @param answer to set.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return question;
    }
}
