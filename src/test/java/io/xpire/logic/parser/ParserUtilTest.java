package io.xpire.logic.parser;

import static io.xpire.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.sort.MethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

public class ParserUtilTest {
    public static final String INVALID_NAME = "@pple";
    public static final String INVALID_EXPIRY_DATE_1 = "50505000";
    public static final String INVALID_EXPIRY_DATE_2 = "50/50/5000";
    public static final String INVALID_TAG = "$cold";
    public static final String INVALID_METHOD_OF_SORTING = "random";
    public static final String INVALID_QUANTITY = "-2";
    public static final String INVALID_REMINDER_THRESHOLD = "-5";

    private static final String VALID_NAME = "Strawberry";
    private static final String VALID_EXPIRY_DATE_1 = "2/12/2020";
    private static final String VALID_EXPIRY_DATE_2 = "02/12/2020";
    private static final String VALID_TAG_1 = "Friend";
    private static final String VALID_TAG_2 = "Neighbour";
    private static final String VALID_METHOD_OF_SORTING_NAME = "name";
    private static final String VALID_METHOD_OF_SORTING_DATE = "date";

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
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
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
    public void parseExpiryDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseExpiryDate(null));
    }

    @Test
    public void parseExpiryDate_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(INVALID_EXPIRY_DATE_1));
    }

    @Test
    public void parseExpiryDate_invalidRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(INVALID_EXPIRY_DATE_2));
    }

    @Test
    public void parseExpiryDate_validValueWithoutWhitespace_returnsExpiryDate() throws Exception {
        ExpiryDate expectedExpiryDate = new ExpiryDate(VALID_EXPIRY_DATE_1);
        assertEquals(expectedExpiryDate, ParserUtil.parseExpiryDate(VALID_EXPIRY_DATE_1));
    }

    @Test
    public void parseExpiryDate_validValueWithWhitespace_returnsTrimmedExpiryDate() throws Exception {
        String expiryDateWithWhitespace = WHITESPACE + VALID_EXPIRY_DATE_2 + WHITESPACE;
        ExpiryDate expectedExpiryDate = new ExpiryDate(VALID_EXPIRY_DATE_1);
        assertEquals(expectedExpiryDate, ParserUtil.parseExpiryDate(expiryDateWithWhitespace));
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
        Set<Tag> expectedTagSet = new TreeSet<>(new TagComparator());
        expectedTagSet.addAll(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMethodOfSorting_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMethodOfSorting(null));
    }

    @Test
    public void parseMethodOfSorting_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMethodOfSorting(INVALID_METHOD_OF_SORTING));
    }

    @Test
    public void parseMethodOfSorting_validValueWithoutWhitespace_returnsMethodOfSorting() throws Exception {
        MethodOfSorting expectedMethodOfSorting = new MethodOfSorting(VALID_METHOD_OF_SORTING_NAME);
        assertEquals(expectedMethodOfSorting, ParserUtil.parseMethodOfSorting(VALID_METHOD_OF_SORTING_NAME));
    }

    @Test
    public void parseMethodOfSorting_validValueWithWhitespace_returnsTrimmedMethodOfSorting() throws Exception {
        String methodOfSortingWithWhitespace = WHITESPACE + VALID_METHOD_OF_SORTING_DATE + WHITESPACE;
        MethodOfSorting expectedMethodOfSorting = new MethodOfSorting(VALID_METHOD_OF_SORTING_DATE);
        assertEquals(expectedMethodOfSorting, ParserUtil.parseMethodOfSorting(methodOfSortingWithWhitespace));
    }

}
