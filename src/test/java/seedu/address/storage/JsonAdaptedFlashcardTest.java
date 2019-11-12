package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashcards.CS_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.flashcard.Title;
import seedu.address.model.flashcard.exceptions.StringToScheduleIncrementConversionException;


public class JsonAdaptedFlashcardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_TITLE = " ";
    private static final String INVALID_STATISTICS_LAST_VIEWED = "09-12-1998";
    private static final String INVALID_STATISTICS_TO_VIEW_NEXT = "20001-12-09";
    private static final String INVALID_STATISTICS_CURRENT_INCREMENT = "SEVENTH";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_QUESTION = CS_ONE.getQuestion().toString();
    private static final String VALID_ANSWER = CS_ONE.getAnswer().toString();
    private static final String VALID_TITLE = CS_ONE.getTitle().toString();
    private static final String VALID_STATISTICS_LAST_VIEWED = CS_ONE.getStatistics().getLastViewed().toString();
    private static final String VALID_STATISTICS_TO_VIEW_NEXT = CS_ONE.getStatistics().getToViewNext().toString();
    private static final String VALID_STATISTICS_CURRENT_INCREMENT =
            CS_ONE.getStatistics().getCurrentIncrement().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CS_ONE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(CS_ONE);
        assertEquals(CS_ONE, flashcard.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(INVALID_QUESTION, VALID_ANSWER, VALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null, VALID_ANSWER, VALID_TITLE,
                VALID_STATISTICS_LAST_VIEWED, VALID_STATISTICS_TO_VIEW_NEXT,
                VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, INVALID_ANSWER, VALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, null, VALID_TITLE,
                VALID_STATISTICS_LAST_VIEWED, VALID_STATISTICS_TO_VIEW_NEXT,
                VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, INVALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, null,
                VALID_STATISTICS_LAST_VIEWED, VALID_STATISTICS_TO_VIEW_NEXT,
                VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidStatisticsLastViewed_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE, INVALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = Statistics.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullStatisticsLastViewed_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE,
                null, VALID_STATISTICS_TO_VIEW_NEXT,
                VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedFlashcard.MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
                "lastViewed");
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidStatisticsToViewNext_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        INVALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = Statistics.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullStatisticsToViewNext_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE,
                VALID_STATISTICS_LAST_VIEWED, null,
                VALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedFlashcard.MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
               "toViewNext");
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidStatisticsCurrentIncrement_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, INVALID_STATISTICS_CURRENT_INCREMENT, VALID_TAGS);
        String expectedMessage = StringToScheduleIncrementConversionException.ERROR_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullStatisticsCurrentIncrement_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE,
                VALID_STATISTICS_LAST_VIEWED, VALID_STATISTICS_TO_VIEW_NEXT,
                null, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedFlashcard.MISSING_STATISTICS_FIELD_MESSAGE_FORMAT,
                "currentIncrement");
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(VALID_QUESTION, VALID_ANSWER, VALID_TITLE, VALID_STATISTICS_LAST_VIEWED,
                        VALID_STATISTICS_TO_VIEW_NEXT, VALID_STATISTICS_CURRENT_INCREMENT, invalidTags);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

}
