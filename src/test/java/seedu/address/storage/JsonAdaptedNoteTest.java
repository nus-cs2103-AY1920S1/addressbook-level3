package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedNote.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Content;
import seedu.address.model.note.Title;

public class JsonAdaptedNoteTest {
    private static final String INVALID_TITLE = "a\nb";
    private static final String INVALID_CONTENT = " ";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_CONTENT = BENSON.getContent().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedNote person = new JsonAdaptedNote(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedNote person =
                new JsonAdaptedNote(INVALID_TITLE, VALID_CONTENT);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedNote person = new JsonAdaptedNote(null, VALID_CONTENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedNote person =
                new JsonAdaptedNote(VALID_TITLE, INVALID_CONTENT);
        String expectedMessage = Content.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedNote person = new JsonAdaptedNote(VALID_TITLE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
