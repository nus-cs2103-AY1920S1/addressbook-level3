package mams.logic.parser;

import static mams.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static mams.testutil.Assert.assertThrows;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import mams.logic.parser.exceptions.ParseException;
import mams.model.student.Credits;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.PrevMods;
import mams.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CREDITS = "20";
    private static final String INVALID_MATRICID = " ";
    private static final String INVALID_PREVMODS = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_CREDITS = "20";
    private static final String VALID_MATRICID = "A0169982H";
    private static final String VALID_PREVMODS = "CS2030, CS1231";
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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
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
    public void parseCredits_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCredits((String) null));
    }


    @Test
    public void parseCredits_validValueWithoutWhitespace_returnsCredits() throws Exception {
        Credits expectedCredits = new Credits(VALID_CREDITS);
        assertEquals(expectedCredits, ParserUtil.parseCredits(VALID_CREDITS));
    }

    @Test
    public void parseCredits_validValueWithWhitespace_returnsTrimmedCredits() throws Exception {
        String creditsWithWhitespace = WHITESPACE + VALID_CREDITS + WHITESPACE;
        Credits expectedCredits = new Credits(VALID_CREDITS);
        assertEquals(expectedCredits, ParserUtil.parseCredits(creditsWithWhitespace));
    }

    @Test
    public void parseMatricId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMatricId((String) null));
    }

    @Test
    public void parseMatricId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMatricId(INVALID_MATRICID));
    }

    @Test
    public void parseMatricId_validValueWithoutWhitespace_returnsAddress() throws Exception {
        MatricId expectedMatricId = new MatricId(VALID_MATRICID);
        assertEquals(expectedMatricId, ParserUtil.parseMatricId(VALID_MATRICID));
    }

    @Test
    public void parseMatricId_validValueWithWhitespace_returnsTrimmedMatricId() throws Exception {
        String matricIdWithWhitespace = WHITESPACE + VALID_MATRICID + WHITESPACE;
        MatricId expectedMatricId = new MatricId(VALID_MATRICID);
        assertEquals(expectedMatricId, ParserUtil.parseMatricId(matricIdWithWhitespace));
    }

    @Test
    public void parsePrevMods_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrevMods((String) null));
    }

    @Test
    public void parsePrevMods_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrevMods(INVALID_PREVMODS));
    }

    @Test
    public void parsePrevMods_validValueWithoutWhitespace_returnsPrevMods() throws Exception {
        PrevMods expectedPrevMods = new PrevMods(VALID_PREVMODS);
        assertEquals(expectedPrevMods, ParserUtil.parsePrevMods(VALID_PREVMODS));
    }

    @Test
    public void parsePrevMods_validValueWithWhitespace_returnsTrimmedPrevMods() throws Exception {
        String prevModsWithWhitespace = WHITESPACE + VALID_PREVMODS + WHITESPACE;
        PrevMods expectedPrevMods = new PrevMods(VALID_PREVMODS);
        assertEquals(expectedPrevMods, ParserUtil.parsePrevMods(prevModsWithWhitespace));
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
