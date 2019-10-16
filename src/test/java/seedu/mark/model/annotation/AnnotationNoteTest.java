package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;

class AnnotationNoteTest {

    @Test
    public void makeNote_success() {
        try {
            AnnotationNote an = AnnotationNote.makeNote("legal note content");
            assertEquals(new AnnotationNote("legal note content"), an);
            assertEquals(an.toString(), "legal note content");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void makeNote_failure_throwsException() {
        assertThrows(NullPointerException.class, () -> AnnotationNote.makeNote(null));
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote(""), AnnotationNote.MESSAGE_BLANK_NOTE);
        assertThrows(IllegalValueException.class, () -> AnnotationNote.makeNote("   "),
                AnnotationNote.MESSAGE_BLANK_NOTE);
    }

}
