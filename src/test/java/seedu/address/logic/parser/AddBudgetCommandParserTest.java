package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddBudgetCommand;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Money;
import seedu.address.testutil.BudgetBuilder;

import java.text.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Budget expectedBudget = new BudgetBuilder().withAmount(VALID_AMOUNT_ONE_THOUSAND).withName(VALID_BUDGET_EQUIPMENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BUDGET_DESC_EQUIPMENT
                + AMOUNT_DESC_ONE_THOUSAND, new AddBudgetCommand(expectedBudget));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BUDGET_DESC_MANPOWER
                + AMOUNT_DESC_ONE_THOUSAND + BUDGET_DESC_EQUIPMENT, new AddBudgetCommand(expectedBudget));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BUDGET_DESC_EQUIPMENT
                + AMOUNT_DESC_FIFTY + AMOUNT_DESC_ONE_THOUSAND, new AddBudgetCommand(expectedBudget));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);

        // missing budget prefix
        assertParseFailure(parser, VALID_AMOUNT_FIFTY,
                expectedMessage);

        // missing expense prefix
        assertParseFailure(parser, VALID_BUDGET_EQUIPMENT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String errorMessage = "Budget name should only contain alphanumeric characters and spaces, and it should not be blank";
        // invalid budget
        assertParseFailure(parser, INVALID_BUDGET_DESC + AMOUNT_DESC_FIFTY, errorMessage);

        // invalid amount
        assertParseFailure(parser, BUDGET_DESC_EQUIPMENT + INVALID_AMOUNT_DESC, Money.MESSAGE_CONSTRAINTS);
    }

}
