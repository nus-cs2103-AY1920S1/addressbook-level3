package seedu.address.model.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Event Starting Date.
 */
public class EventStartDate {

    public final LocalDate startDate;

    /**
     * Constructs a {@code EventStartDate}.
     *
     * @param startDate A valid start date.
     */
    public EventStartDate(LocalDate startDate) {
        this.startDate = startDate;
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
