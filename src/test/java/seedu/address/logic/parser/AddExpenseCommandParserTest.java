package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.CURRENCY_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.CURRENCY_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CURRENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DRINKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRINKS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalExpenses.RUM;
import static seedu.address.testutil.TypicalExpenses.VODKA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.model.commons.Amount;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.Tag;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

public class AddExpenseCommandParserTest {

    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(RUM).withTag(VALID_TAG_DRINKS).build();

        // whitespace only preamble
        assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
                + TAG_DESC_DRINKS, new AddExpenseCommand(expectedExpense));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_VODKA + NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
            + TAG_DESC_DRINKS, new AddExpenseCommand(expectedExpense));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser,
            NAME_DESC_RUM + AMOUNT_DESC_VODKA + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
                + TAG_DESC_DRINKS, new AddExpenseCommand(expectedExpense));

        // multiple currencies - last currency accepted
        assertParseSuccess(parser,
            NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_VODKA + CURRENCY_DESC_RUM + DATE_DESC_RUM
                + TAG_DESC_DRINKS, new AddExpenseCommand(expectedExpense));

        // multiple dates - last date accepted
        assertParseSuccess(parser,
            NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_VODKA + DATE_DESC_RUM
                + TAG_DESC_DRINKS, new AddExpenseCommand(expectedExpense));

        // tags - accepted
        Expense expectedExpenseMultipleTags = new ExpenseBuilder(RUM).withTag(VALID_TAG_DRINKS)
            .build();
        assertParseSuccess(parser,
            NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM + TAG_DESC_DRINKS,
            new AddExpenseCommand(expectedExpenseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(VODKA).build();
        assertParseSuccess(parser, NAME_DESC_VODKA + AMOUNT_DESC_VODKA + DATE_DESC_VODKA + TAG_DESC_ALCOHOL,
            new AddExpenseCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_RUM + AMOUNT_DESC_RUM + DATE_DESC_RUM,
            expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_RUM + VALID_AMOUNT_RUM + DATE_DESC_RUM,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_RUM + VALID_AMOUNT_RUM + VALID_DATE_RUM,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
            + TAG_DESC_ALCOHOL + TAG_DESC_DRINKS, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_RUM + INVALID_AMOUNT_DESC + CURRENCY_DESC_RUM + DATE_DESC_RUM
            + TAG_DESC_ALCOHOL + TAG_DESC_DRINKS, Amount.MESSAGE_CONSTRAINTS);

        // invalid currency
        assertParseFailure(parser, NAME_DESC_RUM + AMOUNT_DESC_RUM + DATE_DESC_RUM + INVALID_CURRENCY_DESC
            + TAG_DESC_ALCOHOL + TAG_DESC_DRINKS, Currency.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + INVALID_DATE_DESC
            + TAG_DESC_ALCOHOL + TAG_DESC_DRINKS, Date.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
            + INVALID_TAG_DESC + VALID_TAG_DRINKS, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + INVALID_DATE_DESC,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
            PREAMBLE_NON_EMPTY + NAME_DESC_RUM + AMOUNT_DESC_RUM + CURRENCY_DESC_RUM + DATE_DESC_RUM
                + TAG_DESC_ALCOHOL + TAG_DESC_DRINKS,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }
}
