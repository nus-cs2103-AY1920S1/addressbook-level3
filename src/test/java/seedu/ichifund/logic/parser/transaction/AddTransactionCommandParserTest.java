package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.commands.CommandTestUtil.AMOUNT_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_TRANSACTION_TYPE_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.DAY_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.DAY_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_MONTH_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.AMOUNT_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ichifund.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_BUS;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.TransactionBuilder;

public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction transaction = new TransactionBuilder(TRANSACTION_ALLOWANCE).build();
        AddTransactionCommand expectedCommand = new AddTransactionCommand(transaction.getDescription(),
                transaction.getAmount(), Optional.of(transaction.getCategory()), 
                Optional.of(transaction.getDay()), Optional.of(transaction.getMonth()), 
                Optional.of(transaction.getYear()), Optional.of(transaction.getTransactionType()));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE
                + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_BUS + DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE
                + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_BUS + AMOUNT_DESC_ALLOWANCE
                + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple days - last day accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_BUS
                + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple months - last month accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_BUS  + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple years - last year accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_BUS + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple categories - last category accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_BUS + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple transaction types - last transaction type accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_BUS
                + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero years
        Transaction transaction = new TransactionBuilder(TRANSACTION_ALLOWANCE).build();
        AddTransactionCommand expectedCommand = new AddTransactionCommand(transaction.getDescription(),
                transaction.getAmount(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());
        assertParseSuccess(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_DESCRIPTION_ALLOWANCE + AMOUNT_DESC_ALLOWANCE 
                        + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + VALID_AMOUNT_ALLOWANCE 
                        + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_ALLOWANCE + VALID_AMOUNT_ALLOWANCE 
                        + VALID_DAY_ALLOWANCE + VALID_MONTH_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE 
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE 
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Description.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + INVALID_AMOUNT_DESC + DAY_DESC_ALLOWANCE 
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Amount.MESSAGE_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + INVALID_DAY_DESC 
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Day.MESSAGE_CONSTRAINTS);

        // invalid month
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE 
                + INVALID_MONTH_DESC + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Month.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE 
                + MONTH_DESC_ALLOWANCE + INVALID_YEAR_DESC + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Year.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + INVALID_CATEGORY_DESC
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Category.MESSAGE_CONSTRAINTS);

        // invalid transaction type
        assertParseFailure(parser, DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE
                + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + INVALID_TRANSACTION_TYPE_DESC, TransactionType.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + AMOUNT_DESC_ALLOWANCE + DAY_DESC_ALLOWANCE 
                        + INVALID_MONTH_DESC + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE,
                Description.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DESCRIPTION_DESC_ALLOWANCE + AMOUNT_DESC_ALLOWANCE
                        + DAY_DESC_ALLOWANCE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE
                        + TRANSACTION_TYPE_DESC_ALLOWANCE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));
    }
}
