package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.logic.parser.CommandParserUtil.MESSAGE_INVALID_INDEX;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT_CENTS;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_AMOUNT_ZERO;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_DATE;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_DESCRIPTION;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.INVALID_INPUT_PERSON;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_AMOUNT;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_DATE;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_DESCRIPTION;
import static budgetbuddy.logic.parser.loancommandparsers.TypicalLoanInput.JOHN_PERSON;
import static budgetbuddy.testutil.loanutil.TypicalLoans.JOHN_OUT_UNPAID;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand;
import budgetbuddy.logic.commands.loancommands.LoanEditCommand.LoanEditDescriptor;
import budgetbuddy.logic.parser.commandparsers.loancommandparsers.LoanEditCommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.loanutil.LoanEditDescriptorBuilder;

public class LoanEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanEditCommand.MESSAGE_USAGE);

    private LoanEditCommandParser parser = new LoanEditCommandParser();

    @Test
    public void parse_missingCompulsoryFields_failure() {
        // no field specified
        assertParseFailure(parser, "1", LoanEditCommand.MESSAGE_UNEDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-1 " + JOHN_PERSON, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0 " + JOHN_PERSON, MESSAGE_INVALID_INDEX);

        // extra arguments in preamble
        assertParseFailure(parser, "1 test " + JOHN_PERSON, MESSAGE_INVALID_FORMAT);

        // invalid prefix in preamble
        assertParseFailure(parser, "1 z/string " + JOHN_PERSON, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid person
        assertParseFailure(parser, "1 " + INVALID_INPUT_PERSON, Name.MESSAGE_CONSTRAINTS);
        // invalid amount
        assertParseFailure(parser, "1 " + INVALID_INPUT_AMOUNT, Amount.MESSAGE_CONSTRAINTS);
        // invalid amount (zero dollars)
        assertParseFailure(parser, "1 " + INVALID_INPUT_AMOUNT_ZERO, Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
        // invalid amount (extra decimals in cents)
        assertParseFailure(parser, "1 " + INVALID_INPUT_AMOUNT_CENTS, Amount.MESSAGE_CENTS_PARSE_ERROR);
        // invalid description
        assertParseFailure(parser, "1 " + INVALID_INPUT_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);
        // invalid date
        assertThrows(ParseException.class, () -> parser.parse("1 " + INVALID_INPUT_DATE));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                String.join(" ", "1", INVALID_INPUT_PERSON, INVALID_INPUT_AMOUNT,
                        INVALID_INPUT_DATE, INVALID_INPUT_DESCRIPTION),
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = String.join(" ", String.format("%d", targetIndex.getOneBased()),
                JOHN_PERSON, JOHN_AMOUNT, JOHN_DATE, JOHN_DESCRIPTION);

        LoanEditDescriptor descriptor = new LoanEditDescriptorBuilder()
                .withPerson(JOHN_OUT_UNPAID.getPerson().getName().toString())
                .withAmount(JOHN_OUT_UNPAID.getAmount().toLong())
                .withDescription(JOHN_OUT_UNPAID.getDescription().toString())
                .withDate(JOHN_OUT_UNPAID.getDate()).build();
        LoanEditCommand expectedCommand = new LoanEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = String.join(" ", String.format("%d", targetIndex.getOneBased()),
                JOHN_PERSON, JOHN_AMOUNT);

        LoanEditDescriptor descriptor = new LoanEditDescriptorBuilder()
                .withPerson(JOHN_OUT_UNPAID.getPerson().getName().toString())
                .withAmount(JOHN_OUT_UNPAID.getAmount().toLong()).build();
        LoanEditCommand expectedCommand = new LoanEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFiledSpecified_success() {
        // person
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String targetIndexInput = String.format("%d", targetIndex.getOneBased());
        String userInput = String.join(" ", targetIndexInput, JOHN_PERSON);
        LoanEditDescriptor descriptor = new LoanEditDescriptorBuilder()
                .withPerson(JOHN_OUT_UNPAID.getPerson().getName().toString()).build();
        LoanEditCommand expectedCommand = new LoanEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = String.join(" ", targetIndexInput, JOHN_AMOUNT);
        descriptor = new LoanEditDescriptorBuilder().withAmount(JOHN_OUT_UNPAID.getAmount().toLong()).build();
        expectedCommand = new LoanEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = String.join(" ", targetIndexInput, JOHN_DESCRIPTION);
        descriptor = new LoanEditDescriptorBuilder()
                .withDescription(JOHN_OUT_UNPAID.getDescription().toString()).build();
        expectedCommand = new LoanEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = String.join(" ", targetIndexInput, JOHN_DATE);
        descriptor = new LoanEditDescriptorBuilder().withDate(JOHN_OUT_UNPAID.getDate()).build();
        expectedCommand = new LoanEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String targetIndexStr = String.format("%d", TypicalIndexes.INDEX_FIRST_ITEM.getOneBased());

        String userInput = String.join(" ", targetIndexStr, JOHN_AMOUNT, JOHN_AMOUNT);
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = String.join(" ", targetIndexStr, JOHN_PERSON, JOHN_PERSON);
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = String.join(" ", targetIndexStr, JOHN_DESCRIPTION, JOHN_DESCRIPTION);
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = String.join(" ", targetIndexStr, JOHN_DATE, JOHN_DATE);
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
