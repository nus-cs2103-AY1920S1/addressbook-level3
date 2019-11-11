package seedu.jarvis.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.jarvis.logic.parser.ParserUtil.parseTaskDes;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

public class ParserUtilTest {
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseNonZeroUnsignedInteger_invalidInputNotNumber_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger("InvalidInput"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger("Invalid Input"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger(" Invalid Input "));
    }

    @Test
    public void parseNonZeroUnsignedInteger_invalidInputNegativeNumber_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger(" -1 "));
    }

    @Test
    public void parseNonZeroUnsignedInteger_validInputWhiteSpace_throwsParseException() throws Exception {
        assertEquals(1, ParserUtil.parseNonZeroUnsignedInteger(""));
        assertEquals(5, ParserUtil.parseNonZeroUnsignedInteger("5"));
        assertEquals(10, ParserUtil.parseNonZeroUnsignedInteger(" 10 "));
    }

    @Test
    public void parseNonZeroUnsignedInteger_outOfRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger(
                Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseNonZeroUnsignedInteger_invalidInputZero_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonZeroUnsignedInteger("0"));
    }

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
    void parsePriority_validInput_success() throws Exception {
        Priority actual = ParserUtil.parsePriority("high");
        Priority expected = Priority.HIGH;

        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void parsePriority_invalidInput_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority("highest"));
    }

    @Test
    void parseFrequency_validInput_success() throws Exception {
        Frequency actual = ParserUtil.parseFrequency("weekly");
        Frequency expected = Frequency.WEEKLY;
        assertEquals(0, expected.compareTo(actual));
    }

    @Test
    void parseFrequency_invalidInput_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFrequency("every week"));
    }

    @Test
    void parseDate_validInput_success() throws Exception {
        LocalDate[] actual = ParserUtil.parseDate("18/10/2019");
        LocalDate expected = LocalDate.parse("18/10/2019", Task.getDateFormat());
        assertEquals(0, expected.compareTo(actual[0]));
    }

    @Test
    void parseDate_invalidInput_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("hello there"));
    }

    @Test
    void buildTask_validInput_success() throws Exception {
        Task expected = new Todo("borrow book");
        LocalDate[] dates = new LocalDate[2];
        Task actual = ParserUtil.buildTask("todo", "borrow book", dates);
        assertTrue(expected.equals(actual));
    }

    @Test
    void buildTask_invalidInput_throwsException() {
        LocalDate[] dates = new LocalDate[2];
        assertThrows(ParseException.class, () -> ParserUtil.buildTask("task", "borrow book",
                dates));
    }

    @Test
    void parseTaskDes_validInput_success() throws ParseException {
        String expected = "taskDes";
        String actual = parseTaskDes("  taskDes ");

        assertEquals(expected, actual);

    }

    @Test
    void parseTaskDes_invalidInput_throwsException() {
        String taskDes = "  ";
        assertThrows(ParseException.class, () -> parseTaskDes(taskDes));
    }
}
