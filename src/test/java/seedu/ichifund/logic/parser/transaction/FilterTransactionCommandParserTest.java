package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.CATEGORY_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_MONTH_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_TRANSACTION_TYPE_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.MONTH_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ichifund.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.YEAR_DESC_BUS;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.TransactionBuilder;

public class FilterTransactionCommandParserTest {
    private FilterTransactionCommandParser parser = new FilterTransactionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction transaction = new TransactionBuilder(TRANSACTION_ALLOWANCE).build();
        FilterTransactionCommand expectedCommand = new FilterTransactionCommand(Optional.of(transaction.getMonth()),
                Optional.of(transaction.getYear()), Optional.of(transaction.getCategory()),
                Optional.of(transaction.getTransactionType()));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE
                + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple months - last month accepted
        assertParseSuccess(parser, MONTH_DESC_BUS + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE
                + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple years - last year accepted
        assertParseSuccess(parser, MONTH_DESC_ALLOWANCE + YEAR_DESC_BUS + YEAR_DESC_ALLOWANCE
                + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple categories - last category accepted
        assertParseSuccess(parser, MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_BUS
                + CATEGORY_DESC_ALLOWANCE + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);

        // multiple transaction types - last transaction type accepted
        assertParseSuccess(parser, MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_BUS + TRANSACTION_TYPE_DESC_ALLOWANCE, expectedCommand);
    }

    @Test
    public void parse_allFieldsMissing_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid month
        assertParseFailure(parser, INVALID_MONTH_DESC + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Month.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, MONTH_DESC_ALLOWANCE + INVALID_YEAR_DESC + CATEGORY_DESC_ALLOWANCE
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Year.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + INVALID_CATEGORY_DESC
                + TRANSACTION_TYPE_DESC_ALLOWANCE, Category.MESSAGE_CONSTRAINTS);

        // invalid transaction type
        assertParseFailure(parser, MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + INVALID_TRANSACTION_TYPE_DESC, TransactionType.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MONTH_DESC + YEAR_DESC_ALLOWANCE + CATEGORY_DESC_ALLOWANCE
                + INVALID_TRANSACTION_TYPE_DESC, Month.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MONTH_DESC_ALLOWANCE + YEAR_DESC_ALLOWANCE
                        + TRANSACTION_TYPE_DESC_ALLOWANCE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTransactionCommand.MESSAGE_USAGE));
    }
}
