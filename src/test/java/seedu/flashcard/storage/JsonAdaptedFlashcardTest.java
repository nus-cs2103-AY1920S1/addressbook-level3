package seedu.flashcard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.flashcard.testutil.TypicalFlashcard.DAXING_AIRPORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Flashcard;

public class JsonAdaptedFlashcardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_DEFINITION = " ";
    private static final JsonAdaptedChoice INVALID_CHOICE = new JsonAdaptedChoice(" ");
    private static final String INVALID_ANSWER = " ";
    private static final JsonAdaptedTag INVALID_TAG = new JsonAdaptedTag(" ");
    private static final String INVALID_TYPE = "CocoFlashcard";

    private static final String VALID_QUESTION = DAXING_AIRPORT.getQuestion().question;
    private static final String VALID_DEFINITION = DAXING_AIRPORT.getDefinition().definition;
    private static final String VALID_ANSWER = DAXING_AIRPORT.getAnswer().answer;
    private static final JsonAdaptedChoice VALID_CHOICE_1 = new JsonAdaptedChoice("Xiongan Airport");
    private static final JsonAdaptedChoice VALID_CHOICE_2 = new JsonAdaptedChoice("Daxing Airport");
    private static final JsonAdaptedChoice VALID_CHOICE_3 = new JsonAdaptedChoice("Lixian Airport");
    private static final JsonAdaptedChoice VALID_CHOICE_4 = new JsonAdaptedChoice("Zhuque Airport");
    private static final JsonAdaptedTag VALID_TAG_1 = new JsonAdaptedTag("Civil Engineering");
    private static final JsonAdaptedTag VALID_TAG_2 = new JsonAdaptedTag("Geography");
    private static final String VALID_SCORE = "1 2";
    private static final String VALID_TYPE = "ShortAnswerFlashcard";
    private static final String VALID_TYPE_2 = "McqFlashcard";

    private static final List<JsonAdaptedChoice> INVALID_CHOICE_LIST =
        new ArrayList<JsonAdaptedChoice>(Arrays.asList(VALID_CHOICE_1, VALID_CHOICE_3, INVALID_CHOICE));
    private static final List<JsonAdaptedChoice> VALID_CHOICE_LIST =
        new ArrayList<JsonAdaptedChoice>(Arrays.asList(VALID_CHOICE_1, VALID_CHOICE_2, VALID_CHOICE_3, VALID_CHOICE_4));
    private static final List<JsonAdaptedTag> INVALID_TAG_LIST =
        new ArrayList<JsonAdaptedTag>(Arrays.asList(VALID_TAG_1, INVALID_TAG, VALID_TAG_2));
    private static final List<JsonAdaptedTag> VALID_TAG_LIST =
        new ArrayList<JsonAdaptedTag>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(DAXING_AIRPORT);
        assertEquals(DAXING_AIRPORT, flashcard.toModelType());
    }

    @Test
    public void toModelType_validFlashcardDetails_successOne() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            null, VALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE);
        Flashcard myCard = flashcard.toModelType();
    }

    @Test
    public void toModelType_validFlashcardDetails_successTwo() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            VALID_CHOICE_LIST, VALID_DEFINITION, VALID_TAG_LIST, VALID_ANSWER, VALID_SCORE, VALID_TYPE_2);
        Flashcard myCard = flashcard.toModelType();
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(INVALID_QUESTION,
            null, VALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidDefinition_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            null, INVALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            null, VALID_DEFINITION, null, INVALID_ANSWER, VALID_SCORE, VALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null,
            null, VALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullDefinition_throwsIllegalValueDefinition() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            null, null, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            null, VALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, INVALID_TYPE);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidChoice_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            INVALID_CHOICE_LIST, VALID_DEFINITION, null, VALID_ANSWER, VALID_SCORE, VALID_TYPE_2);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_QUESTION,
            VALID_CHOICE_LIST, VALID_DEFINITION, INVALID_TAG_LIST, VALID_ANSWER, VALID_SCORE, VALID_TYPE_2);
        assertThrows(IllegalValueException.class, flashcard::toModelType);
    }
}
