// @@author chrischenhui
package seedu.address.storage.wordbanks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.wordbanks.JsonAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;

class JsonAdaptedCardTest {
    private static final String INVALID_WORD = "  "; // Empty spaces
    private static final String INVALID_MEANING = "   "; // Empty spaces
    private static final String INVALID_TAG = "#teg";

    private static final String VALID_ID = ABRA.getId();
    private static final String VALID_WORD = ABRA.getWord().toString();
    private static final String VALID_MEANING = ABRA.getMeaning().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = ABRA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    void toModelType_validCardDetails_returnsCard() throws Exception {
        JsonAdaptedCard card = new JsonAdaptedCard(ABRA);
        assertEquals(ABRA, card.toModelType());
    }

    @Test
    void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedCard card =
                new JsonAdaptedCard(null, VALID_WORD, VALID_MEANING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    void toModelType_invalidWord_throwsIllegalValueException() {
        JsonAdaptedCard card =
                new JsonAdaptedCard(VALID_ID, INVALID_WORD, VALID_MEANING, VALID_TAGS);
        String expectedMessage = Word.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCard card =
                new JsonAdaptedCard(VALID_ID, null, VALID_MEANING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Word.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    void toModelType_invalidMeaning_throwsIllegalValueException() {
        JsonAdaptedCard person =
                new JsonAdaptedCard(VALID_ID, VALID_WORD, INVALID_MEANING, VALID_TAGS);
        String expectedMessage = Meaning.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    void toModelType_nullMeaning_throwsIllegalValueException() {
        JsonAdaptedCard person =
                new JsonAdaptedCard(VALID_ID, VALID_WORD, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Meaning.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCard person =
                new JsonAdaptedCard(VALID_ID, VALID_WORD, VALID_MEANING, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
