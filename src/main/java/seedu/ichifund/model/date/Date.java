package seedu.ichifund.model.date;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Date in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date implements Comparable<Date> {
    public static final String MESSAGE_CONSTRAINTS =
            "Day should match with month.";

    private java.util.Date date;
    private Day day;
    private Month month;
    private Year year;

    /**
     * Constructs a {@code Date}.
     *
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Date(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public Day getDay() {
        return day;
    }

    /**
     * Returns true if a given {@code Date} is a valid date.
     */
    public static boolean isValidDate(Day day, Month month, Year year) {
        int dayNumber = day.getDayNumber();
        if (month.has30Days()) {
            return dayNumber <= 30;
        } else if (month.has31Days()) {
            return dayNumber <= 31;
        } else if (year.isLeapYear()) {
            return dayNumber <= 29;
        } else {
            return dayNumber <= 28;
        }
    }

    @Override
    public int compareTo(Date other) {
        return this.date.compareTo(other.date);
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year, date);
    }
}
