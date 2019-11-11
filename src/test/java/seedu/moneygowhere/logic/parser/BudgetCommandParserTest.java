package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moneygowhere.model.budget.Budget.MESSAGE_CONSTRAINTS;

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

        Budget validBudget4 = new Budget(0.01);
        assertParseSuccess(parser, "0.01", new BudgetCommand(validBudget4));

        Budget validBudget5 = new Budget(1000000000);
        assertParseSuccess(parser, "1000000000", new BudgetCommand(validBudget5));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidBudgetCommandExpectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", invalidBudgetCommandExpectedMessage);

        assertParseFailure(parser, "-1", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "-1.23", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "0.001", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "abcdef", MESSAGE_CONSTRAINTS);
    }
}
