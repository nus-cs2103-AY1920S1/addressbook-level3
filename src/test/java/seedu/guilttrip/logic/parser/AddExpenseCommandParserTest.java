package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DATE_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DATE_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_AMOUNT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_DATE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.guilttrip.logic.commands.CommandTestUtil.NAME_DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.NAME_DESC_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_CLOTHING;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_FOOD;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_WANT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_AMOUNT_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DATE_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_TAG_CLOTHING_CLOTHES;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.CLOTHING_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.FOOD_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.addcommands.AddExpenseCommand;
import seedu.guilttrip.logic.parser.addcommandparsers.AddExpenseCommandParser;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.testutil.ExpenseBuilder;

public class AddExpenseCommandParserTest {
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(FOOD_EXPENSE).build();
        Expense expectedExpenseWithManyTags = new ExpenseBuilder(CLOTHING_EXPENSE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FOOD_EXPENSE + AMOUNT_FOOD_EXPENSE
                        + CATEGORY_FOOD_EXPENSE + DATE_FOOD_EXPENSE + TAG_DESC_FOOD,
                new AddExpenseCommand(expectedExpense));

        // multiple tags - all accepted
        assertParseSuccess(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                        + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT,
                new AddExpenseCommand(expectedExpenseWithManyTags));

        // jumbled up input
        assertParseSuccess(parser, AMOUNT_CLOTHING_EXPENSE + CATEGORY_CLOTHING_EXPENSE
                        + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + NAME_DESC_CLOTHING_EXPENSE + TAG_DESC_WANT,
                new AddExpenseCommand(expectedExpenseWithManyTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(FOOD_EXPENSE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_FOOD_EXPENSE + AMOUNT_FOOD_EXPENSE
                + CATEGORY_FOOD_EXPENSE + DATE_FOOD_EXPENSE, new AddExpenseCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing name prefix
        assertParseFailure(parser, VALID_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + VALID_AMOUNT_CLOTHING_EXPENSE
                + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                + VALID_CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT);

        // missing guilttrip prefix
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                + CATEGORY_CLOTHING_EXPENSE + VALID_DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT);

        // all prefixes missing
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + VALID_AMOUNT_CLOTHING_EXPENSE
                + VALID_CATEGORY_CLOTHING_EXPENSE + VALID_DATE_CLOTHING_EXPENSE + VALID_TAG_CLOTHING_CLOTHES);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_CLOTHING_EXPENSE
                        + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT,
                Description.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + INVALID_AMOUNT
                        + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT,
                Amount.MESSAGE_CONSTRAINTS);

        // invalid Date
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                        + CATEGORY_CLOTHING_EXPENSE + INVALID_DATE + TAG_DESC_CLOTHING + TAG_DESC_WANT,
                Date.MESSAGE_CONSTRAINTS_FOR_ENTRIES);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                        + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + INVALID_TAG_DESC + TAG_DESC_WANT,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_CLOTHING_EXPENSE
                        + CATEGORY_CLOTHING_EXPENSE + INVALID_DATE + INVALID_TAG_DESC + TAG_DESC_WANT,
                Description.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE
                + CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT);
    }

}
