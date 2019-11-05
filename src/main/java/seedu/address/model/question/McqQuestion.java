package seedu.address.model.question;

/**
 * Represents an MCQ question in the question list.
 */
public class McqQuestion extends Question {

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    /**
     * Creates a new MCQ question.
     *
     * @param question to set.
     * @param answer   to the question.
     * @param optionA  The first option of the question.
     * @param optionB  The second option of the question.
     * @param optionC  The third option of the question.
     * @param optionD  The fourth option of the question.
     */
    public McqQuestion(String question, String answer, String optionA, String optionB,
        String optionC, String optionD) {
        super(question, answer);
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    /**
     * Returns the first option of an McqQuestion.
     *
     * @return The first option of the McqQuestion.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Returns the second option of an McqQuestion.
     *
     * @return The second option of the McqQuestion.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Returns the third option of an McqQuestion.
     *
     * @return The third option of the McqQuestion.
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Returns the fourth option of an McqQuestion.
     *
     * @return The fourth option of the McqQuestion.
     */
    public String getOptionD() {
        return optionD;
    }

    public McqQuestion duplicate() {
        return new McqQuestion(question, answer, optionA, optionB, optionC, optionD);
    }

    @Override
    public String toString() {
        return question
            + "\nAnswer: "
            + answer
            + "\nOptions:"
            + "\nA: " + optionA
            + " B:" + optionB
            + " C:" + optionC
            + " D:" + optionD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof McqQuestion)) {
            return false;
        }

        McqQuestion e = (McqQuestion) other;
        return question.equals(e.question)
            && answer.equals(e.answer)
            && optionA.equals(e.getOptionA())
            && optionB.equals(e.getOptionB())
            && optionC.equals(e.getOptionC())
            && optionD.equals(e.getOptionD());
    }
}
