// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_TAG_FORMAT;
import static tagline.logic.commands.NoteCommandTestUtil.INVALID_TAG;
import static tagline.logic.commands.NoteCommandTestUtil.INVALID_TITLE;
import static tagline.logic.parser.note.NoteParserUtil.ERROR_INVALID_INDEX;
import static tagline.logic.parser.tag.TagParserUtil.TAG_USAGE;
import static tagline.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.logic.parser.exceptions.ParseException;
import tagline.model.note.Title;

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

    @Test
    public void parseTitle_invalidTitle_throwsParseException() {
        assertThrows(ParseException.class, Title.MESSAGE_CONSTRAINTS, ()
            -> NoteParserUtil.parseTitle(INVALID_TITLE));
    }

    @Test
    public void parseTags_invalidTags_throwsParseException() {
        List<String> invalidTags = Arrays.asList(INVALID_TAG);

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_TAG_FORMAT, TAG_USAGE), ()
            -> NoteParserUtil.parseTags(invalidTags));
    }
}
