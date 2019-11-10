package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_CATEGORY_TYPE_BUDGET;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
<<<<<<< HEAD
=======
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_NAME_EXPENSE_FOOD;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_TYPE_EXPENSE;
>>>>>>> 6c8808293a4466f0a09dd6f1a452ffdb7b4e87c7
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_BUSINESS;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_FOOD;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.addcommands.AddCategoryCommand;
import seedu.guilttrip.logic.parser.addcommandparsers.AddCategoryCommandParser;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.testutil.CategoryBuilder;

public class AddCategoryCommandParserTest {
    private AddCategoryCommandParser parser = new AddCategoryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Category expectedCategoryExpense = new CategoryBuilder(CATEGORY_FOOD).build();
        Category expectedCategoryIncome = new CategoryBuilder(CATEGORY_BUSINESS).build();
        //whitespace only preamble, Expense
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CATEGORY_TYPE_EXPENSE + CATEGORY_NAME_EXPENSE,
                new AddCategoryCommand(expectedCategoryExpense));
        // whitespace only preamble, Income
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CATEGORY_TYPE_INCOME + CATEGORY_NAME_INCOME,
                new AddCategoryCommand(expectedCategoryIncome));
    }

    @Test
<<<<<<< HEAD
=======
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCategoryCommand.MESSAGE_USAGE);

        // missing category prefix
        assertParseFailure(parser, VALID_CATEGORY_TYPE_EXPENSE + CATEGORY_NAME_EXPENSE);

        // missing Category name prefix
        assertParseFailure(parser, CATEGORY_TYPE_EXPENSE + VALID_CATEGORY_NAME_EXPENSE_FOOD);

        // all prefixes missing
        assertParseFailure(parser, VALID_CATEGORY_TYPE_EXPENSE + VALID_CATEGORY_NAME_EXPENSE_FOOD);
    }

    @Test
>>>>>>> 6c8808293a4466f0a09dd6f1a452ffdb7b4e87c7
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_CATEGORY_TYPE_BUDGET + CATEGORY_NAME_EXPENSE,
                Category.MESSAGE_CONSTRAINTS_TYPE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CATEGORY_TYPE_INCOME + CATEGORY_NAME_INCOME);
    }
}
