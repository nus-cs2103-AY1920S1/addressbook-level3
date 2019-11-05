package mams.logic.parser;

import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import mams.logic.commands.HistoryCommand;
import mams.logic.history.HistoryFilterSettings;

public class HistoryCommandParserTest {

    private HistoryCommandParser parser = new HistoryCommandParser();

    @Test
    public void parse_noParameters_returnsHistoryCommand() {
        assertParseSuccess(parser, " ", new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL));
    }

    @Test
    public void parse_hideOutput_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + HistoryCommandParser.OPTION_HIDE_OUTPUT.toString(),
                new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL));
    }

    @Test
    public void parse_hideUnsuccessfulCommands_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + HistoryCommandParser.OPTION_HIDE_UNSUCCESSFUL.toString(),
                new HistoryCommand(false, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL));
    }

}
