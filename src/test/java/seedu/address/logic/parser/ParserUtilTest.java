package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Picture;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PICTURE = "234.jpgg";
    private static final String INVALID_CLASSID = " ";
    private static final String INVALID_ATTENDANCE = "example";
    private static final String INVALID_RESULT = "excellent";
    private static final String INVALID_PARTICIPATION = "outspoken";

    private static final String VALID_NAME = "Rachel";
    private static final String VALID_PICTURE = "234.jpg";
    private static final String VALID_CLASSID = "Tut1";
    private static final String VALID_ATTENDANCE = "3";
    private static final String VALID_RESULT = "33";
    private static final String VALID_PARTICIPATION = "2";


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
    public void parsePicture_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePicture((String) null));
    }

    @Test
    public void parsePicture_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePicture(INVALID_PICTURE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Picture expectedPicture = new Picture(VALID_PICTURE);
        assertEquals(expectedPicture, ParserUtil.parsePicture(VALID_PICTURE));
    }

    @Test
    public void parsePicture_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String pictureWithWhitespace = WHITESPACE + VALID_PICTURE + WHITESPACE;
        Picture expectedPicture = new Picture(VALID_PICTURE);
        assertEquals(expectedPicture, ParserUtil.parsePicture(pictureWithWhitespace));
    }

}
