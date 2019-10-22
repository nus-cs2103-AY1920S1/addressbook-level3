package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Date of an Event in AddMin+. Events can span a time period of multiple days (dates).
 */
public class EventDate {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the following format DDMMYYYY";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final LocalDate date;

    /**
     * Constructs a {@code EventDate}.
     *
     * @param date A valid LocalDate Object.
     */
    public EventDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid localDate number.
     */
    public static boolean isValidDate(String test) {
        try {
            return LocalDate.parse(test, FORMATTER) instanceof LocalDate;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && date.equals(((EventDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
