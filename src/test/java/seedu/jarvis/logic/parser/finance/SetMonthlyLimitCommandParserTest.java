package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.CommandTestUtil.INVALID_MONTHLY_LIMIT;
import static seedu.jarvis.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jarvis.logic.commands.finance.FinanceCommandTestUtil.HIGH_MONTHLY_LIMIT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.SetMonthlyLimitCommand;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.testutil.finance.MonthlyLimitBuilder;

public class SetMonthlyLimitCommandParserTest {

    private SetMonthlyLimitCommandParser parser = new SetMonthlyLimitCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        MonthlyLimit expectedLimit = new MonthlyLimitBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + HIGH_MONTHLY_LIMIT,
                new SetMonthlyLimitCommand(expectedLimit));

        // multiple values - last value accepted
        assertParseSuccess(parser, HIGH_MONTHLY_LIMIT + HIGH_MONTHLY_LIMIT,
                new SetMonthlyLimitCommand(expectedLimit));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetMonthlyLimitCommand.MESSAGE_USAGE);

        // missing value
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidMoneyAmount_failure() {
        assertParseFailure(parser, INVALID_MONTHLY_LIMIT, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetMonthlyLimitCommand.MESSAGE_MONEY_ERROR));
    }
}
