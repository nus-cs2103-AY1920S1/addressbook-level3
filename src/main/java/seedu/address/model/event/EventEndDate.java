package seedu.address.model.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Event Starting Date.
 */
public class EventEndDate {

    public final LocalDate endDate;

    /**
     * Constructs a {@code EventStartDate}.
     *
     * @param endDate A valid end date.
     */
    public EventEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
