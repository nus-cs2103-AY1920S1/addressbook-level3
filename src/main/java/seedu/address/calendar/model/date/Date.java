package seedu.address.calendar.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.IntervalPart;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date with specified day, month and year.
 * Guarantees: immutable, and {@code Day}, {@code MonthOfYear} and {@code Year} instances associated with the date
 *              is not {@code null}
 */
public class Date implements IntervalPart<Date> {
    private static final String DAY_OF_WEEK_KEY = "dayOfWeek";
    private static final String DAY_OF_MONTH_KEY = "dayOfMonth";
    private static final String MONTH_KEY = "month";
    private static final String YEAR_KEY = "year";
    private static final String DATE_PATTERN = "(?<" + DAY_OF_WEEK_KEY + ">\\S{3})\\,\\s(?<" + DAY_OF_MONTH_KEY + ">"
            + "\\d{1,2})\\s(?<" + MONTH_KEY + ">\\S{3,})\\s(?<" + YEAR_KEY + ">\\d{4})";
    private static final Pattern DATE_FORMAT = Pattern.compile(DATE_PATTERN);

    public static final String MESSAGE_CONSTRAINTS = "Date must be represented in the following format: "
            + "DDD, dd MMM... yyyy\n" + "where D stands for the letters in a day of week (there must be 3), "
            + "d stands for the digit in the day of month (there must be 1 to 2), "
            + "M stands for the letters in a day of month (there must be at least 3) and y stands for "
            + "the digits in a year (there must be 4).\n"
            + "The year must also be between 1980 and 2200.";
    private static final String MESSAGE_DATE_CONSTRAINT = "Date's fields must be consistent";

    private Day day;
    private MonthOfYear month;
    private Year year;

    /**
     * Represents a date with the specified day, month and year.
     * Guarantees: the specified day, month and year are not {@code null}, and the date is valid (i.e. exists)
     *
     * @param day The specified day
     * @param month The specified month
     * @param year The specified year
     */
    public Date(Day day, MonthOfYear month, Year year) {
        requireNonNull(day);
        requireNonNull(month);
        requireNonNull(year);
        checkArgument(Day.isValidDay(day, month, year), MESSAGE_DATE_CONSTRAINT);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Represents a date with the specified day. If month and year are not specified, the current month and year
     * will be used.
     * Guarantees: the specified day, month and year are not {@code null}, and the date is valid (i.e. exists)
     *
     * @param day The specified day, compulsory
     * @param month The specified month, if any
     * @param year The specified year, if any
     */
    public Date(Optional<Day> day, Optional<MonthOfYear> month, Optional<Year> year) {
        assert day.isEmpty() : "Every date should have a specified day";
        this.day = day.get();

        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        if (month.isEmpty()) {
            int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
            this.month = DateUtil.convertJavaMonth(currentUnformattedMonth);
        } else {
            this.month = month.get();
        }

        if (year.isEmpty()) {
            int currentUnformattedYear = currentDate.get(java.util.Calendar.YEAR);
            this.year = new Year(currentUnformattedYear);
        } else {
            this.year = year.get();
        }
    }

    /**
     * Gets the day of {@code this}.
     *
     * @return The day of {@code this}
     */
    public Day getDay() {
        return day;
    }

    /**
     * Gets the year of {@code this}.
     *
     * @return The year of {@code this}
     */
    public Year getYear() {
        return year;
    }

    /**
     * Gets the month of year of {@code this}.
     *
     * @return The month of year of {@code this}
     */
    public MonthOfYear getMonth() {
        return month;
    }

    /**
     * Gets a {@code Date} instance from a representative {@code String}, if possible.
     *
     * @param dateString The representative {@code String} of a {@code Date} instance
     * @return A {@code Date} instance that is represented by {@code dateString}
     * @throws IllegalValueException if {@code dateString} doesn't represent a {@code Date} instance correctly
     */
    public static Date getInstanceFromString(String dateString) throws IllegalValueException {
        String dateStringTrimmed = dateString.trim();
        final Matcher matcher = DATE_FORMAT.matcher(dateStringTrimmed);
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        String dayOfWeek = matcher.group(DAY_OF_WEEK_KEY).toUpperCase();
        String dayOfMonth = matcher.group(DAY_OF_MONTH_KEY);
        String month = matcher.group(MONTH_KEY).toUpperCase();
        String year = matcher.group(YEAR_KEY);

        try {
            DayOfWeek.valueOf(dayOfWeek);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        if (!DateUtil.isValidMonthStr(month)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        Date specifiedDate = getSpecifiedDate(dayOfWeek, dayOfMonth, month, year);

        return specifiedDate;
    }

    /**
     * Gets a {@code Date} instance when given a representative day of week {@code String}, day of month {@code String},
     * month {@code String} and year {@code String}.
     * Guarantees: None of the parameters are {@code null}
     *
     * @param dayOfWeek The representative day of week {@code String}
     * @param dayOfMonth The representative day of month {@code String}
     * @param month The representative month {@code String}
     * @param year The representative year {@code String}
     * @return A {@code Date} instance that is represented by the {@code String}s
     */
    private static Date getSpecifiedDate(String dayOfWeek, String dayOfMonth, String month, String year) throws
            IllegalValueException {
        requireNonNull(dayOfWeek);
        requireNonNull(dayOfMonth);
        requireNonNull(month);
        requireNonNull(year);
        DayOfWeek dayOfWeekVal = DayOfWeek.valueOf(dayOfWeek);
        MonthOfYear monthVal = DateUtil.convertStrToMonth(month);
        Year yearVal = new Year(Integer.parseInt(year));
        int dayOfMonthVal = Integer.parseInt(dayOfMonth);

        if (!Day.isValidDayOfMonth(dayOfMonthVal, monthVal, yearVal)) {
            throw new IllegalValueException(Day.MESSAGE_INVALID_DAY_RANGE_ERROR);
        } else if (!Day.isValidDay(dayOfWeekVal, dayOfMonthVal, monthVal, yearVal)) {
            throw new IllegalValueException(Day.MESSAGE_MISMATCH);
        }

        Day givenDay = new Day(dayOfWeekVal, dayOfMonthVal, monthVal, yearVal);

        return new Date(givenDay, monthVal, yearVal);
    }

    /**
     * Represents {@code this} as a string.
     *
     * @return A representative {@code String} of {@code this}
     */
    public String asString() {
        return toString();
    }

    /**
     * Gets an {@code Date} instance that is temporally before {@code this}.
     *
     * @return An {@code Date} instance that is temporally before {@code this}
     */
    public Date getPreviousDate() {
        GregorianCalendar javaDate = toJavaDate();
        javaDate.add(Calendar.DAY_OF_MONTH, -1);
        return Date.fromJavaDate(javaDate);
    }

    /**
     * Gets an {@code Date} instance that is temporally after {@code this}.
     *
     * @return An {@code Date} instance that is temporally after {@code this}
     */
    public Date getNextDate() {
        GregorianCalendar javaDate = toJavaDate();
        javaDate.add(Calendar.DAY_OF_MONTH, 1);
        return Date.fromJavaDate(javaDate);
    }

    /**
     * Converts {@code this} to a {@code GregorianCalendar} representation.
     *
     * @return A {@code GregorianCalendar} representation of {@code this}
     */
    private GregorianCalendar toJavaDate() {
        int dayOfMonth = day.getDayOfMonth();
        int monthInt = month.getNumericalVal() - 1;
        int yearInt = year.getNumericalValue();

        return new GregorianCalendar(yearInt, monthInt, dayOfMonth);
    }

    /**
     * Converts the specified {@code GregorianCalendar} representation to an instance of {@code Date}.
     *
     * @param javaDate The specified {@code GregorianCalendar} representation to convert
     * @return An instance of {@code Date} which represents {@code javaDate}
     */
    private static Date fromJavaDate(GregorianCalendar javaDate) {
        requireNonNull(javaDate);
        int yearInt = javaDate.get(Calendar.YEAR);
        int monthInt = javaDate.get(Calendar.MONTH);
        int dayOfMonth = javaDate.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekInt = javaDate.get(Calendar.DAY_OF_WEEK) - 1;

        Year year = new Year(yearInt);
        MonthOfYear monthOfYear = DateUtil.convertJavaMonth(monthInt);
        DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekInt);
        assert Day.isValidDay(dayOfWeek, dayOfMonth, monthOfYear, year) : "Day should be valid";
        Day day = new Day(dayOfWeek, dayOfMonth, monthOfYear, year);

        return new Date(day, monthOfYear, year);
    }

    /**
     * Compares {@code this} to the other date.
     *
     * @param otherDate The other date to be compared to
     * @return An {@code int} representing the order between the two dates (follows Java conventions)
     */
    public int compareTo(Date otherDate) {
        Year otherYear = otherDate.year;
        int compareYear = year.compareTo(otherYear);

        if (compareYear != 0) {
            return compareYear;
        }

        MonthOfYear otherMonth = otherDate.month;
        int compareMonth = month.compareTo(otherMonth);

        if (compareMonth != 0) {
            return compareMonth;
        }

        Day otherDay = otherDate.day;
        return day.compareTo(otherDay);
    }

    @Override
    public int compareTo(IntervalPart o) {
        if (!(o instanceof Date)) {
            assert false : "Unable to compare different types of interval part";
        }
        return compareTo((Date) o);
    }

    @Override
    public Date copy() {
        Day copiedDay = day.copy();
        Year copiedYear = year.copy();
        return new Date(copiedDay, month, copiedYear);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) obj;
        Year otherYear = otherDate.year;
        boolean isSameYear = year.equals(otherYear);

        MonthOfYear otherMonth = otherDate.month;
        boolean isSameMonth = month.equals(otherMonth);

        Day otherDay = otherDate.day;
        boolean isSameDay = day.equals(otherDay);

        return isSameYear && isSameMonth && isSameDay;
    }

    @Override
    public int hashCode() {
        Object[] arr = {day, month, year};
        return Arrays.hashCode(arr);
    }
}
