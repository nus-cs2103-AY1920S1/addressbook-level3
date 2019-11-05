package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_INCOME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_CATEGORY_TYPE_BUDGET;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.guilttrip.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_BUSINESS;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_FOOD;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.AddCategoryCommand;
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
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_CATEGORY_TYPE_BUDGET + CATEGORY_NAME_EXPENSE,
                Category.MESSAGE_CONSTRAINTS_TYPE);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CATEGORY_TYPE_INCOME + CATEGORY_NAME_INCOME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCategoryCommand.MESSAGE_USAGE));
    }
}
