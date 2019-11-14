package seedu.address.logic.parser.historycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.historycommand.UndoCommand;

class UndoCommandParserTest {
    private UndoCommandParser ucp = new UndoCommandParser();

    @Test
    public void parse_validArgsEmptyString_returnsUndoCommand() {
        assertParseSuccess(ucp, "", new UndoCommand(1));
    }

    @Test
    public void parse_validArgsInteger_returnsUndoCommand() {
        //Tests the boundary values of the valid regex
        assertParseSuccess(ucp, "1", new UndoCommand(1));
        assertParseSuccess(ucp, "49", new UndoCommand(49));
    }

    @Test
    public void parse_invalidArgsIntegers_throwsParseException() {
        //Tests for large integers past the boundary values of the valid regex
        assertParseFailure(ucp, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(ucp, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(ucp, "50",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(ucp, "999999999999",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsInvalidStrings_throwsParseException() {
        assertParseFailure(ucp, "null",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(ucp, "!@#$%^&*(",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
