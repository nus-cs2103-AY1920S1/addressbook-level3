package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Event Starting Date.
 */
public class EventEndDate {

    public static final String MESSAGE_CONSTRAINTS =
            "End Date Needed should be an in the following format DDMMYYYY";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final LocalDate endDate;

    /**
     * Constructs a {@code EventStartDate}.
     *
     * @param endDate A valid end date.
     */
    public EventEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    /**
     * Returns true if a given string is a valid localDate number.
     */
    public static boolean isValidEndDate(String test) {
        try {
            return LocalDate.parse(test, FORMATTER) instanceof LocalDate;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventEndDate // instanceof handles nulls
                && endDate.equals(((EventEndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
