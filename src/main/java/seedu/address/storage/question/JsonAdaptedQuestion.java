package seedu.address.storage.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

/**
 * Jackson-friendly version of {@link Question}.
 */
class JsonAdaptedQuestion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "question %s field is missing!";

    private final String question;
    private final String answer;
    private final String type;

    private String optionA = null;
    private String optionB = null;
    private String optionC = null;
    private String optionD = null;

    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given question details.
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("question") String question,
        @JsonProperty("answer") String answer,
        @JsonProperty("type") String type,
        @JsonProperty("optionA") String optionA,
        @JsonProperty("optionB") String optionB,
        @JsonProperty("optionC") String optionC,
        @JsonProperty("optionD") String optionD) {
        this.question = question;
        this.answer = answer;
        this.type = type;

        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     */
    public JsonAdaptedQuestion(Question source) {
        question = source.getQuestion();
        answer = source.getAnswer();

        if (source instanceof OpenEndedQuestion) {
            type = "open";
        } else if (source instanceof McqQuestion) {
            type = "mcq";
            optionA = ((McqQuestion) source).getOptionA();
            optionB = ((McqQuestion) source).getOptionB();
            optionC = ((McqQuestion) source).getOptionC();
            optionD = ((McqQuestion) source).getOptionD();
        } else {
            type = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               question.
     */
    public Question toModelType() throws IllegalValueException {
        if (question == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUESTION"));
        }
        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ANSWER"));
        }
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE"));
        }

        if (type.equals("open")) {
            return new OpenEndedQuestion(question, answer);
        } else if (type.equals("mcq")) {
            if (optionA == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION A"));
            }
            if (optionB == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION B"));
            }
            if (optionC == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION C"));
            }
            if (optionD == null) {
                throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION D"));
            }

            return new McqQuestion(question, answer, optionA, optionB, optionC, optionD);
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE"));
        }

    }

}
