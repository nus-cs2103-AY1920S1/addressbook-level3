package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.CurrencyCommand;
import seedu.moneygowhere.model.currency.Currency;

class CurrencyCommandParserTest {

    private CurrencyCommandParser parser = new CurrencyCommandParser();

    @Test
    public void parse_validInput_success() {
        assertParseSuccess(parser, " sgd", new CurrencyCommand("SGD"));
        assertParseSuccess(parser, " SGD", new CurrencyCommand("SGD"));

        assertParseSuccess(parser, "", new CurrencyCommand(""));

        assertParseSuccess(parser, " ", new CurrencyCommand(""));
    }

    @Test
    public void parse_invalidInput_failure() {
        assertParseFailure(parser, " SGDEE", Currency.MESSAGE_CONSTRAINTS);
    }
}
