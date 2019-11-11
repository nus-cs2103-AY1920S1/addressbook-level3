package seedu.address.calendar.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import seedu.address.calendar.model.util.DateUtil;

/**
 * Creates a day object which contains information about the day of month and day of week.
 * Guarantees: immutable, {@code dayOfMonth} is valid and {@code dayOfWeek} matches {@code dayOfMonth}
 */
public class Day implements Comparable<Day> {
    public static final String MESSAGE_INVALID_DAY_RANGE_ERROR = "Invalid day. Day should exist within stated month.";
    public static final String MESSAGE_MISMATCH = "Invalid day. Day of week and day of month should match.";
    public static final int BOUND_LOWER = 1;
    private DayOfWeek dayOfWeek;
    private int dayOfMonth;

    /**
     * Represents day's day of week in one-based index.
     * Guarantees: {@code dayOfWeek}, {@code monthOfYear} and {@code year} are not {@code null} and {@code day} is valid
     *
     * @param dayOfWeek day of the week (e.g. Sunday, Monday, ...)
     * @param dayOfMonth day of the month (e.g. 1, 2, ..., 31)
     */
    public Day(DayOfWeek dayOfWeek, int dayOfMonth, MonthOfYear monthOfYear, Year year) {
        // todo: check where is this called, ensure that exceptions are handled (should only be called at toModel)
        requireNonNull(dayOfWeek);
        requireNonNull(monthOfYear);
        requireNonNull(year);
        checkArgument(isValidDayOfMonth(dayOfMonth, monthOfYear, year), MESSAGE_INVALID_DAY_RANGE_ERROR);
        checkArgument(isDayOfWeekMatchesDayOfMonth(dayOfWeek, dayOfMonth, monthOfYear, year), MESSAGE_MISMATCH);
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Returns a {@code Day} instance with the specified {@code dayOfWeek} and {@code dayOfMonth}.
     *
     * @param dayOfWeek The specified day of week that is associated with {@code dayOfMonth}
     * @param dayOfMonth The specified day of month that is associated with {@code dayOfWeek} (e.g. 1, 2, ..., 31)
     */
    private Day(DayOfWeek dayOfWeek, int dayOfMonth) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Gets a {@code Day} instance with the correct {@code dayOfWeek}.
     * Guarantees: {@code dayOfMonth} is valid, i.e. it is present in the stated month, and {@code monthOfYear}
     * and {@code dayOfMonth} are not {@code null}
     *
     * @param dayOfMonth The specified day of month (e.g. 1, 2, ..., 31)
     * @param monthOfYear The specified month of year associated with {@code dayOfMonth}
     * @param year The specified year associated with {@code monthOfYear}
     * @return A {@code Day} with the associated day of month and day of week
     */
    public static Day getDay(int dayOfMonth, MonthOfYear monthOfYear, Year year) {
        requireNonNull(monthOfYear);
        requireNonNull(year);
        checkArgument(isValidDayOfMonth(dayOfMonth, monthOfYear, year), MESSAGE_INVALID_DAY_RANGE_ERROR);
        DayOfWeek dayOfWeek = getDayOfWeek(dayOfMonth, monthOfYear, year);
        return new Day(dayOfWeek, dayOfMonth);
    }

    /**
     * Gets the {@code dayOfWeek} for the {@code dayOfMonth} in {@code monthOfYear} {@code year}.
     *
     * @param dayOfMonth The specified day of month (e.g. 1, 2, ..., 31)
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return A {@code DayOfWeek} instance that is associated with {@code dayOfMonth} in {@code monthOfYear}
     *         {@code year}
     */
    private static DayOfWeek getDayOfWeek(int dayOfMonth, MonthOfYear monthOfYear, Year year) {
        int monthOfYearAsInt = monthOfYear.getNumericalVal();
        int yearAsInt = year.getNumericalValue();
        int firstDayOfWeekAsNum = DateUtil.findFirstDayOfWeekAsNum(monthOfYearAsInt, yearAsInt);
        int dayOfMonthZeroBased = toDayOfMonthZeroBased(dayOfMonth);
        DayOfWeek dayOfWeek = getDayOfWeekGivenFirstDayOfMonth(firstDayOfWeekAsNum, dayOfMonthZeroBased);

        return dayOfWeek;
    }

    /**
     * Gets the day of week of {@code this}.
     *
     * @return The day of week of {@code this}
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Get the first {@code DayOfWeek} instance of the specified day when the numerical representation of the first
     * day of week in the specified month is known.
     *
     * @param firstDayOfWeekAsNum The numerical representation of the first day of week in the specified month
     * @param dayOfMonthZeroBased The {@acode dayOfMonthZeroBased}th of the specified month (i.e. 0, 1, ..., 30)
     * @return The {@code DayOfWeek} instance of the {@acode dayOfMonthZeroBased}th of the specified month
     */
    private static DayOfWeek getDayOfWeekGivenFirstDayOfMonth(int firstDayOfWeekAsNum, int dayOfMonthZeroBased) {
        int dayOfWeekAsNum = (firstDayOfWeekAsNum + dayOfMonthZeroBased) % 7;
        DayOfWeek dayOfWeek = DateUtil.toDayOfWeek(dayOfWeekAsNum);
        return dayOfWeek;
    }

    /**
     * Converts day of month from one-based indexing to zero-based indexing.
     * Note: This should only be used for calculation
     *
     * @param dayOfMonth The day of month in one-based index
     * @return The zero-based index representation of {@code dayOfMonth}
     */
    private static int toDayOfMonthZeroBased(int dayOfMonth) {
        return dayOfMonth - 1;
    }

    /**
     * Gets the {@code Day} instances of the specified month of year and year.
     * Guarantees: {@code monthOfYear} and {@code year} are not {@code null}
     *
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return The list of {@code Day} instances of the specified month of year and year
     */
    public static List<Day> getDaysOfMonth(MonthOfYear monthOfYear, Year year) {
        // todo: think about how to ensure that they are not null
        requireNonNull(monthOfYear);
        requireNonNull(year);

        int monthOfYearAsInt = monthOfYear.getNumericalVal();
        int yearAsInt = year.getNumericalValue();
        int firstDayOfWeekAsNum = DateUtil.findFirstDayOfWeekAsNum(monthOfYearAsInt, yearAsInt);
        int daysInMonth = monthOfYear.getNumDaysInMonth(year);
        List<Day> daysOfMonth = new ArrayList<>();

        IntStream.rangeClosed(DateUtil.FIRST_DAY_OF_MONTH, daysInMonth)
                .mapToObj(dayOfMonth -> Day.getDayGivenFirstDayOfWeek(firstDayOfWeekAsNum, dayOfMonth))
                .forEach(day -> daysOfMonth.add(day));

        return daysOfMonth;
    }

    /**
     * Gets the {@code Day} instance of the {@code dayOfMonth}th when the first day of week (as number) is known.
     *
     * @param firstDayOfWeekAsNum The numerical representation of the first day of week (of the month)
     * @param dayOfMonth The {@code dayOfMonth}th of the month
     * @return {@code Day} instance of the {@code dayOfMonth}th when the first day of week (as number) is known
     */
    private static Day getDayGivenFirstDayOfWeek(int firstDayOfWeekAsNum, int dayOfMonth) {
        int dayOfMonthZeroBased = toDayOfMonthZeroBased(dayOfMonth);
        DayOfWeek dayOfWeek = getDayOfWeekGivenFirstDayOfMonth(firstDayOfWeekAsNum, dayOfMonthZeroBased);

        return new Day(dayOfWeek, dayOfMonth);
    }

    /**
     * Gets the day of month of {@code this}.
     *
     * @return The day of month of {@code this}
     */
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Copies {@code this}.
     *
     * @return A copy of {@code this}
     */
    Day copy() {
        return new Day(dayOfWeek, dayOfMonth);
    }

    /**
     * Checks whether the specified day, month of year and year are consistent.
     *
     * @param day The specified day
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return {@code true} if and only if the information provided is consistent
     */
    public static boolean isValidDay(Day day, MonthOfYear monthOfYear, Year year) {
        return isValidDay(day.dayOfWeek, day.dayOfMonth, monthOfYear, year);
    }

    /**
     * Checks whether the specified day of week, day of month, month of year and year are consistent.
     *
     * @param dayOfWeek The specified day of week
     * @param dayOfMonth The specified day of month (i.e. 1, 2, 3, ..., 31)
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return {@code true} if and only if {@code dayOfMonth} exists within the specified month and {@code dayOfWeek}
     *          is consistent with the specified day of month, month of year and year
     */
    public static boolean isValidDay(DayOfWeek dayOfWeek, int dayOfMonth, MonthOfYear monthOfYear, Year year) {
        boolean isValidDayOfMonth = isValidDayOfMonth(dayOfMonth, monthOfYear, year);

        if (!isValidDayOfMonth) {
            return false;
        }

        boolean isCorrectDayOfWeek = isDayOfWeekMatchesDayOfMonth(dayOfWeek, dayOfMonth, monthOfYear, year);
        return isCorrectDayOfWeek;
    }

    /**
     * Checks whether the specified day of month exists in the specified month of year and year.
     *
     * @param dayOfMonth The specified day of month
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return {@code true} if and only if the {@code dayOfMonth} exists in {@code monthOfYear} and {@code Year}
     */
    public static boolean isValidDayOfMonth(int dayOfMonth, MonthOfYear monthOfYear, Year year) {
        boolean hasNoNulls = monthOfYear != null && year != null;

        if (!hasNoNulls) {
            return false;
        }

        boolean isNotLessThanOne = dayOfMonth >= BOUND_LOWER;
        boolean isWithinUpperBound = dayOfMonth <= DateUtil.getNumDaysInMonth(monthOfYear, year);
        return isNotLessThanOne && isWithinUpperBound;
    }

    /**
     * Checks whether the specified day of week matches that of the specified day of month, month of year and year.
     *
     * @param dayOfWeek The specified day of week
     * @param dayOfMonth The specified day of month
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return {@code true} if and only if the {@code dayOfWeek} matches the expected {@code DayOfWeek} instance of
     *          {@code dayOfMonth}, {@code monthOfYear} and {@code year}
     */
    private static boolean isDayOfWeekMatchesDayOfMonth(DayOfWeek dayOfWeek, int dayOfMonth, MonthOfYear monthOfYear,
            Year year) {
        boolean hasNoNulls = monthOfYear != null && year != null && dayOfWeek != null;

        if (!hasNoNulls) {
            return false;
        }

        DayOfWeek expectedDayOfWeek = getDayOfWeek(dayOfMonth, monthOfYear, year);

        return expectedDayOfWeek.equals(dayOfWeek);
    }

    @Override
    public int compareTo(Day other) {
        return this.dayOfMonth - other.dayOfMonth;
    }


    @Override
    public String toString() {
        return String.format("%s, %d", dayOfWeek, dayOfMonth);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Day)) {
            return false;
        }
        Day dayToCompare = (Day) o;
        return dayToCompare.dayOfWeek.equals(this.dayOfWeek) && dayToCompare.dayOfMonth == this.dayOfMonth;
    }

    @Override
    public int hashCode() {
        Object[] arr = {dayOfMonth, dayOfWeek};
        return Arrays.hashCode(arr);
    }
}
