package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;

class AnnotationNoteTest {

    @Test
    public void makeNote_validNoteContent_success() {
        try {
            AnnotationNote an = AnnotationNote.makeNote("legal note content");
            assertEquals(an, AnnotationNote.makeNote("legal note content"));
            assertEquals(an.toString(), "legal note content");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void makeNote_invalidNoteContent_throwsException() {
        assertThrows(NullPointerException.class, () -> AnnotationNote.makeNote(null));
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote(""), AnnotationNote.MESSAGE_BLANK_NOTE);
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote("   "),
                AnnotationNote.MESSAGE_BLANK_NOTE);
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote(" \n  "),
                AnnotationNote.MESSAGE_BLANK_NOTE);
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote(" \r  "),
                AnnotationNote.MESSAGE_BLANK_NOTE);
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote(" \t  "),
                AnnotationNote.MESSAGE_BLANK_NOTE);
    }

}
