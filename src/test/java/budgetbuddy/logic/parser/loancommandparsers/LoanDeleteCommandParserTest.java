package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;
import static budgetbuddy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanDeleteCommand;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanDeleteCommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.testutil.TypicalIndexes;

public class LoanDeleteCommandParserTest {

    private LoanDeleteCommandParser parser = new LoanDeleteCommandParser();

    @Test
    public void parse_validArgs_success() throws CommandException {
        assertParseSuccess(parser, "1",
                new LoanDeleteCommand(List.of(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<>()));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);
        assertThrows(ParseException.class, () -> parser.parse("a"));
    }
}
