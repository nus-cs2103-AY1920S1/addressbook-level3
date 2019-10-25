package seedu.revision.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.logic.parser.QuestionType;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.answer.McqAnswer;
import seedu.revision.model.answerable.answer.SaqAnswer;
import seedu.revision.model.answerable.answer.TfAnswer;

/**
 * Jackson-friendly version of {@link answer}.
 */
class JsonAdaptedAnswer {

    private final String answer;

    /**
     * Constructs a {@code JsonAdaptedAnswer} with the given {@code answerDescription}.
     */
    @JsonCreator
    public JsonAdaptedAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Converts a given {@code answer} into this class for Jackson use.
     */
    public JsonAdaptedAnswer(Answer source) {
        answer = source.toString();
    }

    @JsonValue
    public String getAnswer() {
        return answer;
    }

    /**
     * Converts this Jackson-friendly adapted category object into the model's {@code category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Answer toModelType(String questionType) throws IllegalValueException {

        switch(questionType.toLowerCase()) {
        case "mcq":
            return new McqAnswer(answer);
        case "tf":
            return new TfAnswer(answer);
        case "saq":
            return new SaqAnswer(answer);
        default:
            throw new IllegalValueException(QuestionType.MESSAGE_CONSTRAINTS);
        }
    }

}
