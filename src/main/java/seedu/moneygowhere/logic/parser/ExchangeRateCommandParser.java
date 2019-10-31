package seedu.moneygowhere.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.moneygowhere.logic.commands.ExchangeRateCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Parses input arguments and creates a new ExchangeRateCommand object.
 */
public class ExchangeRateCommandParser implements Parser<ExchangeRateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExchangeRateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ExchangeRateCommand();
        }

        String[] words = trimmedArgs.split("\\s+");
        if (words.length != 2) {
            throw new ParseException(ExchangeRateCommand.MESSAGE_USAGE);
        }

        double amount = Double.parseDouble(words[0]);
        String currencyCode = words[1].toUpperCase();

        if (amount <= 0 || amount >= Double.MAX_VALUE) {
            throw new ParseException(ExchangeRateCommand.MESSAGE_INVALID_AMOUNT);
        }

        if (!Currency.isValidCurrencyName(currencyCode)) {
            throw new ParseException(Currency.MESSAGE_CONSTRAINTS);
        }

        if (currencyCode.equalsIgnoreCase("SGD")) {
            throw new ParseException(ExchangeRateCommand.MESSAGE_SAME_CURRENCY);
        }

        return new ExchangeRateCommand(amount, currencyCode);
    }
}
