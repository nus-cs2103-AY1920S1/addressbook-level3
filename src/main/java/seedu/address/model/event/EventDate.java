package seedu.address.model.event;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_INVALID;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import seedu.address.commons.core.Config;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Single Date of an Event. Events can span a time period of multiple days (dates).
 * Valid with Constraints.
 */
public class EventDate implements Comparable<EventDate> {
    public static final String MESSAGE_CONSTRAINTS = MESSAGE_DATE_INVALID;
    public static final String MESSAGE_CONSTRAINTS_MONTH = "Input Year Month should be MM/yyyy";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern("MM/yyyy");

    private final LocalDate date;

    /**
     * Constructs a {@code EventDate}.
     *
     * @param date A valid LocalDate Object that satisfies the Date Constraints.
     */
    public EventDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Boolean to check if the current EventDate is after another EventDate
     * Note: If same, date, return false.
     */
    public boolean isAfter(EventDate otherDate) {
        return date.isAfter(otherDate.getDate());
    }

    /**
     * Boolean to check if the current EventDate is before another EventDate
     * Note: If same, date, return false.
     */
    public boolean isBefore(EventDate otherDate) {
        return date.isBefore(otherDate.getDate());
    }

    /**
     * Returns true if a given string can be parsed to a LocalDate and satisfy the Constraints.
     */
    public static boolean isValidDate(String test) {
        try {
            return ParserUtil.parseAnyDate(test) instanceof LocalDate;
        } catch (ParseException e) {
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
     * Returns true if the current EventDate is already past, according to the System's Date.
     */
    public boolean isPastDate() {
        return Config.getCurrentDate().isAfter(date);
    }

    /**
     * Calculates the difference, in number of days between two EventDates.
     */
    public long dateDifference(EventDate other) {
        long daysBetween = DAYS.between(date, other.getDate());
        return daysBetween;
    }

    /**
     * Returns a sequential stream of {@code EventDate}.
     *
     * @param endInclusive an {@code EventDate} that acts as the ending range (inclusive) of the Stream.
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
