package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_BUDGET_AMOUNT;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.BudgetCommand;
import seedu.moneygowhere.model.budget.Budget;

class BudgetCommandParserTest {

    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_validArgs_returnBudgetCommand() {
        Budget validBudget = new Budget(0);
        assertParseSuccess(parser, "0", new BudgetCommand(validBudget));
        Budget validBudget2 = new Budget(12);
        assertParseSuccess(parser, "12", new BudgetCommand(validBudget2));
        Budget validBudget3 = new Budget(0.1);
        assertParseSuccess(parser, "0.1", new BudgetCommand(validBudget3));
        Budget validBudget4 = new Budget(1000000000);
        assertParseSuccess(parser, "1000000000", new BudgetCommand(validBudget4));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_BUDGET_AMOUNT);
        assertParseFailure(parser, "-1.23", MESSAGE_INVALID_BUDGET_AMOUNT);
        assertParseFailure(parser, "", MESSAGE_INVALID_BUDGET_AMOUNT);
        assertParseFailure(parser, "abcdef", MESSAGE_INVALID_BUDGET_AMOUNT);
    }
}
