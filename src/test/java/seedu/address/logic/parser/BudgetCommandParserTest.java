package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BudgetCommand;
import seedu.address.model.budget.Budget;

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
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BudgetCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1.23", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BudgetCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BudgetCommand.MESSAGE_USAGE));
    }
}
