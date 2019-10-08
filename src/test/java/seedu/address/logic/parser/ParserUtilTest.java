package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Meaning;
import seedu.address.model.card.Word;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_WORD = " \n \t ";
    private static final String INVALID_MEANING = "    \n\n\n";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_WORD = "Bulbasaur";
    private static final String VALID_MEANING = "There is a plant seed on its back right from the day this Pokémon "
            + "is born. The seed slowly grows larger.";
    private static final String VALID_TAG_1 = "grass";
    private static final String VALID_TAG_2 = "water";

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
            assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

            // Leading and trailing whitespaces
            assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
        }

        @Test
        public void parseWord_null_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () -> ParserUtil.parseWord((String) null));
        }

        @Test
        public void parseWord_invalidValue_throwsParseException() {
            assertThrows(ParseException.class, () -> ParserUtil.parseWord(INVALID_WORD));
        }

        @Test
        public void parseWord_validValueWithoutWhitespace_returnsName() throws Exception {
            Word expectedWord = new Word(VALID_WORD);
            assertEquals(expectedWord, ParserUtil.parseWord(VALID_WORD));
        }

        @Test
        public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
            String nameWithWhitespace = WHITESPACE + VALID_WORD + WHITESPACE;
            Word expectedWord = new Word(VALID_WORD);
            assertEquals(expectedWord, ParserUtil.parseWord(nameWithWhitespace));
        }

        @Test
        public void parseMeaning_null_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () -> ParserUtil.parseMeaning((String) null));
        }

        @Test
        public void parseMeaning_invalidValue_throwsParseException() {
            assertThrows(ParseException.class, () -> ParserUtil.parseMeaning(INVALID_MEANING));
        }

        @Test
        public void parseMeaning_validValueWithoutWhitespace_returnsPhone() throws Exception {
            Meaning expectedMeaning = new Meaning(VALID_MEANING);
            assertEquals(expectedMeaning, ParserUtil.parseMeaning(VALID_MEANING));
        }

        @Test
        public void parseMeaning_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
            String phoneWithWhitespace = WHITESPACE + VALID_MEANING + WHITESPACE;
            Meaning expectedMeaning = new Meaning(VALID_MEANING);
            assertEquals(expectedMeaning, ParserUtil.parseMeaning(phoneWithWhitespace));
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
