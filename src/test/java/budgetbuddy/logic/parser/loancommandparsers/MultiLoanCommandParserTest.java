package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_PERSON;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanPaidCommand;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanPaidCommandParser;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.MultiLoanCommandParser;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class MultiLoanCommandParserTest {

    private MultiLoanCommandParser parser = new LoanPaidCommandParser();

    @Test
    public void parseMultiLoanArgs_validArgs_success() throws CommandException {
        assertParseSuccess(parser, "1 2 3",
                new LoanPaidCommand(
                        List.of(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_SECOND_ITEM,
                                TypicalIndexes.INDEX_THIRD_ITEM),
                        new ArrayList<>()));
        assertParseSuccess(parser, String.join(" ", "1", JOHN_PERSON),
                new LoanPaidCommand(
                        List.of(TypicalIndexes.INDEX_FIRST_ITEM),
                        List.of(TypicalLoans.JOHN_OUT_UNPAID.getPerson())));
    }

    @Test
    public void parseMultiLoanArgs_invalidArgs_failure() {
        assertParseFailure(parser, "1 2 a", MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, String.join(" ", "1", INVALID_INPUT_PERSON),
                Name.MESSAGE_CONSTRAINTS);
    }
}
