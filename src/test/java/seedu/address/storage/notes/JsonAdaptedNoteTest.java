package seedu.address.storage.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.note.JsonAdaptedNote.ILLEGAL_FIELD_MESSAGE;
import static seedu.address.storage.note.JsonAdaptedNote.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Note;
import seedu.address.storage.note.JsonAdaptedNote;
import seedu.address.testutil.note.NoteBuilder;

public class JsonAdaptedNoteTest {

    private static final String INVALID_NOTE = "";
    private static final String INVALID_DESC = "";
    private static final String INVALID_PRIORITY = "hi gh";

    private static final String VALID_NOTE = "note title";
    private static final String VALID_DESC = "note desc";
    private static final String VALID_PRIORITY = "high";

    private static final Note NOTE = new NoteBuilder().withNote(VALID_NOTE).withDesc(VALID_DESC).build();

    @Test
    public void toModelType_validNoteDetails_returnsNote() throws Exception {
        JsonAdaptedNote note = new JsonAdaptedNote(NOTE);
        assertEquals(NOTE, note.toModelType());
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(INVALID_NOTE, VALID_DESC, VALID_PRIORITY);
        String expectedMessage = MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(null, VALID_DESC, VALID_PRIORITY);
        String expectedMessage = MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(VALID_NOTE, INVALID_DESC, VALID_PRIORITY);
        String expectedMessage = MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(VALID_NOTE, null, VALID_PRIORITY);
        String expectedMessage = MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(VALID_NOTE, VALID_DESC, INVALID_PRIORITY);
        String expectedMessage = ILLEGAL_FIELD_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedNote note = new JsonAdaptedNote(VALID_NOTE, VALID_DESC, null);
        String expectedMessage = MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }
}
