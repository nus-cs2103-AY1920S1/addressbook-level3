package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DOLLAR_AMOUNT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PayCommand;

class PayCommandParserTest {
    private static final String VALID_DOLLAR_AMOUNT = " $1.23";
    private static final int VALID_DOLLAR_AMOUNT_IN_CENTS = 123;
    private static final String EMPTY_STRING = "";
    private static final String INVALID_DOLLAR_AMOUNT = " $aa";

    private PayCommandParser payCommandParser = new PayCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(payCommandParser, VALID_DOLLAR_AMOUNT,
                new PayCommand(VALID_DOLLAR_AMOUNT_IN_CENTS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(payCommandParser, EMPTY_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(payCommandParser, INVALID_DOLLAR_AMOUNT, MESSAGE_INVALID_DOLLAR_AMOUNT);
    }
}
