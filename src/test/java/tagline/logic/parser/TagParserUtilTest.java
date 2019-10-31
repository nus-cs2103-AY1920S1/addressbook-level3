package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.tag.TagParserUtil;
import tagline.model.contact.ContactId;
import tagline.model.tag.ContactTag;
import tagline.model.tag.Tag;

public class TagParserUtilTest {
    private static final String INVALID_TAG = "!12345";
    private static final String VALID_TAG_1 = "@12345";
    private static final String VALID_TAG_2 = "@67890";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TagParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> TagParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new ContactTag(new ContactId(VALID_TAG_1.substring(1)));
        assertTrue(expectedTag.equals(TagParserUtil.parseTag(VALID_TAG_1)));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new ContactTag(new ContactId(VALID_TAG_1.substring(1)));
        assertTrue(expectedTag.equals(TagParserUtil.parseTag(tagWithWhitespace)));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() {
    }
}
