package seedu.address.logic.parser.trips;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;

import static java.util.Objects.requireNonNull;

public abstract class TripParserUtil {

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
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String budget} into a {@code Expenditure}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Expenditure} is invalid.
     */
    public static Expenditure parseBudget(String budget) throws ParseException {
        requireNonNull(budget);
        String trimmedName = budget.trim();
        if (!Expenditure.isValidExpenditure(trimmedName)) {
            throw new ParseException(Expenditure.MESSAGE_CONSTRAINTS);
        }
        return new Expenditure(trimmedName);
    }

}
