package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COLOR_STRING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECURRENCE_TYPE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCHEDULE_VIEW_MODE;
import static seedu.address.commons.util.EventUtil.BAD_DATE_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {

    private static final String INVALID_TAG = "#invalid";
    private static final String VALID_STUDENT_NAME = "Valid Name";
    private static final String INVALID_STUDENT_NAME = "@Invalid Name";
    private static final String INVALID_DATE_TIME = "@Invalid DateTime";
    private static final String INVALID_RECURRENCE_RULE = "@Invalid Rule";
    private static final String INVALID_EVENT_SCHEDULE_VIEW_MODE = "@Invalid Mode";

    private static final String VALID_TAG_1 = "TestTag";
    private static final String VALID_TAG_2 = "TestTagTwo";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseEventColor_invalidColorMaxBound_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_COLOR_STRING, () -> ParserUtil.parseColorNumber("24"));
    }

    @Test
    public void parseEventColor_invalidColorMinBound_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_COLOR_STRING, () -> ParserUtil.parseColorNumber("-1"));
    }

    @Test
    public void parseEventDateTime_invalidDate_throwsParseException() {
        assertThrows(ParseException.class,
                BAD_DATE_FORMAT, () -> ParserUtil.parseLocalDateTime(INVALID_DATE_TIME));
    }

    @Test
    public void parseEventDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocalDateTime((String) null));
    }

    @Test
    public void parseEventRecurrenceRule_invalidRule_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_RECURRENCE_TYPE, () -> ParserUtil.parseRecurrenceType(INVALID_RECURRENCE_RULE));
    }

    @Test
    public void parseEventRecurrenceRule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRecurrenceType((String) null));
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_DATE, () -> ParserUtil.parseLocalDate(INVALID_DATE_TIME));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocalDate((String) null));
    }

    @Test
    public void parseEventScheduleViewMode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_SCHEDULE_VIEW_MODE, () -> ParserUtil
                        .parseEventScheduleViewMode(INVALID_EVENT_SCHEDULE_VIEW_MODE));
    }

    @Test
    public void parseEventScheduleViewMode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventScheduleViewMode((String) null));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(Index.fromOneBased(1), ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(Index.fromOneBased(1), ParserUtil.parseIndex("  1  "));
    }


    @Test
    public void parseStudentName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudentName((String) null));
    }

    @Test
    public void parseStudentName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudentName(INVALID_STUDENT_NAME));
    }

    @Test
    public void parseStudentName_validValueWithoutWhitespace_returnsStudentName() throws Exception {
        Name expectedName = new Name(VALID_STUDENT_NAME);
        assertEquals(expectedName, ParserUtil.parseStudentName(VALID_STUDENT_NAME));
    }

    @Test
    public void parseStudentName_validValueWithWhitespace_returnsTrimmedStudentName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_STUDENT_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_STUDENT_NAME);
        assertEquals(expectedName, ParserUtil.parseStudentName(nameWithWhitespace));
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
}
