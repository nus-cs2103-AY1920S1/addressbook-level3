package seedu.eatme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_FILE_NO_PREFIX_JOHN;
import static seedu.eatme.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_CATEGORY = "0fish";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_REVIEW_DESCRIPTION = " ";
    private static final double INVALID_REVIEW_COST_1 = -2;
    private static final double INVALID_REVIEW_COST_2 = 10001;
    private static final int INVALID_REVIEW_RATING = 6;
    private static final double INVALID_REVIEW_RATING_2 = 2.3;

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_CATEGORY = "Chinese";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_REVIEW_DESCRIPTION = "GOOD";
    private static final double VALID_REVIEW_COST = 3.2;
    private static final int VALID_REVIEW_RATING = 4;

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
        assertEquals(INDEX_FIRST_EATERY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EATERY, ParserUtil.parseIndex("  1  "));
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
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory((String) null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseReviewDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseReviewDescription((String) null));
    }

    @Test
    public void parseReviewDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReviewDescription(INVALID_REVIEW_DESCRIPTION));
    }

    @Test
    public void parseReviewDescription_validValueWithoutWhiteSpace_returnsReviewDescription() throws ParseException {
        String expectedReviewDescription = VALID_REVIEW_DESCRIPTION;
        assertEquals(expectedReviewDescription, ParserUtil.parseReviewDescription(VALID_REVIEW_DESCRIPTION));
    }

    @Test
    public void parseReviewDescription_validValueWithWhitespace_returnsTrimmedDescription() throws ParseException {
        String reviewDescriptionWithWhitespace = WHITESPACE + VALID_REVIEW_DESCRIPTION + WHITESPACE;
        String expectedReviewDescription = VALID_REVIEW_DESCRIPTION;
        assertEquals(expectedReviewDescription, ParserUtil.parseReviewDescription(reviewDescriptionWithWhitespace));
    }

    @Test
    public void parseReviewCost_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseReviewCost((String) null));
    }

    @Test
    public void parseReviewCost_invalidValue1_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReviewCost(String.valueOf(INVALID_REVIEW_COST_1)));
    }

    @Test
    public void parseReviewCost_invalidValue2_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReviewCost(String.valueOf(INVALID_REVIEW_COST_2)));
    }

    @Test
    public void parseReviewCost_validValueWithoutWhiteSpace_returnsReviewCost() throws ParseException {
        double expectedReviewCost = VALID_REVIEW_COST;
        assertEquals(expectedReviewCost, ParserUtil.parseReviewCost(String.valueOf(VALID_REVIEW_COST)));
    }

    @Test
    public void parseReviewCost_validValueWithWhitespace_returnsTrimmedCost() throws ParseException {
        String reviewCostWithWhitespace = WHITESPACE + VALID_REVIEW_COST + WHITESPACE;
        double expectedReviewCost = VALID_REVIEW_COST;
        assertEquals(expectedReviewCost, ParserUtil.parseReviewCost(reviewCostWithWhitespace));
    }

    @Test
    public void parseReviewRating_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseReviewRating((String) null));
    }

    @Test
    public void parseReviewRating_invalidValue1_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReviewRating(String.valueOf(INVALID_REVIEW_RATING)));
    }

    @Test
    public void parseReviewEating_invalidValue2_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseReviewRating(String.valueOf(INVALID_REVIEW_RATING_2)));
    }

    @Test
    public void parseReviewRating_validValueWithoutWhiteSpace_returnsReviewRating() throws ParseException {
        double expectedReviewRating = VALID_REVIEW_RATING;
        assertEquals(expectedReviewRating, ParserUtil.parseReviewRating(String.valueOf(VALID_REVIEW_RATING)));
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
    public void parseFile_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFile(null));
    }

    @Test
    public void parseFile_withValidName_returnsUnchanged() {
        assertEquals(VALID_FILE_NO_PREFIX_JOHN, ParserUtil.parseFile(VALID_FILE_NO_PREFIX_JOHN));
    }

    @Test
    public void parseFile_withInvalidName_returnsFormatted() {
        assertEquals(VALID_FILE_NO_PREFIX_JOHN, ParserUtil.parseFile("john"));
    }
}
