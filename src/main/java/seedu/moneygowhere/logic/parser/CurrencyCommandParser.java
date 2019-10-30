package seedu.moneygowhere.logic.parser;

import seedu.moneygowhere.logic.commands.CurrencyCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CurrencyCommandParser implements Parser<CurrencyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CurrencyCommand
     * and returns an CurrencyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CurrencyCommand parse(String args) throws ParseException {
        String trimmedCurrency = args.trim().toUpperCase();

        if (trimmedCurrency.isEmpty()) {
            return new CurrencyCommand("");
        }

        if (!Currency.isValidCurrencyName(trimmedCurrency)) {
            throw new ParseException(Currency.MESSAGE_CONSTRAINTS);
        }

        return new CurrencyCommand(trimmedCurrency);
    }
}
