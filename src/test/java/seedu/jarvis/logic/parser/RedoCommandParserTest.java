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
import seedu.jarvis.logic.commands.RedoCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;

/**
 * Tests RedoCommandParser's behaviour on parsing arguments.
 */
public class RedoCommandParserTest {

    private Parser<RedoCommand> parser = new RedoCommandParser();

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
        assertParseSuccess(parser, arguments, new RedoCommand());
    }

    /**
     * Verifies that parsing all as argument returns an undo command to undo all available actions.
     */
    @Test
    public void test_parse_all() {
        String arguments1 = " " + PREFIX_UNDO_REDO + "all";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        System.out.println(VersionControl.INSTANCE.getTotalNumberOfRedoableCommands());
        assertParseSuccess(parser, arguments1, new RedoCommand(
                VersionControl.INSTANCE.getTotalNumberOfRedoableCommands()));

        String arguments2 = " " + PREFIX_UNDO_REDO + "ALL";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertParseSuccess(parser, arguments2, new RedoCommand(
                VersionControl.INSTANCE.getTotalNumberOfRedoableCommands()));

        String arguments3 = " " + PREFIX_UNDO_REDO + "aLl";
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(new InvertibleCommandStub()));
        assertParseSuccess(parser, arguments3, new RedoCommand(
                VersionControl.INSTANCE.getTotalNumberOfRedoableCommands()));

    }

    /**
     * Verifies that parsing a valid numeric value would return an redo command to undo that many actions.
     */
    @Test
    public void test_parse_validNumeric() {
        String validNumber = "5";
        String arguments = " " + PREFIX_UNDO_REDO + validNumber;
        assertParseSuccess(parser, arguments, new RedoCommand(Integer.parseInt(validNumber)));
    }

    /**
     * Verifies that parsing a negative number as argument returns an redo command of 1 action.
     */
    @Test
    public void test_parse_negativeNumeric() {
        String negativeNumber = "-5";
        String arguments = " " + PREFIX_UNDO_REDO + negativeNumber;
        assertParseSuccess(parser, arguments, new RedoCommand());
    }

    /**
     * Verifies that parser throws exception if the argument is invalid.
     */
    @Test
    public void test_parse_invalid() {
        Stream.of("1.1", " ", "INVALID", "?", "Html Is A Programming Language")
                .map(invalidArgument -> " " + PREFIX_UNDO_REDO + invalidArgument)
                .forEach(argument -> assertParseFailure(parser, argument,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE)));
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
