package seedu.address.model.itinerary;

import java.util.Calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Follows conventions of  ISO 8601 compatible week parameters, " +
            "smallest degree of separation by seconds, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final Calendar date;

    /**
     * Constructs an {@code Date}.
     *
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     * @param hour A valid hour.
     * @param minute A valid minute.
     */
    public Date (int day, int month, int year, int hour, int minute) {
        requireNonNull(day);
        requireNonNull(month);
        requireNonNull(year);
        requireNonNull(hour);
        requireNonNull(minute);

        checkArgument(isValidDate(day, month, year, hour, minute), MESSAGE_CONSTRAINTS);

        Calendar date = new Calendar.Builder()
                .setCalendarType("iso8601")
                .setDate(year, month, day)
                .setTimeOfDay(hour, minute, 0, 0)
                .build();

        this.date = date;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDate (int day, int month, int year, int hour, int minute) {
        boolean isValidDay = day <= 31 && day >= 1;
        boolean isValidMonth = month <=12 && month >= 1;
        boolean isValidYear = year >=0;
        boolean isValidHour = hour >=0 && hour <= 23;
        boolean isValidMinute = minute >= 0 && minute <= 59;
        return isValidDay
                && isValidMonth
                && isValidYear
                && isValidHour
                && isValidMinute;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    public int compareTo(Date date){
        return this.date.compareTo(date.date);
    }

}
