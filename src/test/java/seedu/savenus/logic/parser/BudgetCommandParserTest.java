package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;


public class BudgetCommandParserTest {

    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_validArgs_returnsBudgetCommand() {
        assertParseSuccess(parser, "100 30", new BudgetCommand(
                new Wallet(new RemainingBudget("100"),
                        new DaysToExpire("30"))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abcd asd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }
}
