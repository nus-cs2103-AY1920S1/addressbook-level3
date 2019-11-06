package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.ExitCommand;
import thrift.logic.commands.HelpCommand;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.parser.exceptions.ParseException;

public class NoArgumentsCommandParserTest {
    @Test
    public void parse_parseExitCommandWithoutArguments_success() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("exit",
                ExitCommand.MESSAGE_USAGE);
        assertTrue(noArgumentsCommandParser.parse("") instanceof ExitCommand);
    }

    @Test
    public void parse_parseExitCommandWithArguments_failure() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("exit",
                ExitCommand.MESSAGE_USAGE);
        try {
            noArgumentsCommandParser.parse("abc");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE), e.getMessage());
        }
    }

    @Test
    public void parse_redoCommandWithoutArguments_success() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("redo",
                RedoCommand.MESSAGE_USAGE);
        assertTrue(noArgumentsCommandParser.parse("") instanceof RedoCommand);
    }

    @Test
    public void parse_parseRedoCommandWithArguments_failure() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("redo",
                RedoCommand.MESSAGE_USAGE);
        try {
            noArgumentsCommandParser.parse("abc");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE), e.getMessage());
        }
    }

    @Test
    public void parse_undoCommandWithoutArguments_success() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("undo",
                UndoCommand.MESSAGE_USAGE);
        assertTrue(noArgumentsCommandParser.parse("") instanceof UndoCommand);
    }

    @Test
    public void parse_parseUndoCommandWithArguments_failure() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("undo",
                UndoCommand.MESSAGE_USAGE);
        try {
            noArgumentsCommandParser.parse("abc");
        } catch (ParseException e) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE), e.getMessage());
        }
    }

    @Test
    public void parse_parseInvalidCommandWithoutArguments_failure() throws ParseException {
        NoArgumentsCommandParser noArgumentsCommandParser = new NoArgumentsCommandParser("help",
                HelpCommand.MESSAGE_USAGE);
        try {
            noArgumentsCommandParser.parse("");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }

}
