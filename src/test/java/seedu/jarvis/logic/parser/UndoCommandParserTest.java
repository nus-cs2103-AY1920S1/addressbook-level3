package seedu.jarvis.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.UndoCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;

/**
 * Tests UndoCommandParser's behaviour on parsing arguments.
 */
public class UndoCommandParserTest {

    private Parser<UndoCommand> parser = new UndoCommandParser();

    /**
     * Resets version control before each test.
     */
    @BeforeEach
    public void resetBefore() {
        VersionControl.INSTANCE.hardReset();
    }

    /**
     * Resets version control after each test.
     */
    @AfterEach
    public void resetAfter() {
        VersionControl.INSTANCE.hardReset();
    }

    /**
     * Verifies that parsing no arguments returns an undo command of 1 action.
     */
    @Test
    public void test_parse_noArguments() {
        String arguments = "";
        assertParseSuccess(parser, arguments, new UndoCommand());
    }

    /**
     * Verifies that parsing all as argument returns an undo command to undo all available actions.
     */
    @Test
    public void test_parse_all() {
        String arguments1 = " " + PREFIX_UNDO_REDO + "all";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertParseSuccess(parser, arguments1, new UndoCommand(
                VersionControl.INSTANCE.getTotalNumberOfUndoableCommands()));

        String arguments2 = " " + PREFIX_UNDO_REDO + "ALL";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertParseSuccess(parser, arguments2, new UndoCommand(
                VersionControl.INSTANCE.getTotalNumberOfUndoableCommands()));

        String arguments3 = " " + PREFIX_UNDO_REDO + "aLl";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(new InvertibleCommandStub()));
        assertParseSuccess(parser, arguments3, new UndoCommand(
                VersionControl.INSTANCE.getTotalNumberOfUndoableCommands()));

    }

    /**
     * Verifies that parsing a valid numeric value would return an undo command to undo that many actions.
     */
    @Test
    public void test_parse_validNumeric() {
        String validNumber = "5";
        String arguments = " " + PREFIX_UNDO_REDO + validNumber;
        assertParseSuccess(parser, arguments, new UndoCommand(Integer.parseInt(validNumber)));
    }

    /**
     * Verifies that parsing a negative number as argument returns an undo command of 1 action.
     */
    @Test
    public void test_parse_negativeNumeric() {
        String negativeNumber = "-5";
        String arguments = " " + PREFIX_UNDO_REDO + negativeNumber;
        assertParseSuccess(parser, arguments, new UndoCommand());
    }

    /**
     * Verifies that parsing zero as argument returns an undo command of 1 action.
     */
    @Test
    public void test_parse_zero() {
        String arguments = " " + PREFIX_UNDO_REDO + "0";
        assertParseSuccess(parser, arguments, new UndoCommand());
    }

    /**
     * Verifies that parser throws exception if the argument is invalid.
     */
    @Test
    public void test_parse_invalid() {
        Stream.of("1.1", " ", "INVALID", "?", "Html Is A Programming Language")
                .map(invalidArgument -> " " + PREFIX_UNDO_REDO + invalidArgument)
                .forEach(argument -> assertParseFailure(parser, argument,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE)));
    }

    /**
     * A stub command class to store in {@code VersionControl}.
     */
    private static class InvertibleCommandStub extends Command {
        @Override
        public boolean hasInverseExecution() {
            return true;
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            return null;
        }

        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            return null;
        }
    }
}
