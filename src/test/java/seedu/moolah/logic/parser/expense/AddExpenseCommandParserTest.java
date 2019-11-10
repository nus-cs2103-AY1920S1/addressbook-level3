package seedu.moolah.logic.parser.expense;

import static seedu.moolah.commons.core.Messages.MESSAGE_EXPENSE_WITH_FUTURE_TIMESTAMP;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_CATEGORY_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_CATEGORY_DESC_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_DESCRIPTION_DESC_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_PRICE_DESC_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_TIMESTAMP_DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.EXPENSE_TIMESTAMP_DESC_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_CATEGORY_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_DESCRIPTION_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_PRICE_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EXPENSE_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_FUTURE_TIMESTAMP_EXPENSE_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_TIMESTAMP_TAXI;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.expense.AddExpenseCommand;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

public class AddExpenseCommandParserTest {
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    // addCommand should never return a same command as another, so cannot check if the command is same as expected

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser,
                VALID_EXPENSE_DESCRIPTION_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + EXPENSE_CATEGORY_DESC_CHICKEN + EXPENSE_TIMESTAMP_DESC_CHICKEN,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser,
                EXPENSE_DESCRIPTION_DESC_TAXI + VALID_EXPENSE_PRICE_TAXI
                        + EXPENSE_CATEGORY_DESC_CHICKEN + EXPENSE_TIMESTAMP_DESC_CHICKEN,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser,
                EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + VALID_EXPENSE_CATEGORY_TAXI + EXPENSE_TIMESTAMP_DESC_CHICKEN,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EXPENSE_DESCRIPTION_TAXI + VALID_EXPENSE_PRICE_TAXI
                + VALID_EXPENSE_CATEGORY_TAXI + VALID_EXPENSE_TIMESTAMP_TAXI,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser,
                INVALID_EXPENSE_DESCRIPTION_DESC + EXPENSE_PRICE_DESC_TAXI
                        + EXPENSE_CATEGORY_DESC_TAXI + EXPENSE_TIMESTAMP_DESC_TAXI,
                Description.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser,
                EXPENSE_DESCRIPTION_DESC_TAXI + INVALID_EXPENSE_PRICE_DESC
                        + EXPENSE_CATEGORY_DESC_TAXI + EXPENSE_TIMESTAMP_DESC_TAXI,
                Price.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser,
                EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + INVALID_EXPENSE_CATEGORY_DESC + EXPENSE_TIMESTAMP_DESC_TAXI,
                Category.MESSAGE_CONSTRAINTS);

        // invalid timestamp
        assertParseFailure(parser,
                EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + EXPENSE_CATEGORY_DESC_TAXI + INVALID_EXPENSE_TIMESTAMP_DESC,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_EXPENSE_DESCRIPTION_DESC + EXPENSE_PRICE_DESC_TAXI
                        + EXPENSE_CATEGORY_DESC_TAXI + EXPENSE_TIMESTAMP_DESC_TAXI,
                Description.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + EXPENSE_CATEGORY_DESC_TAXI + EXPENSE_TIMESTAMP_DESC_TAXI,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertParseFailure(parser, EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                + EXPENSE_CATEGORY_DESC_CHICKEN + EXPENSE_CATEGORY_DESC_CHICKEN, MESSAGE_REPEATED_PREFIX_COMMAND);
    }

    @Test
    public void parse_expenseWithFutureTimestamp_throwsParseException() {
        assertParseFailure(parser, EXPENSE_DESCRIPTION_DESC_TAXI + EXPENSE_PRICE_DESC_TAXI
                        + INVALID_FUTURE_TIMESTAMP_EXPENSE_TIMESTAMP_DESC + EXPENSE_CATEGORY_DESC_TAXI,
                MESSAGE_EXPENSE_WITH_FUTURE_TIMESTAMP);
    }
}
