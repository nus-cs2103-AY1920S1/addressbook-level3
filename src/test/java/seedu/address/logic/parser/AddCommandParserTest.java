package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CLAIMABLE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DISCOUNTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CLAIMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DISCOUNTED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalExpenses.CHICKEN;
import static seedu.address.testutil.TypicalExpenses.TRANSPORT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ExpenseBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(TRANSPORT).withTags(VALID_TAG_DISCOUNTED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DESCRIPTION_DESC_TRANSPORT
                + PRICE_DESC_TRANSPORT + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpense));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_CHICKEN + DESCRIPTION_DESC_TRANSPORT
                + PRICE_DESC_TRANSPORT + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpense));

        // multiple prices - last price accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_CHICKEN
                + PRICE_DESC_TRANSPORT + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpense));

        // multiple emails - last email accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_TRANSPORT
                + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpense));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_TRANSPORT
                + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpense));

        // multiple tags - all accepted
        Expense expectedExpenseMultipleTags = new ExpenseBuilder(TRANSPORT)
                .withTags(VALID_TAG_DISCOUNTED, VALID_TAG_CLAIMABLE)
                .build();
        assertParseSuccess(parser, DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_TRANSPORT
                + TAG_DESC_DISCOUNTED + TAG_DESC_CLAIMABLE, new AddCommand(expectedExpenseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(CHICKEN).withTags().build();
        assertParseSuccess(parser, DESCRIPTION_DESC_CHICKEN + PRICE_DESC_CHICKEN,
                new AddCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_DESCRIPTION_TRANSPORT + PRICE_DESC_TRANSPORT,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, DESCRIPTION_DESC_TRANSPORT + VALID_PRICE_TRANSPORT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_TRANSPORT + VALID_PRICE_TRANSPORT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser,
                INVALID_DESCRIPTION_DESC + PRICE_DESC_TRANSPORT
                        + TAG_DESC_DISCOUNTED + TAG_DESC_CLAIMABLE,
                Description.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser,
                DESCRIPTION_DESC_TRANSPORT + INVALID_PRICE_DESC
                        + TAG_DESC_DISCOUNTED + TAG_DESC_CLAIMABLE,
                Price.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_TRANSPORT
                + INVALID_TAG_DESC + VALID_TAG_DISCOUNTED, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + PRICE_DESC_TRANSPORT,
                Description.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + DESCRIPTION_DESC_TRANSPORT + PRICE_DESC_TRANSPORT
                        + TAG_DESC_DISCOUNTED + TAG_DESC_CLAIMABLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
