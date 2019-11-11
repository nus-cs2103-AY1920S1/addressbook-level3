package seedu.weme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.parser.util.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.model.ModelContext.CONTEXT_VIEW;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.parser.contextparser.MemeParser;
import seedu.weme.logic.parser.contextparser.ViewParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.meme.Description;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

public class ParserUtilTest extends ApplicationTest {
    private static final String INVALID_TAB = "NOT A TAB";
    private static final String INVALID_FILEPATH = "Hello world";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_DESCRIPTION = "Sit vitae voluptas sint non voluptates";

    private static final String VALID_FILEPATH = "src/test/data/memes/charmander_meme.jpg";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void forContext_success() {
        assertEquals(MemeParser.class, ParserUtil.forContext(CONTEXT_MEMES).getClass());
        assertEquals(ViewParser.class, ParserUtil.forContext(CONTEXT_VIEW).getClass());
    }

    @Test
    public void parseTab_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTab(INVALID_TAB));
    }

    @Test
    public void parseTab_validInput_success() throws Exception {
        String contextName = CONTEXT_MEMES.getContextName();

        // No whitespaces
        assertEquals(CONTEXT_MEMES, ParserUtil.parseTab(contextName));

        // With whitespaces
        assertEquals(CONTEXT_MEMES, ParserUtil.parseTab(" " + contextName + " "));
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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFilePath((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFilePath(INVALID_FILEPATH));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        ImagePath expectedName = new ImagePath(VALID_FILEPATH);
        assertEquals(expectedName, ParserUtil.parseFilePath(VALID_FILEPATH));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String urlWithWhitespace = WHITESPACE + VALID_FILEPATH + WHITESPACE;
        ImagePath expectedName = new ImagePath(VALID_FILEPATH);
        assertEquals(expectedName, ParserUtil.parseFilePath(urlWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
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
