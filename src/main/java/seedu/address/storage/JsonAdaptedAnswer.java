package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.answerable.Answer;

/**
 * Jackson-friendly version of {@link Answer}.
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
        answer = source.answer;
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
    public Answer toModelType() throws IllegalValueException {
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        return new Answer(answer);
    }

}
