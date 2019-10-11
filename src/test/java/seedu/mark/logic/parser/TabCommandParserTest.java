package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;

class TabCommandParserTest {

    private TabCommandParser parser = new TabCommandParser();

    @Test
    public void parse_validArgs_returnsTabCommand() {
        TabCommand expected = new TabCommand(TabCommand.Tab.DASHBOARD);
        assertParseSuccess(parser, "1", expected);
        assertParseSuccess(parser, "dash", expected);

        expected = new TabCommand(TabCommand.Tab.ONLINE);
        assertParseSuccess(parser, "2", expected);
        assertParseSuccess(parser, "on", expected);

        expected = new TabCommand(TabCommand.Tab.OFFLINE);
        assertParseSuccess(parser, "3", expected);
        assertParseSuccess(parser, "off", expected);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "4", TabCommand.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, null, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
    }

}
