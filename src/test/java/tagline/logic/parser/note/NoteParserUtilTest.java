// @@author shiweing
package tagline.logic.parser.note;

import static tagline.logic.parser.note.NoteParserUtil.MESSAGE_INVALID_INDEX;
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
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> NoteParserUtil.parseIndex(String.valueOf(Long.MAX_VALUE + 1)));
    }
}
