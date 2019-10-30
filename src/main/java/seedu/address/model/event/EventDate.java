package seedu.address.model.event;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Represents the Date of an Event in AddMin+. Events can span a time period of multiple days (dates).
 */
public class EventDate implements Comparable<EventDate> {
    public static final String MESSAGE_CONSTRAINTS = "Date should be in the following format dd/MM/yyyy";
    public static final String MESSAGE_CONSTRAINTS_MONTH = "Input Year Month should be MM/yyyy";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern("MM/yyyy");

    private final LocalDate date;

    /**
     * Constructs a {@code EventDate}.
     *
     * @param date A valid LocalDate Object.
     */
    public EventDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
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

    /**
     * Returns true if a given string is a valid month, year format MM/yyyy.
     */
    public static boolean isValidYearMonth(String test) {
        try {
            return YearMonth.parse(test, FORMATTER_YEAR_MONTH) instanceof YearMonth;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns a sequential stream of Event Dates.
     *
     * @param endInclusive an Event Date that will be included in the Stream
     */
    public Stream<EventDate> datesUntil(EventDate endInclusive) {
        return getDate().datesUntil(endInclusive.getDate().plusDays(1))
            .map(date -> new EventDate(date));
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

    @Override
    public int compareTo(EventDate otherEventDate) {
        return getDate().compareTo(otherEventDate.getDate());
    }
}
