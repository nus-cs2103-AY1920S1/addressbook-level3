package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.GoToCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;



class GoToParserTest {

    private GoToParser goToParser = new GoToParser();

    @Test
    void parse() throws ParseException {
        String watchlist = "watchlist";
        GoToCommand goToCommand = new GoToCommand("1");
        String watched = "watched";
        GoToCommand goToCommand2 = new GoToCommand("2");
        String search = "search";
        GoToCommand goToCommand3 = new GoToCommand("3");
        String statistics = "statistics";
        GoToCommand goToCommand4 = new GoToCommand("4");

        assertTrue(goToCommand.equals(goToParser.parse(watchlist, "1")));
        assertTrue(goToCommand2.equals(goToParser.parse(watched, "1")));
        assertTrue(goToCommand3.equals(goToParser.parse(search, "1")));
        assertTrue(goToCommand4.equals(goToParser.parse(statistics, "1")));
        assertThrows(ParseException.class, () -> goToParser.parse("123", "1"));
    }

    @Test
    void goToEqualCurrentPanel() throws ParseException {
        assertTrue(goToParser.goToEqualCurrentPanel("watchlist", "watch-list"));
        assertTrue(goToParser.goToEqualCurrentPanel("watched", "watched-list"));
        assertTrue(goToParser.goToEqualCurrentPanel("search", "search-list"));
        assertTrue(goToParser.goToEqualCurrentPanel("statistics", "statistics tab"));
    }
}
