package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Expense's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimestamp(String)}
 */
public class Timestamp {

    public static final String MESSAGE_CONSTRAINTS_DATE =
            "Timestamps must be in the format dd-MM";

    public static final String MESSAGE_CONSTRAINTS_PERIOD =
            "Input period is not week/month/year";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM");

    public final LocalDate timestamp;

    /**
     * Constructs a {@code Description}.
     *
     * @param rawTimestamp A valid description.
     */
    public Timestamp(String rawTimestamp) {
        requireNonNull(rawTimestamp);
        checkArgument(isValidTimestamp(rawTimestamp), MESSAGE_CONSTRAINTS_DATE);
        timestamp = LocalDate.parse(rawTimestamp, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidTimestamp(String test) { // TBI
        try {
            LocalDate.parse(test, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return timestamp.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timestamp // instanceof handles nulls
                && timestamp.equals(((Timestamp) other).timestamp)); // state check
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }
}
