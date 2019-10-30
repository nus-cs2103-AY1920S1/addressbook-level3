package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;

public class AnnotationTest {

    @Test
    public void hasNote_success() {
        assertEquals(true, new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE).hasNote());
        assertEquals(false, new Annotation(Highlight.YELLOW).hasNote());
    }

    @Test
    public void createNew_nullHighlightAnyNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Annotation(null, AnnotationNote.SAMPLE_NOTE));
        assertThrows(NullPointerException.class, () -> new Annotation(null, null));
    }

    @Test
    public void createNew_nonNullHighlightInvalidNote_throwsException() {
        assertThrows(NullPointerException.class, () -> new Annotation(Highlight.YELLOW, null));
    }

    @Test
    public void createNew_anyHighlightInvalidNote_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new Annotation(null, AnnotationNote.makeNote("   ")));
        assertThrows(IllegalValueException.class, () -> new Annotation(Highlight.ORANGE, AnnotationNote.makeNote("")));
    }

    @Test
    public void equals_changeHighlightNote_success() throws Exception {
        Annotation an = new Annotation(Highlight.ORANGE, AnnotationNote.makeNote("note"));
        Annotation an2 = new Annotation(Highlight.PINK, AnnotationNote.makeNote("haha"));
        assertNotEquals(an, an2);
        an2.setNote(AnnotationNote.makeNote("note"));
        assertNotEquals(an, an2);
        an2.setHighlight(Highlight.ORANGE);
        assertEquals(an, an2);
    }

    @Test
    public void toString_checkCorrectFormat() {
        assertEquals("yellow highlight with note \"" + AnnotationNote.SAMPLE_NOTE.toString() + "\"",
                new Annotation(Highlight.YELLOW, AnnotationNote.SAMPLE_NOTE).toString());
    }

}
