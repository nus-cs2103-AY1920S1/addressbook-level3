package seedu.address.model.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
        requireAllNonNull(question, answer);

        this.question = question;
        this.answer = answer;
    }

    /**
     * Creates an empty question
     */
    public Question(){}

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
        assert !question.isBlank() : "Question should not be empty.";
        this.question = question;
    }

    /**
     * Sets the answer.
     *
     * @param answer to set.
     */
    public void setAnswer(String answer) {
        assert !answer.isBlank() : "Answer should not be empty.";
        this.answer = answer;
    }

    public abstract Question duplicate();

    @Override
    public String toString() {
        return question
            + "\nAnswer: "
            + answer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Question)) {
            return false;
        }

        Question otherQuestion = (Question) o;
        return this.question.equals(otherQuestion.question);
    }
}
