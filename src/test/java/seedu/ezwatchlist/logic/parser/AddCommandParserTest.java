package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    void parse() throws ParseException {
        AddCommand addCommand = new AddCommand(1);
    }

    @Test
    void parse2() throws ParseException {
        String args = " 1";
        assertTrue(parser.parse(args, "search-list") instanceof AddCommand);

        String args1 = " n/";
        assertThrows(ParseException.class, () -> parser.parse(args1, "1"));

        String args2 = " n/test t/movie";
        assertTrue(parser.parse(args2, "1") instanceof AddCommand);

        String args3 = " n/test t/tv";
        assertTrue(parser.parse(args3, "1") instanceof AddCommand);

        String args5 = " n/test t/tv w/false";
        assertTrue(parser.parse(args5, "1") instanceof AddCommand);

        String args6 = " n/test t/tv s/desc";
        assertTrue(parser.parse(args6, "1") instanceof AddCommand);

        String args7 = " n/test t/tv r/122";
        assertTrue(parser.parse(args7, "1") instanceof AddCommand);

        String args8 = " n/test t/tv a/Tony";
        assertTrue(parser.parse(args8, "1") instanceof AddCommand);

        String args4 = " n/test t/tv d/24/09/1997";
        assertTrue(parser.parse(args4, "1") instanceof AddCommand);
    }

}
