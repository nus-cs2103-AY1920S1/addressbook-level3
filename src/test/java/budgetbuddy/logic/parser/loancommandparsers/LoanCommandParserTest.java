package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT_CENTS;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT_ZERO;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_DATE;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_DESCRIPTION;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_PERSON;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_AMOUNT;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_DATE;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_DESCRIPTION;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_DIRECTION;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_PERSON;
import static budgetbuddy.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.loancommands.LoanCommand;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanCommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.testutil.loanutil.LoanBuilder;
import budgetbuddy.testutil.loanutil.TypicalLoans;

public class LoanCommandParserTest {

    private LoanCommandParser parser = new LoanCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Loan expectedLoan = TypicalLoans.JOHN_OUT_UNPAID;
        String input = String.join(" ",
                JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_DESCRIPTION, JOHN_DATE);
        assertParseSuccess(parser, input, new LoanCommand(expectedLoan));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no description
        Loan expectedLoan = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withDescription("").build();
        String input = String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_DATE);
        assertParseSuccess(parser, input, new LoanCommand(expectedLoan));

        // no date
        expectedLoan = new LoanBuilder(TypicalLoans.JOHN_OUT_UNPAID).withDate(LocalDate.now()).build();
        input = String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_DESCRIPTION);
        assertParseSuccess(parser, input, new LoanCommand(expectedLoan));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // no direction
        assertParseFailure(parser, String.join(" ", JOHN_AMOUNT, JOHN_PERSON), MESSAGE_UNKNOWN_COMMAND);

        // no person
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_AMOUNT),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));

        // no amount
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE);

        // extra direction
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT),
                MESSAGE_UNKNOWN_COMMAND);

        // extra person
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_PERSON, JOHN_AMOUNT),
                expectedMessage);

        // extra amount
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_AMOUNT),
               expectedMessage);

        // extra description
        assertParseFailure(parser,
                String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_DESCRIPTION, JOHN_DESCRIPTION),
                expectedMessage);

        // extra date
        assertParseFailure(parser,
                String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, JOHN_DATE, JOHN_DATE),
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid person
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, INVALID_INPUT_PERSON, JOHN_AMOUNT),
                Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON, INVALID_INPUT_AMOUNT),
                Amount.MESSAGE_CONSTRAINTS);

        // invalid amount of zero dollars
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON, INVALID_INPUT_AMOUNT_ZERO),
                Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);

        // invalid amount due to extra decimals
        assertParseFailure(parser, String.join(" ", JOHN_DIRECTION, JOHN_PERSON, INVALID_INPUT_AMOUNT_CENTS),
                Amount.MESSAGE_CENTS_PARSE_ERROR);

        // invalid description
        assertParseFailure(parser,
                String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, INVALID_INPUT_DESCRIPTION),
                Description.MESSAGE_CONSTRAINTS);

        // invalid date
        assertThrows(ParseException.class, () -> parser.parse(
                String.join(" ", JOHN_DIRECTION, JOHN_PERSON, JOHN_AMOUNT, INVALID_INPUT_DATE)));
    }
}
