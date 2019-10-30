package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.parser.exceptions.ParseException;

class HighlightTest {

    @Test
    public void toString_checkLowerCase() {
        assertEquals("yellow", Highlight.YELLOW.toString());
        assertEquals("pink", Highlight.PINK.toString());
    }

    @Test
    public void strToHighlight_validColour_returnsHighlight() throws Exception {
        assertEquals(Highlight.GREEN, Highlight.strToHighlight("grEeN"));
        assertEquals(Highlight.ORANGE, Highlight.strToHighlight("ORANGE"));
    }

    @Test
    public void strToHighlight_invalidColour_throwsParseException() {
        assertThrows(ParseException.class, Highlight.MESSAGE_INVALID_COLOUR, () ->
                Highlight.strToHighlight(""));
        assertThrows(ParseException.class, Highlight.MESSAGE_INVALID_COLOUR, () ->
                Highlight.strToHighlight("grey"));
    }

    @Test
    public void strToHighlight_nullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Highlight.strToHighlight(null));
    }

}
