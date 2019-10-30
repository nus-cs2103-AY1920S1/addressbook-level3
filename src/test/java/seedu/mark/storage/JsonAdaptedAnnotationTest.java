package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.testutil.Assert;

class JsonAdaptedAnnotationTest {

    @Test
    public void toModelType_invalidHighlight_throwsIllegalValueException() {
        JsonAdaptedAnnotation jan = new JsonAdaptedAnnotation("invalid colour", "a note");
        Assert.assertThrows(IllegalValueException.class, Highlight.MESSAGE_INVALID_COLOUR, jan::toModelType);
        jan = new JsonAdaptedAnnotation("invalid colour", null);
        Assert.assertThrows(IllegalValueException.class, Highlight.MESSAGE_INVALID_COLOUR, jan::toModelType);
    }

    @Test
    public void toModelType_validHighlightAnyNote_returnsAnnotation() throws Exception {
        JsonAdaptedAnnotation jan = new JsonAdaptedAnnotation("yellow", null);
        assertEquals(new Annotation(Highlight.YELLOW), jan.toModelType());

        jan = new JsonAdaptedAnnotation("gReEn", "  ");
        assertEquals(new Annotation(Highlight.GREEN), jan.toModelType());

        jan = new JsonAdaptedAnnotation("PINK", "this is a legit note");
        assertEquals(new Annotation(Highlight.PINK, AnnotationNote.makeNote("this is a legit note")),
                jan.toModelType());
    }

    @Test
    public void toModelType_nullHighlightAnyNote_returnsNull() throws Exception {
        JsonAdaptedAnnotation jan = new JsonAdaptedAnnotation(null, null);
        assertEquals(null, jan.toModelType());
        jan = new JsonAdaptedAnnotation(null, "legit note");
        assertEquals(null, jan.toModelType());
    }

}
