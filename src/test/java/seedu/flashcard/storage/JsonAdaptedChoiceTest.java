package seedu.flashcard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Choice;

public class JsonAdaptedChoiceTest {

    private static final String VALID_CHOICE = "Team 14 will get A+.";
    private static final String INVALID_CHOICE = " ";

    @Test
    public void toModelType_validChoice_returnsChoice() throws Exception {
        JsonAdaptedChoice choice = new JsonAdaptedChoice(VALID_CHOICE);
        assertEquals(new Choice(VALID_CHOICE), choice.toModelType());
    }

    @Test
    public void toModelType_invalidChoice_throwsIllegalValueException() {
        JsonAdaptedChoice choice = new JsonAdaptedChoice(INVALID_CHOICE);
        assertThrows(IllegalValueException.class, choice::toModelType);
    }
}
