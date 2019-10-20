package seedu.moneygowhere.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.tag.Tag;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = " ";
    private static final String INVALID_COST = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_DATE_1 = "21/1/2019";
    private static final String VALID_DATE_2 = "23/1/2019";
    private static final String VALID_COST = "123";
    private static final String VALID_REMARK = "Likes to watch movies";
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
        assertEquals(INDEX_FIRST_SPENDING, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SPENDING, ParserUtil.parseIndex("  1  "));
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
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE_1);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE_1));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE_1 + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE_1);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseDates_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDates(null));
    }

    @Test
    public void parseDates_collectionWithInvalidDates_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDates(Arrays.asList(VALID_DATE_1, INVALID_DATE)));
    }

    @Test
    public void parseDates_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseDates(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseDates_collectionWithValidDates_returnsDateSet() throws Exception {
        List<Date> actualDateList = ParserUtil.parseDates(Arrays.asList(VALID_DATE_1, VALID_DATE_2));
        List<Date> expectedDateList = new ArrayList<>(Arrays.asList(new Date(VALID_DATE_1),
            new Date(VALID_DATE_2)));

        assertEquals(expectedDateList, actualDateList);
    }

    @Test
    public void parseCost_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCost((String) null));
    }

    @Test
    public void parseCost_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCost(INVALID_COST));
    }

    @Test
    public void parseCost_validValueWithoutWhitespace_returnsCost() throws Exception {
        Cost expectedCost = new Cost(VALID_COST);
        assertEquals(expectedCost, ParserUtil.parseCost(VALID_COST));
    }

    @Test
    public void parseCost_validValueWithWhitespace_returnsTrimmedCost() throws Exception {
        String costWithWhitespace = WHITESPACE + VALID_COST + WHITESPACE;
        Cost expectedCost = new Cost(VALID_COST);
        assertEquals(expectedCost, ParserUtil.parseCost(costWithWhitespace));
    }

    @Test
    public void parseCosts_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCosts(null));
    }

    @Test
    public void parseCosts_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCosts(Collections.singletonList(INVALID_COST)));
        assertThrows(ParseException.class, () -> ParserUtil.parseCosts(Collections.singletonList("123.000-123.00")));
    }

    @Test
    public void parseCosts_validValueWithoutWhitespace_returnsCost() throws Exception {
        Cost expectedCost = new Cost(VALID_COST);
        List<Cost> costs = ParserUtil.parseCosts(Collections.singletonList(VALID_COST));
        assertEquals(1, costs.size());
        assertEquals(expectedCost, costs.get(0));
    }

    @Test
    public void parseCosts_validValueWithWhitespace_returnsTrimmedCost() throws Exception {
        String costWithWhitespace = WHITESPACE + VALID_COST + WHITESPACE;
        Cost expectedCost = new Cost(VALID_COST);

        List<Cost> costs = ParserUtil.parseCosts(Collections.singletonList(costWithWhitespace));
        assertEquals(1, costs.size());
        assertEquals(expectedCost, costs.get(0));
    }

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace));
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
