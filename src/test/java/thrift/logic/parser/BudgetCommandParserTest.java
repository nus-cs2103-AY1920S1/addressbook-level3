package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CommandTestUtil;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;

public class BudgetCommandParserTest {
    private BudgetCommandParser parser = new BudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.VALUE_BUDGET
                + CommandTestUtil.DATE_BUDGET));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE);

        // missing value prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_VALUE_BUDGET
                + CommandTestUtil.DATE_BUDGET, expectedMessage);

        // missing date prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALUE_BUDGET
                + CommandTestUtil.VALID_DATE_BUDGET, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_VALUE_BUDGET
                + CommandTestUtil.VALID_DATE_BUDGET, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid value
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_VALUE
                + CommandTestUtil.DATE_BUDGET, BudgetValue.VALUE_CONSTRAINTS);

        // invalid date
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_DATE
                + CommandTestUtil.VALUE_BUDGET, Budget.DATE_CONSTRAINTS);

        // two invalid fields, only first invalid field reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_DATE
                + CommandTestUtil.INVALID_VALUE, Budget.DATE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.VALUE_BUDGET + CommandTestUtil.DATE_BUDGET,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
    }
}
