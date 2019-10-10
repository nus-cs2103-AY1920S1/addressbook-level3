package seedu.jarvis.logic.parser.history;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.history.UndoCommand;

/**
 * Tests the behaviour of {@code UndoCommandParser}.
 */
public class UndoCommandParserTest {
    private UndoCommandParser parser = new UndoCommandParser();

    /**
     * Tests that if there are no arguments, a {@code UndoCommand} to undo a single command is created.
     */
    @Test
    public void parse_noArguments_success() {
        assertParseSuccess(parser, "", new UndoCommand());
    }

    /**
     * Tests that if given a valid integer argument, the correct {@code UndoCommand} with the correct number of actions
     * to undo is created.
     */
    @Test
    public void parse_withValidArguments_success() {
        IntStream.range(1, 100).forEach(index -> assertParseSuccess(parser, " " + PREFIX_UNDO_REDO + index,
                new UndoCommand(index)));
    }

    /**
     * Tests that if given an invalid argument, which is any number < 1, any floating point number or any non-numeric
     * argument, then the parser will throw a {@code ParserException} with the correct message.
     */
    @Test
    public void parse_withInvalidArguments_failure() {
        assertParseFailure(parser, " " + PREFIX_UNDO_REDO + "-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_UNDO_REDO + "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UndoCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_UNDO_REDO + "Html is a programming language",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
