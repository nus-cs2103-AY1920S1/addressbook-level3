package seedu.moolah.logic.parser.budget;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.DeleteExpenseFromBudgetCommand;

public class DeleteExpenseFromBudgetCommandParserTest {
    private DeleteExpenseFromBudgetCommandParser parser = new DeleteExpenseFromBudgetCommandParser();

    @Test
    void parse_blankArgument_parseException() {
        assertParseFailure(
                parser,
                "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseFromBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullArgument_nullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "x",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseFromBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteExpenseFromBudgetCommand() {
        assertParseSuccess(parser, "1", new DeleteExpenseFromBudgetCommand(INDEX_FIRST));
    }

    @Test
    public void parse_validArgsWithLeadingWhitespace_returnsDeleteExpenseFromBudgetCommand() {
        assertParseSuccess(parser, "    1", new DeleteExpenseFromBudgetCommand(INDEX_FIRST));
    }

    @Test
    public void parse_validArgsWithTrailingWhitespace_returnsDeleteExpenseFromBudgetCommand() {
        assertParseSuccess(parser, "1    ", new DeleteExpenseFromBudgetCommand(INDEX_FIRST));
    }
}
