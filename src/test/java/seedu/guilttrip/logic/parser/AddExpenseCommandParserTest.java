package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DATE_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DATE_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.NAME_DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.NAME_DESC_FOOD_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_CLOTHING;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_FOOD;
import static seedu.guilttrip.logic.commands.CommandTestUtil.TAG_DESC_WANT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.CLOTHING_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.FOOD_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.TRAVEL_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.addcommands.AddCommand;
import seedu.guilttrip.logic.commands.addcommands.AddExpenseCommand;
import seedu.guilttrip.logic.parser.addcommandparsers.AddExpenseCommandParser;
import seedu.guilttrip.model.entry.Expense;
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
        assertParseSuccess(parser, NAME_DESC_CLOTHING_EXPENSE + AMOUNT_CLOTHING_EXPENSE +
                        CATEGORY_CLOTHING_EXPENSE + DATE_CLOTHING_EXPENSE + TAG_DESC_CLOTHING + TAG_DESC_WANT,
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
    
}
