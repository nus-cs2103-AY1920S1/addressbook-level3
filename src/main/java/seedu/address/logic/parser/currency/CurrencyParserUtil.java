package seedu.address.logic.parser.currency;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Collection of methods for parsing trip's abstractions.
 */
public abstract class CurrencyParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static String parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String symbol} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseSymbol(String symbol) {
        requireNonNull(symbol);
        String trimmedName = symbol.trim();
        return trimmedName;
    }

    /**
     * Parses a {@code String rate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rate} is invalid.
     */
    public static double parseRate(String rate) throws ParseException {
        requireNonNull(rate);
        String trimmedRate = rate.trim();
        if (!Budget.isValidBudget(trimmedRate)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return Double.parseDouble(trimmedRate);
    }

}
