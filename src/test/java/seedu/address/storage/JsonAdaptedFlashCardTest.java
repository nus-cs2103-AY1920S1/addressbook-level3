package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.DELAY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;

public class JsonAdaptedFlashCardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_RATING = "bad";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_QUESTION = DELAY.getQuestion().toString();
    private static final String VALID_ANSWER = DELAY.getAnswer().toString();
    private static final String VALID_ADDRESS = DELAY.getRating().toString();
    private static final List<JsonAdaptedCategory> VALID_TAGS = DELAY.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedFlashcard person = new JsonAdaptedFlashcard(DELAY);
        assertEquals(DELAY, person.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard person =
                new JsonAdaptedFlashcard(INVALID_QUESTION, VALID_ANSWER,  VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard person = new JsonAdaptedFlashcard(null, VALID_ANSWER,  VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard person =
                new JsonAdaptedFlashcard(VALID_QUESTION, INVALID_ANSWER,  VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard person = new JsonAdaptedFlashcard(VALID_QUESTION, null,  VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedFlashcard person =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, INVALID_RATING, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        JsonAdaptedFlashcard person = new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER,  null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedCategory(INVALID_TAG));
        JsonAdaptedFlashcard person =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER,  VALID_ADDRESS, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
