// @@author shiweing
package tagline.logic.parser.note;

import static tagline.logic.parser.note.NoteParserUtil.ERROR_INVALID_INDEX;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tagline.logic.parser.exceptions.ParseException;

class NoteParserUtilTest {

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> NoteParserUtil.parseIndex("a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        String outOfRangeInput = String.valueOf(Long.MAX_VALUE + 1);
        assertThrows(ParseException.class, String.format(ERROR_INVALID_INDEX, outOfRangeInput), ()
            -> NoteParserUtil.parseIndex(outOfRangeInput));
    }
}
