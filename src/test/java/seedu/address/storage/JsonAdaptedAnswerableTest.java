package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAnswerable.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.answerable.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Question;

public class JsonAdaptedAnswerableTest {
    private static final String INVALID_QUESTION = "R@chel";
    private static final String INVALID_DIFFICULTY = "+651234";
    private static final String INVALID_CATEGORY = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_QUESTION = BENSON.getQuestion().toString();
    private static final String VALID_ANSWER = BENSON.getAnswer().toString();
    private static final String VALID_DIFFICULTY = BENSON.getDifficulty().toString();
    private static final String VALID_CATEGORY = BENSON.getCategory().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(INVALID_QUESTION, VALID_ANSWER, VALID_DIFFICULTY, VALID_CATEGORY, VALID_TAGS);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(null, VALID_ANSWER, VALID_DIFFICULTY, VALID_CATEGORY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION, VALID_ANSWER, INVALID_DIFFICULTY, VALID_CATEGORY, VALID_TAGS);
        String expectedMessage = Difficulty.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(VALID_QUESTION, VALID_ANSWER, null, VALID_CATEGORY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION, VALID_ANSWER, VALID_DIFFICULTY, INVALID_CATEGORY, VALID_TAGS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedAnswerable person = new JsonAdaptedAnswerable(VALID_QUESTION, VALID_ANSWER, VALID_DIFFICULTY, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedAnswerable person =
                new JsonAdaptedAnswerable(VALID_QUESTION, VALID_ANSWER, VALID_DIFFICULTY, VALID_CATEGORY, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
