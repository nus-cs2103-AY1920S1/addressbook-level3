package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_IN;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_OUT;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_PAID;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_PERSON;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.loancommands.LoanListCommand;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanListCommandParser;

public class LoanListCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE);

    private LoanListCommandParser parser = new LoanListCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid keyword
        String userInput = "test";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // duplicate keywords
        userInput = String.join(" ", KEYWORD_LOAN_IN, KEYWORD_LOAN_IN);
        assertParseFailure(parser, userInput, LoanListCommandParser.MESSAGE_DUPLICATE_FILTERS);

        // more than 2 keywords
        userInput = String.join(" ", KEYWORD_LOAN_IN, KEYWORD_LOAN_OUT, KEYWORD_LOAN_PAID);
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleSortArgs_failure() {
        String userInput = PREFIX_SORT + SORT_ARG_AMOUNT + " " + PREFIX_SORT + SORT_ARG_PERSON;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidSortArgs_failure() {
        String userInput = PREFIX_SORT + "z";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
