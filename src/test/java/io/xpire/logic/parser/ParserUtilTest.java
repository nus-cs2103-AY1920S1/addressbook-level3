package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_EXPIRY_DATE_RANGE;
import static io.xpire.testutil.TypicalItemsFields.INVALID_NAME;
import static io.xpire.testutil.TypicalItemsFields.INVALID_QUANTITY_INTEGER;
import static io.xpire.testutil.TypicalItemsFields.INVALID_REMINDER_THRESHOLD;
import static io.xpire.testutil.TypicalItemsFields.INVALID_TAG;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
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
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

public class ParserUtilTest {

    private static final String WHITESPACE = "       ";
    private static final String VALID_EXPIRY_DATE_1 = "2/9/2050";
    private static final String VALID_EXPIRY_DATE_2 = "02/09/2050";
    private static final String VALID_METHOD_OF_SORTING_NAME = "name";
    private static final String VALID_METHOD_OF_SORTING_DATE = "date";
    private static final String INVALID_METHOD_OF_SORTING = "random";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
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
        Name expectedName = new Name(VALID_NAME_FISH);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_FISH));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME_FISH + WHITESPACE;
        Name expectedName = new Name(VALID_NAME_FISH);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseExpiryDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseExpiryDate(null));
    }

    @Test
    public void parseExpiryDate_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(INVALID_EXPIRY_DATE));
    }

    @Test
    public void parseExpiryDate_invalidRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(INVALID_EXPIRY_DATE_RANGE));
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
        Tag expectedTag = new Tag(VALID_TAG_DRINK);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_DRINK));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_DRINK + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_DRINK);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_DRINK, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_DRINK, VALID_TAG_FRUIT));
        Set<Tag> expectedTagSet = new TreeSet<>(new TagComparator());
        expectedTagSet.addAll(Arrays.asList(new Tag(VALID_TAG_DRINK), new Tag(VALID_TAG_FRUIT)));
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseExpiryDate_validDate_returnsExpiryDate() throws Exception {
        ExpiryDate validDate = new ExpiryDate(VALID_EXPIRY_DATE_FISH);
        assertEquals(validDate, ParserUtil.parseExpiryDate(VALID_EXPIRY_DATE_FISH));
    }

    @Test
    public void parseExpiryDate_validDateWithWhiteSpace_returnsExpiryDate() throws Exception {
        ExpiryDate validDate = new ExpiryDate(VALID_EXPIRY_DATE_FISH);
        assertEquals(validDate, ParserUtil.parseExpiryDate(WHITESPACE + VALID_EXPIRY_DATE_FISH + WHITESPACE));
    }

    //Note: I think need to catch DateTimeParseException somehow? If not here always got warning.
    @Test
    public void parseExpiryDate_emptyDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(WHITESPACE));
    }

    @Test
    public void parseExpiryDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseExpiryDate(INVALID_EXPIRY_DATE));
    }

    @Test
    public void parseQuantity_validQuantity_returnsQuantity() throws Exception {
        Quantity validQuantity = new Quantity(VALID_QUANTITY_FISH);
        assertEquals(validQuantity, ParserUtil.parseQuantity(VALID_QUANTITY_FISH));
    }

    @Test
    public void parseQuantity_invalidQuantity_returnsQuantity() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(INVALID_QUANTITY_INTEGER));
    }

    @Test
    public void parseReminderThreshold_validThreshold_returnsReminderThreshold() throws Exception {
        ReminderThreshold validThreshold = new ReminderThreshold(VALID_REMINDER_THRESHOLD_FISH);
        assertEquals(validThreshold, ParserUtil.parseReminderThreshold(VALID_REMINDER_THRESHOLD_FISH));
    }

    @Test
    public void parseReminderThreshold_invalidThreshold_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReminderThreshold(INVALID_REMINDER_THRESHOLD));
    }

    @Test
    public void parseTagsFromInput_validTags_returnsSet() throws Exception {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_DRINK));
        set.add(new Tag(VALID_TAG_FRUIT));
        assertEquals(set, ParserUtil.parseTagsFromInput("#" + VALID_TAG_FRUIT + "#" + VALID_TAG_DRINK));
    }

    @Test
    public void parseTagsFromInput_invalidTags_returnsSet() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTagsFromInput("#"));
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
        XpireMethodOfSorting expectedXpireMethodOfSorting = new XpireMethodOfSorting(VALID_METHOD_OF_SORTING_NAME);
        assertEquals(expectedXpireMethodOfSorting, ParserUtil.parseMethodOfSorting(VALID_METHOD_OF_SORTING_NAME));
    }

    @Test
    public void parseMethodOfSorting_validValueWithWhitespace_returnsTrimmedMethodOfSorting() throws Exception {
        String methodOfSortingWithWhitespace = WHITESPACE + VALID_METHOD_OF_SORTING_DATE + WHITESPACE;
        XpireMethodOfSorting expectedXpireMethodOfSorting = new XpireMethodOfSorting(VALID_METHOD_OF_SORTING_DATE);
        assertEquals(expectedXpireMethodOfSorting, ParserUtil.parseMethodOfSorting(methodOfSortingWithWhitespace));
    }
}
