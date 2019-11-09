package seedu.address.logic.parser.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;

/**
 * Collection of methods for parsing customised currency's abstractions.
 */
public abstract class CurrencyParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String symbol} into a {@code Symbol}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Symbol parseSymbol(String symbol) throws ParseException {
        requireNonNull(symbol);
        String trimmedSymbol = symbol.trim();
        if (!(Symbol.isValidIndex(trimmedSymbol) || Symbol.isValidSymbol(trimmedSymbol))) {
            throw new ParseException(Symbol.MESSAGE_CONSTRAINTS);
        }
        return new Symbol(trimmedSymbol);
    }

    /**
     * Parses a {@code String rate} into a {@code Rate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Rate} is invalid.
     */
    public static Rate parseRate(String rate) throws ParseException {
        requireNonNull(rate);
        String trimmedRate = rate.trim();
        if (!Rate.isValidRate(trimmedRate)) {
            throw new ParseException(Rate.MESSAGE_CONSTRAINTS);
        }
        return new Rate(trimmedRate);
    }

}
