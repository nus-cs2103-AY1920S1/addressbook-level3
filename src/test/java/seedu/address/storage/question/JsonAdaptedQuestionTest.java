package seedu.address.storage.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.question.JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.question.Question;
import seedu.address.testutil.question.QuestionBuilder;

public class JsonAdaptedQuestionTest {

    private static final String INVALID_QUESTION = "";
    private static final String INVALID_ANSWER = "";
    private static final String INVALID_TYPE = "";
    private static final String INVALID_OPTIONA = "";
    private static final String INVALID_OPTIONB = "";
    private static final String INVALID_OPTIONC = "";
    private static final String INVALID_OPTIOND = "";

    private static final String VALID_QUESTION = "What is 1+1?";
    private static final String VALID_ANSWER = "2";
    private static final String VALID_OPEN_TYPE = "open";
    private static final String VALID_MCQ_TYPE = "mcq";
    private static final String VALID_OPTIONA = "A";
    private static final String VALID_OPTIONB = "B";
    private static final String VALID_OPTIONC = "C";
    private static final String VALID_OPTIOND = "D";

    private static final Question question = new QuestionBuilder().withQuestion(VALID_QUESTION)
        .withAnswer(VALID_ANSWER).build();

    @Test
    public void toModelType_validQuestionDetails_returnsQuestion() throws Exception {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(this.question);
        assertEquals(this.question, question.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(INVALID_QUESTION, VALID_ANSWER,
            VALID_OPEN_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUESTION");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(null, VALID_ANSWER, VALID_OPEN_TYPE,
            VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "QUESTION");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, INVALID_ANSWER,
            VALID_OPEN_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "ANSWER");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, null,
            VALID_OPEN_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "ANSWER");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            INVALID_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            null, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidOptions_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, INVALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION A");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, INVALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION B");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, VALID_OPTIONB, INVALID_OPTIONC, VALID_OPTIOND);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION C");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, INVALID_OPTIOND);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION D");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullOptions_throwsIllegalValueException() {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, null, VALID_OPTIONB, VALID_OPTIONC, VALID_OPTIOND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION A");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, null, VALID_OPTIONC, VALID_OPTIOND);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION B");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, VALID_OPTIONB, null, VALID_OPTIOND);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION C");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);

        question = new JsonAdaptedQuestion(VALID_QUESTION, VALID_ANSWER,
            VALID_MCQ_TYPE, VALID_OPTIONA, VALID_OPTIONB, VALID_OPTIONC, null);
        expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "OPTION D");
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }
}
