package seedu.address.logic.calendar.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.calendar.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;


public class ParserUtilTest {
    private static final String INVALID_DAY = "tuessday";
    private static final String INVALID_TASKTIME = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TASKTITLE = "Rachel Walker";
    private static final String VALID_DAY = "tuesday";
    private static final String VALID_TASKTIME = "12:00";
    private static final String VALID_DESCRIPTION = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTitle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
    }


    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsName() throws Exception {
        TaskTitle expectedName = new TaskTitle(VALID_TASKTITLE);
        assertEquals(expectedName, ParserUtil.parseTitle(VALID_TASKTITLE));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_TASKTITLE + WHITESPACE;
        TaskTitle expectedName = new TaskTitle(VALID_TASKTITLE);
        assertEquals(expectedName, ParserUtil.parseTitle(nameWithWhitespace));
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay((String) null));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay(INVALID_DAY));
    }

    @Test
    public void parseDay_validValueWithoutWhitespace_returnsPhone() throws Exception {
        TaskDay expectedPhone = new TaskDay(VALID_DAY);
        assertEquals(expectedPhone, ParserUtil.parseDay(VALID_DAY));
    }

    @Test
    public void parseDay_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_DAY + WHITESPACE;
        TaskDay expectedPhone = new TaskDay(VALID_DAY);
        assertEquals(expectedPhone, ParserUtil.parseDay(phoneWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TASKTIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsAddress() throws Exception {
        TaskTime expectedAddress = new TaskTime(VALID_TASKTIME);
        assertEquals(expectedAddress, ParserUtil.parseTime(VALID_TASKTIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_TASKTIME + WHITESPACE;
        TaskTime expectedAddress = new TaskTime(VALID_TASKTIME);
        assertEquals(expectedAddress, ParserUtil.parseTime(addressWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsEmail() throws Exception {
        TaskDescription expectedEmail = new TaskDescription(VALID_DESCRIPTION);
        assertEquals(expectedEmail, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        TaskDescription expectedEmail = new TaskDescription(VALID_DESCRIPTION);
        assertEquals(expectedEmail, ParserUtil.parseDescription(emailWithWhitespace));
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
        TaskTag expectedTag = new TaskTag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        TaskTag expectedTag = new TaskTag(VALID_TAG_1);
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
        Set<TaskTag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<TaskTag> expectedTagSet =
            new HashSet<>(Arrays.asList(new TaskTag(VALID_TAG_1), new TaskTag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
