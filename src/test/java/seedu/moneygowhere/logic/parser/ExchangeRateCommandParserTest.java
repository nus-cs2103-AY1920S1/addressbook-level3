package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.ExchangeRateCommand;
import seedu.moneygowhere.model.currency.Currency;

class ExchangeRateCommandParserTest {
    private ExchangeRateCommandParser parser = new ExchangeRateCommandParser();

    @Test
    public void parse_emptyArg_returnsExchangeRateCommand() {
        assertParseSuccess(parser, "     ", new ExchangeRateCommand());
    }

    @Test
    public void parse_validArgs_returnsExchangeRateCommand() {
        assertParseSuccess(parser, "", new ExchangeRateCommand());
        assertParseSuccess(parser, " 5 USD", new ExchangeRateCommand(5, "USD"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " 5 MYEEE", Currency.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " -1", ExchangeRateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " -1 USD", ExchangeRateCommand.MESSAGE_INVALID_AMOUNT);
        assertParseFailure(parser, " " + Double.MAX_VALUE + " USD", ExchangeRateCommand.MESSAGE_INVALID_AMOUNT);
        assertParseFailure(parser, " 1 SGD", ExchangeRateCommand.MESSAGE_SAME_CURRENCY);
    }
}
