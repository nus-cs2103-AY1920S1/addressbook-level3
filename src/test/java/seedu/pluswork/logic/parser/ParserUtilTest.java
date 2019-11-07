package seedu.pluswork.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_CLOCK_FORMAT = "#twelve";
    private static final String INVALID_THEME = "li@ght";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_CLOCK_FORMAT = "twEnty_four";
    private static final String VALID_THEME = "daRk";

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
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseClock_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClock(null));
    }

    @Test
    public void parseClock_validClockFormatWithWhiteSpace_success() throws ParseException {
        String clockWhiteSpace = WHITESPACE + VALID_CLOCK_FORMAT;
        assertEquals(ClockFormat.TWENTY_FOUR, ParserUtil.parseClock(clockWhiteSpace));
    }

    @Test
    public void parseClock_invalidClockFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClock(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseClock(INVALID_CLOCK_FORMAT));
    }

    @Test
    public void parseTheme_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTheme(null));
    }

    @Test
    public void parseTheme_validThemeWithWhiteSpace_success() throws ParseException {
        String themeWhiteSpace = WHITESPACE + VALID_THEME;
        assertEquals(Theme.DARK, ParserUtil.parseTheme(themeWhiteSpace));
    }

    @Test
    public void parseTheme_invalidTheme_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTheme(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseTheme(INVALID_THEME));
    }
}
