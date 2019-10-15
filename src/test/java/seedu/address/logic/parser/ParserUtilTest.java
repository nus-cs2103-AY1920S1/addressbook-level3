package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DIARY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.DiaryName;

public class ParserUtilTest {
    private static final String INVALID_DIARY_NAME = "R@ndom name";

    private static final String VALID_DIARY_NAME = "Test Diary";

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
        assertEquals(INDEX_FIRST_DIARY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_DIARY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseDiaryName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseDiaryName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_DIARY_NAME));
    }

    @Test
    public void parseDiaryName_validValueWithoutWhitespace_returnsName() throws Exception {
        DiaryName expectedName = new DiaryName(VALID_DIARY_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_DIARY_NAME));
    }

    @Test
    public void parseDiaryName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_DIARY_NAME + WHITESPACE;
        DiaryName expectedName = new DiaryName(VALID_DIARY_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }
}
