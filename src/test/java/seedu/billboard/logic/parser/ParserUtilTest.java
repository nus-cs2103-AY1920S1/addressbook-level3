package seedu.billboard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_AMOUNT = "test";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DESC = "eating at macs";
    private static final String VALID_AMOUNT = "2";

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
        assertEquals(INDEX_FIRST_EXPENSE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EXPENSE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseAmt_validValueWithoutWhitespace_returnsAmt() throws Exception {
        Amount expectedAmt = new Amount(VALID_AMOUNT);
        assertEquals(expectedAmt, ParserUtil.parseAmount(VALID_AMOUNT));
    }

    @Test
    public void parseAmt_validValueWithWhitespace_returnsAmt() throws Exception {
        String amtWithWhitespace = WHITESPACE + VALID_AMOUNT + WHITESPACE;
        Amount expectedAmt = new Amount(amtWithWhitespace.trim());
        assertEquals(expectedAmt, ParserUtil.parseAmount(amtWithWhitespace));
    }

    @Test
    public void parseAmt_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount((String) null));
    }

    @Test
    public void parseAmt_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT));
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
    public void parseDesc_validValueWithoutWhitespace_returnsDesc() throws Exception {
        Description expectedDesc = new Description(VALID_DESC);
        assertEquals(expectedDesc, ParserUtil.parseDescription(VALID_DESC));
    }

    @Test
    public void parseDesc_validValueWithWhitespace_returnsTrimmedDesc() throws Exception {
        String descWithWhitespace = WHITESPACE + VALID_DESC + WHITESPACE;
        Description expectedDesc = new Description(descWithWhitespace.trim());
        assertEquals(expectedDesc, ParserUtil.parseDescription(descWithWhitespace));
    }

    @Test
    public void parseDesc_validWhitespace_returnsDesc() throws Exception {
        String descOfWhitespace = WHITESPACE;
        Description expectedDesc = new Description(WHITESPACE.trim());
        assertEquals(expectedDesc, ParserUtil.parseDescription(descOfWhitespace));
    }

    @Test
    public void parseDesc_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
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
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
