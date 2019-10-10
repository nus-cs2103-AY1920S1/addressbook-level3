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
     */
    public McqQuestion(String question, String answer) {
        super(question, answer);
    }

    /**
     * Creates a new MCQ question.
     *
     * @param question to set.
     * @param answer   to the question.
     * @param optionA The first option of the question.
     * @param optionB The second option of the question.
     * @param optionC The third option of the question.
     * @param optionD The fourth option of the question.
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
     * @return The first option of the McqQuestion.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Returns the second option of an McqQuestion.
     * @return The second option of the McqQuestion.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Returns the third option of an McqQuestion.
     * @return The third option of the McqQuestion.
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Returns the fourth option of an McqQuestion.
     * @return The fourth option of the McqQuestion.
     */
    public String getOptionD() {
        return optionD;
    }
}
