package mams.logic.parser;

import static mams.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import mams.logic.commands.HistoryCommand;

public class HistoryCommandParserTest {

    private HistoryCommandParser parser = new HistoryCommandParser();

    @Test
    public void parse_noParameters_returnsHistoryCommand() {
        assertParseSuccess(parser, " ", new HistoryCommand());
    }

    @Test
    public void parse_hideOutput_returnsHistoryCommand() {
        assertParseSuccess(parser,
                " " + HistoryCommandParser.PREFIX_HIDE_OUTPUT.toString(),
                new HistoryCommand(true));
    }

}
