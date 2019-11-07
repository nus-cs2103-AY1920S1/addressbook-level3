package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanPaidCommand;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanPaidCommandParser;
import budgetbuddy.testutil.TypicalIndexes;

public class LoanPaidCommandParserTest {

    private LoanPaidCommandParser parser = new LoanPaidCommandParser();

    @Test
    public void parse_validArgs_success() throws CommandException {
        assertParseSuccess(parser, "1",
                new LoanPaidCommand(List.of(TypicalIndexes.INDEX_FIRST_ITEM), new ArrayList<>()));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_INDEX);
    }
}
