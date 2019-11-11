package seedu.ichifund.model.date;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Date in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date implements Comparable<Date> {
    public static final String MESSAGE_CONSTRAINTS =
            "Day should match with month and year.";

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

    public static Date getCurrent() {
        return new Date(Day.getCurrent(), Month.getCurrent(), Year.getCurrent());
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
     * Returns true if the {@code Date} constructed from a {@code Day}, {@code Month} and {@code Year} is a valid date.
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

    /**
     * Returns true if a given {@code Date} is a valid date.
     */
    public static boolean isValidDate(Date date) {
        return isValidDate(date.getDay(), date.getMonth(), date.getYear());
    }

    public boolean isIn(Month month) {
        return getMonth().equals(month);
    }

    public boolean isIn(Year year) {
        return getYear().equals(year);
    }

    public String toFullString() {
        return day.toString() + " " + month.wordString() + " " + year.toString();
    }

    @Override
    public int compareTo(Date other) {
        if (getYear().equals(other.getYear()) && getMonth().equals(other.getMonth())) {
            return getDay().compareTo(other.getDay());
        } else if (getYear().equals(other.getYear())) {
            return getMonth().compareTo(other.getMonth());
        } else {
            return getYear().compareTo(other.getYear());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;

        return otherDate.getDay().equals(getDay())
                && otherDate.getMonth().equals(getMonth())
                && otherDate.getYear().equals(getYear());
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
