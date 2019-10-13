package seedu.mark.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

class TabCommandParserTest {

    private TabCommandParser parser = new TabCommandParser();


    @Test
    public void parseTab_validArgs_success() throws Exception {
        TabCommand.Tab expected = TabCommand.Tab.ONLINE;
        assertEquals(expected, TabCommandParser.parseTabIndex("2"));
        assertEquals(expected, TabCommandParser.parseTabKeyword("on"));
        assertEquals(expected, TabCommandParser.parseTab("2"));
        assertEquals(expected, TabCommandParser.parseTab("on"));

        expected = TabCommand.Tab.OFFLINE;
        assertEquals(expected, TabCommandParser.parseTabIndex("3"));
        assertEquals(expected, TabCommandParser.parseTabKeyword("off"));
        assertEquals(expected, TabCommandParser.parseTab("3"));
        assertEquals(expected, TabCommandParser.parseTab("off"));

        expected = TabCommand.Tab.DASHBOARD;
        assertEquals(expected, TabCommandParser.parseTabIndex("1"));
        assertEquals(expected, TabCommandParser.parseTabKeyword("dash"));
        assertEquals(expected, TabCommandParser.parseTab("1"));
        assertEquals(expected, TabCommandParser.parseTab("dash"));
    }

    @Test
    public void parseTab_invalidIndex_throwsInvalidIndexParseException() {
        assertThrows(ParseException.class, TabCommand.MESSAGE_INVALID_INDEX, () -> TabCommandParser.parseTabIndex("5"));
        assertThrows(ParseException.class, TabCommand.MESSAGE_INVALID_INDEX, () -> TabCommandParser.parseTab("5"));
        assertThrows(ParseException.class, TabCommand.MESSAGE_INVALID_INDEX, () -> TabCommandParser.parseTab("-5"));
    }

    @Test
    public void parseTab_invalidArg_throwsInvalidFormatParseException() {
        String expectedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMsg, () -> TabCommandParser.parseTabIndex("invalid arg"));
        assertThrows(ParseException.class, expectedMsg, () -> TabCommandParser.parseTab("invalid arg"));
        assertThrows(ParseException.class, expectedMsg, () -> TabCommandParser.parseTab(""));
        assertThrows(ParseException.class, expectedMsg, () -> TabCommandParser.parseTab("0.5"));
    }

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
        assertParseFailure(parser, "blob", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "4", TabCommand.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "0", TabCommand.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "-1", TabCommand.MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
    }

}
