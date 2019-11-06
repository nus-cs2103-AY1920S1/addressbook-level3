package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static thrift.testutil.Assert.assertThrows;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Budget;
import thrift.model.transaction.Value;
import thrift.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_TAG = "#friend";
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
        assertEquals(TypicalIndexes.INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, Budget.DATE_CONSTRAINTS, () -> ParserUtil.parseDate("Feb2019"));
    }

    @Test
    public void parseValue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, Value.VALUE_CONSTRAINTS, ()
            -> ParserUtil.parseValue("A+"));
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
    public void parseCurrencies_invalidCurrencyFormat_throwsParseException() {
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_CURRENCY, ()
            -> ParserUtil.parseCurrencies(new ArrayList<String>() {
                {
                    add("Ka-Ching!");
                    add("$$$");
                }
            }));
    }

    @Test
    public void parseMonth_validMonth_success() throws ParseException {
        Month expectedMonth = Month.valueOf("JANUARY");
        assertEquals(expectedMonth, ParserUtil.parseMonth("Jan"));
    }

    @Test
    public void parseMonth_invalidMonthInput_throwsParseException() {
        // Invalid input month String
        assertThrows(ParseException.class, ListCommandParser.MESSAGE_INVALID_MONTH_FORMAT, ()
            -> ParserUtil.parseMonth("FirstMonth"));
    }

    @Test
    public void parseMonth_nullMonthInput_throwsNullPointerException() throws NullPointerException {
        // Null input month String
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMonth(null));
    }
}
