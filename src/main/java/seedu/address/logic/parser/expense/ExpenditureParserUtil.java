package seedu.address.logic.parser.expense;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

import static java.util.Objects.requireNonNull;

/**
 * Collection of methods for parsing expense's abstractions.
 */
public abstract class ExpenditureParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
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
     * Parses a {@code String budget} into a {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Budget} is invalid.
     */
    public static Budget parseBudget(String budget) throws ParseException {
        requireNonNull(budget);
        String trimmedName = budget.trim();
        if (!Budget.isValidBudget(trimmedName)) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        return new Budget(trimmedName);
    }

}
