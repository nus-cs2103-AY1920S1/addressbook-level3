package seedu.address.logic.parser.historycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.historycommand.RedoCommand;

class RedoCommandParserTest {
    private RedoCommandParser rcp = new RedoCommandParser();

    @Test
    public void parse_validArgsEmptyString_returnsRedoCommand() {
        assertParseSuccess(rcp, "", new RedoCommand(1));
    }

    @Test
    public void parse_validArgsInteger_returnsRedoCommand() {
        //Tests the boundary values of the valid regex
        assertParseSuccess(rcp, "1", new RedoCommand(1));
        assertParseSuccess(rcp, "49", new RedoCommand(49));
    }

    @Test
    public void parse_invalidArgsIntegers_throwsParseException() {
        //Tests for large integers past the boundary values of the valid regex
        assertParseFailure(rcp, "-1",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertParseFailure(rcp, "0",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertParseFailure(rcp, "50",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertParseFailure(rcp, "999999999999",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsInvalidStrings_throwsParseException() {
        assertParseFailure(rcp, "null",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertParseFailure(rcp, "!@#$%^&*(",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}
