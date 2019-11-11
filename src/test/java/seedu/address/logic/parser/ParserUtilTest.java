package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDYPLAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.UserTag;

public class ParserUtilTest {
    private static final String INVALID_MODULE_CODE = "CS2";
    private static final String INVALID_SEMESTER = "y1s";
    private static final String INVALID_TAG_1 = "CS3244";
    private static final String INVALID_TAG_2 = "s p a c e s";
    private static final String INVALID_TAG_3 = "y1s2";

    private static final String VALID_MODULE_CODE = "CS1101S";
    private static final String VALID_SEMESTER = "y1st1";
    private static final String VALID_TAG_1 = "super-cool";
    private static final String VALID_TAG_2 = "S/U-able";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_STUDYPLAN, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_STUDYPLAN, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModule(null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSemester(null));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsModuleString() throws Exception {
        assertEquals(VALID_MODULE_CODE, ParserUtil.parseModule(VALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_MODULE_CODE + WHITESPACE;
        assertEquals(VALID_MODULE_CODE, ParserUtil.parseModule(nameWithWhitespace));
    }


    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG_2));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        UserTag expectedTag = new UserTag(VALID_TAG_1);
        assertEquals(expectedTag.getTagName(), ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        UserTag expectedTag = new UserTag(VALID_TAG_1);
        assertEquals(expectedTag.getTagName(), ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG_2)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagList() throws Exception {
        List<String> actualTagList = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        List<String> expectedTagList = new ArrayList<String>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

        assertEquals(expectedTagList, actualTagList);
    }
}
