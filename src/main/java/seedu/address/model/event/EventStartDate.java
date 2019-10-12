package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Event Starting Date.
 */
public class EventStartDate {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String MESSAGE_CONSTRAINTS =
            "Start Date Needed should be an in the following format dd/MM/yyyy";

    public final LocalDate startDate;

    /**
     * Constructs a {@code EventStartDate}.
     *
     * @param startDate A valid start date.
     */
    public EventStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns true if a given string is a valid localDate number.
     */
    public static boolean isValidStartDate(String test) {
        try {
            return LocalDate.parse(test, FORMATTER) instanceof LocalDate;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStartDate // instanceof handles nulls
                && startDate.equals(((EventStartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }
}
