package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.logic.commands.SyncCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;



class SyncCommandParserTest {

    private SyncCommandParser syncCommandParser = new SyncCommandParser();

    @Test
    void parse() throws ParseException, OnlineConnectionException {
        assertThrows(ParseException.class, () -> syncCommandParser.parse("INVALID", "search"));
        assertEquals(syncCommandParser.parse("1", "search"), new SyncCommand(INDEX_FIRST_SHOW));
    }
}
