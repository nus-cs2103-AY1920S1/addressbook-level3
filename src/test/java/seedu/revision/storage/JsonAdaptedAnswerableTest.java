package seedu.revision.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.revision.storage.JsonAdaptedAnswerable.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.B_ANSWERABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.exceptions.IllegalValueException;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;

public class JsonAdaptedAnswerableTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_DIFFICULTY = "+651234";
    private static final String INVALID_CATEGORY = "";

    private static final String VALID_QUESTION_TYPE = "MCQ";
    private static final String VALID_QUESTION = B_ANSWERABLE.getQuestion().toString();
    private static final List<JsonAdaptedAnswer> VALID_CORRECT_ANSWER = B_ANSWERABLE.getCorrectAnswerList().stream()
            .map(JsonAdaptedAnswer::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAnswer> VALID_WRONG_ANSWER = B_ANSWERABLE.getWrongAnswerList().stream()
            .map(JsonAdaptedAnswer::new)
            .collect(Collectors.toList());
    private static final String VALID_DIFFICULTY = B_ANSWERABLE.getDifficulty().toString();
    private static final List<JsonAdaptedCategory> VALID_CATEGORIES = B_ANSWERABLE.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedAnswerable answerable = new JsonAdaptedAnswerable(B_ANSWERABLE);
        assertEquals(B_ANSWERABLE, answerable.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION_TYPE, INVALID_QUESTION, VALID_CORRECT_ANSWER,
                        VALID_WRONG_ANSWER, VALID_DIFFICULTY, VALID_CATEGORIES);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(VALID_QUESTION_TYPE, null, VALID_CORRECT_ANSWER,
                VALID_WRONG_ANSWER, VALID_DIFFICULTY, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION_TYPE, VALID_QUESTION, VALID_CORRECT_ANSWER, VALID_WRONG_ANSWER,
                        INVALID_DIFFICULTY, VALID_CATEGORIES);
        String expectedMessage = Difficulty.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(VALID_QUESTION_TYPE, VALID_QUESTION,
                VALID_CORRECT_ANSWER, VALID_WRONG_ANSWER, null, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCategories = new ArrayList<>(VALID_CATEGORIES);
        invalidCategories.add(new JsonAdaptedCategory(INVALID_CATEGORY));
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION_TYPE, VALID_QUESTION, VALID_CORRECT_ANSWER, VALID_WRONG_ANSWER,
                VALID_DIFFICULTY, invalidCategories);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
