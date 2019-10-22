package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE;

import java.util.List;

import thrift.logic.commands.ConvertCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConvertCommand object.
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConvertCommand
     * and returns an ConvertCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_VALUE, CliSyntax.PREFIX_CURRENCY);

        double value;
        List<String> currencies;

        try {
            String valueString = argMultimap.getSingleValue(CliSyntax.PREFIX_VALUE).orElse("1.00");
            value = ParserUtil.parseValue(valueString).getMonetaryValue();


            List<String> currencyStrings = argMultimap.getAllValues(CliSyntax.PREFIX_CURRENCY);
            if (currencyStrings.isEmpty()) {
                throw new ParseException("");
            }
            currencies = ParserUtil.parseCurrencies(currencyStrings);
            if (currencies.size() == 1) {
                currencies.add(0, "SGD");
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE,
                            ConvertCommand.MESSAGE_USAGE,
                            pe.getMessage()), pe);
        }

        return new ConvertCommand(value, currencies);
    }
}
