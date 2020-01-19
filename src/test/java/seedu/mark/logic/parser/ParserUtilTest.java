package seedu.mark.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.mark.logic.parser.ParserUtil.NoArgumentParser;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.AddAnnotationCommand;
import seedu.mark.logic.commands.Command;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;
import seedu.mark.storage.Storage;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_URL = "exam?ple.com?";
    private static final String INVALID_REMARK = "t/ means tag";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_URL = "https://rachel-example.com";
    private static final String VALID_REMARK = "123 Main Street #0505";
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
        assertEquals(INDEX_FIRST_BOOKMARK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_BOOKMARK, ParserUtil.parseIndex("  1  "));
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
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
    }

    @Test
    public void parseRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRemark(INVALID_REMARK));
    }

    @Test
    public void parseRemark_validValueEmpty_returnsDefaultRemark() throws Exception {
        Remark expectedRemark = new Remark(Remark.DEFAULT_VALUE);
        assertEquals(expectedRemark, ParserUtil.parseRemark(WHITESPACE));
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
    public void parseUrl_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUrl((String) null));
    }

    @Test
    public void parseUrl_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUrl(INVALID_URL));
    }

    @Test
    public void parseUrl_validValueWithoutWhitespace_returnsUrl() throws Exception {
        Url expectedUrl = new Url(VALID_URL);
        assertEquals(expectedUrl, ParserUtil.parseUrl(VALID_URL));
    }

    @Test
    public void parseUrl_validValueWithWhitespace_returnsTrimmedUrl() throws Exception {
        String urlWithWhitespace = WHITESPACE + VALID_URL + WHITESPACE;
        Url expectedUrl = new Url(VALID_URL);
        assertEquals(expectedUrl, ParserUtil.parseUrl(urlWithWhitespace));
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
    public void parseParagraphId_validId_returnsParagraphIdentifier() throws Exception {
        Index index = Index.fromOneBased(1);
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST),
                ParserUtil.parseParagraphIdentifier("p1"));
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST),
                ParserUtil.parseParagraphIdentifier("P1"));
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.STRAY),
                ParserUtil.parseParagraphIdentifier("g1"));
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.STRAY),
                ParserUtil.parseParagraphIdentifier("G1"));
    }

    @Test
    public void parseParagraphId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseParagraphIdentifier(null));
    }

    @Test
    public void parseParagraphId_invalidId_throwsParseException() {
        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier(" "));
        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier(""));

        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier("ello"));
        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier("1p"));

        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier("ps2"));
        assertThrows(ParseException.class,
                AddAnnotationCommand.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseParagraphIdentifier("p2s"));
    }

    @Test
    public void parseNoArgumentCommand_noArgument_returnsCommand() throws ParseException {
        assertNotNull(new NoArgumentParser<>(CommandStub::new).parse(""));
    }

    @Test
    public void parseNoArgumentCommand_withWhitespace_returnsCommand() throws ParseException {
        assertNotNull(new NoArgumentParser<>(CommandStub::new).parse(" \t\n"));
    }

    @Test
    public void parseNoArgumentCommand_nonEmpty_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> new NoArgumentParser<>(CommandStub::new).parse(" not empty"));
    }

    private static class CommandStub extends Command {

        @Override
        public CommandResult execute(Model model, Storage storage) {
            throw new AssertionError("This constructor should not be called.");
        }
    }
}
