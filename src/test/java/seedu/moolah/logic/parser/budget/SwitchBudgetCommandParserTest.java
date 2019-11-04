package seedu.moolah.logic.parser.budget;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.SwitchBudgetCommand;

public class SwitchBudgetCommandParserTest {
    private SwitchBudgetCommandParser parser = new SwitchBudgetCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchBudgetCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, VALID_BUDGET_DESCRIPTION_SCHOOL,
                expectedMessage);
    }
}
