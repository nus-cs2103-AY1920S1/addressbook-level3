package seedu.address.calendar.model.date;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.IntervalPart;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date implements IntervalPart<Date> {
    private static final String DAY_OF_WEEK_KEY = "dayOfWeek";
    private static final String DAY_OF_MONTH_KEY = "dayOfMonth";
    private static final String MONTH_KEY= "month";
    private static final String YEAR_KEY = "year";
    private static final String DATE_PATTERN= "(?<" + DAY_OF_WEEK_KEY + ">\\S{3})\\,\\s(?<" + DAY_OF_MONTH_KEY + ">"
            + "\\d{1,3})\\s(?<" + MONTH_KEY + ">\\S{3,})\\s(?<" + YEAR_KEY + ">\\d{4})";
    private static final Pattern DATE_FORMAT = Pattern.compile(DATE_PATTERN);

    public static final String MESSAGE_CONSTRAINTS = "Date must be represented in the following format: "
            + "DDD, dd MMM... yyyy\n" + "where D stands for the letters in a day of week (there must be 3), "
            + "d stands for the digit in the day of month (there must be 1 to 2), "
            + "m stands for the letters in a day of month (there must be at least 3) and y stands for "
            + "the digits in a year (there must be 4)";

    private Day day;
    private MonthOfYear month;
    private Year year;

    public Date(Day day, MonthOfYear month, Year year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

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

    public Day getDay() {
        return day;
    }

    public Year getYear() {
        return year;
    }

    public MonthOfYear getMonth() {
        return month;
    }

    public static Date getInstanceFromString(String dateString) throws IllegalValueException {
        final Matcher matcher = DATE_FORMAT.matcher(dateString.trim());
        if (!matcher.matches()) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        String dayOfWeek = matcher.group(DAY_OF_WEEK_KEY).toUpperCase();
        String dayOfMonth = matcher.group(DAY_OF_MONTH_KEY);
        String month = matcher.group(MONTH_KEY).toUpperCase();
        String year = matcher.group(YEAR_KEY);

        try {
            DayOfWeek.valueOf(dayOfWeek);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        if (!DateUtil.isValidMonthStr(month)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        if (!isValidYear(year)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        Date specifiedDate = getSpecifiedDate(dayOfWeek, dayOfMonth, month, year);

        return specifiedDate;
    }

    private static Date getSpecifiedDate(String dayOfWeek, String dayOfMonth, String month, String year)
            throws IllegalValueException {
        DayOfWeek dayOfWeekVal = DayOfWeek.valueOf(dayOfWeek);
        MonthOfYear monthVal = MonthOfYear.valueOf(month);
        Year yearVal = new Year(Integer.parseInt(year));
        int dayOfMonthVal = Integer.parseInt(dayOfMonth);

        // todo: check if exception needs to be explicitly thrown

        Day givenDay = new Day(dayOfWeekVal, dayOfMonthVal, monthVal, yearVal);

        return new Date(givenDay, monthVal, yearVal);
    }

    public String asString() {
        return toString();
    }

    // todo: separate
    private static boolean isValidYear(String yearInput) {
        int year = Integer.parseInt(yearInput);
        if (year < 0) {
            return false;
        }
        return true;
    }

    public Date getPreviousDate() {
        GregorianCalendar javaDate = toJavaDate();
        javaDate.add(Calendar.DAY_OF_MONTH, -1);
        return fromJavaDate(javaDate);
    }

    public Date getNextDate() {
        GregorianCalendar javaDate = toJavaDate();
        javaDate.add(Calendar.DAY_OF_MONTH, 1);
        return fromJavaDate(javaDate);
    }

    private GregorianCalendar toJavaDate() {
        int dayOfMonth = day.getDayOfMonth();
        int monthInt = month.getNumericalVal() - 1;
        int yearInt = year.getNumericalValue();

        return new GregorianCalendar(yearInt, monthInt, dayOfMonth);
    }

    private Date fromJavaDate(GregorianCalendar javaDate) {
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
    public int compareTo(IntervalPart o) {
        if (!(o instanceof Date)) {
            assert false : "Unable to compare different types of interval part";
        }
        return compareTo((Date) o);
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
