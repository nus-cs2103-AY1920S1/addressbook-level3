package seedu.address.logic.parser.expense;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Collection of methods for parsing expense's abstractions.
 */
public abstract class ExpenseParserUtil {

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

    /**
     * Parses a {@code String dayNumber} into a {@code DayNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DayNumber} is invalid.
     */
    public static DayNumber parseDayNumber(String dayNumber) throws ParseException {
        requireNonNull(dayNumber);
        String trimmedDayNumber = dayNumber.trim();
        if (!DayNumber.isValidDayNumber(trimmedDayNumber)) {
            throw new ParseException(DayNumber.MESSAGE_CONSTRAINTS);
        }
        return new DayNumber(trimmedDayNumber);
    }

}
