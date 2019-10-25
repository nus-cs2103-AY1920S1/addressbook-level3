package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.testutil.Assert;

class JsonAdaptedAnnotationTest {

    @Test
    public void toModelType_invalidHighlight_throwsIllegalValueException() {
        JsonAdaptedAnnotation jan = new JsonAdaptedAnnotation("invalid colour", "a note");
        Assert.assertThrows(IllegalValueException.class, Highlight.MESSAGE_INVALID_COLOUR, jan::toModelType);
    }

    @Test
    public void toModelType_validHighlightAnyNote_returnsAnnotation() {
        //note null
        //note empty
        //legit note
        //highlight just test one
    }

    @Test
    public void toModelType_nullHighlightAnyNote_returnsNull() {

    }

}
