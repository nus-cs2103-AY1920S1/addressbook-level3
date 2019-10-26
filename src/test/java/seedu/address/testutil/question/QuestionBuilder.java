package seedu.address.testutil.question;

import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

/**
 * A utility class to help with building Question objects.
 * Example usage: <br>
 *     {@code QuestionBuilder qb = new QuestionBuilder().withQuestion("What is 1+1?").build();}
 */
public class QuestionBuilder {

    public static final String DEFAULT_QUESTION = "How awesome is Njoy?";
    public static final String DEFAULT_ANSWER = "Awesome";
    public static final String DEFAULT_TYPE = "open";
    public static final String DEFAULT_OPTIONA = "Very Awesome";
    public static final String DEFAULT_OPTIONB = "Awesome";
    public static final String DEFAULT_OPTIONC = "OK";
    public static final String DEFAULT_OPTIOND = "No Comment";

    private String question;
    private String answer;
    private String type;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuestionBuilder() {
        question = DEFAULT_QUESTION;
        answer = DEFAULT_ANSWER;
        type = DEFAULT_TYPE;
        optionA = DEFAULT_OPTIONA;
        optionB = DEFAULT_OPTIONB;
        optionC = DEFAULT_OPTIONC;
        optionD = DEFAULT_OPTIOND;
    }

    /**
     * Sets the {@code Question} of the {@code Question} that we are building.
     */
    public QuestionBuilder withQuestion(String question) {
        this.question = question;
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Question} that we are building.
     */
    public QuestionBuilder withAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Question} that we are building.
     */
    public QuestionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the {@code OptionA} of the {@code Question} that we are building.
     */
    public QuestionBuilder withOptionA(String optionA) {
        this.optionA = optionA;
        return this;
    }

    /**
     * Sets the {@code OptionB} of the {@code Question} that we are building.
     */
    public QuestionBuilder withOptionB(String optionB) {
        this.optionB = optionB;
        return this;
    }

    /**
     * Sets the {@code OptionC} of the {@code Question} that we are building.
     */
    public QuestionBuilder withOptionC(String optionC) {
        this.optionC = optionC;
        return this;
    }

    /**
     * Sets the {@code OptionD} of the {@code Question} that we are building.
     */
    public QuestionBuilder withOptionD(String optionD) {
        this.optionD = optionD;
        return this;
    }

    /**
     * Builds the question with the specified fields.
     * @return Question object.
     */
    public Question build() {
        if (type.equals("mcq")) {
            return new McqQuestion(question, answer, optionA, optionB, optionC, optionD);
        } else { // Default to open ended
            return new OpenEndedQuestion(question, answer);
        }
    }
}
