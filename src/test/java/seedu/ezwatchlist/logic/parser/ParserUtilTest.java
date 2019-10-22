package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Date;

public class ParserUtilTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_IS_WATCHED = "";
    private static final String INVALID_DATE = "";
    private static final int INVALID_RUNNING_TIME = -1;
    private static final String INVALID_ACTORS = "";

    private static final String VALID_NAME = "Titanic";
    private static final String VALID_DESCRIPTION = "Ship";
    private static final boolean VALID_IS_WATCHED = false;
    private static final String VALID_DATE = "24 September 1997";
    private static final int VALID_RUNNING_TIME = 122;
    private static final String VALID_ACTORS_1 = "Leonardo Di Caprio";
    private static final String VALID_ACTORS_2 = "Kate Winslet";
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
        assertEquals(INDEX_FIRST_SHOW, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SHOW, ParserUtil.parseIndex("  1  "));
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
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

}
