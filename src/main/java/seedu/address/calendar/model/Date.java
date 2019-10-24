package seedu.address.calendar.model;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {
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
            this.month = MonthOfYear.convertJavaMonth(currentUnformattedMonth);
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

        if (!MonthOfYear.isValidMonthStr(month)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        if (!isValidYear(year)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        Date specifiedDate = getSpecifiedDate(dayOfWeek, dayOfMonth, month, year);

        return specifiedDate;
    }

    private static Date getSpecifiedDate(String dayOfWeek, String dayOfMonth, String month, String year)
            throws IllegalValueException{
        DayOfWeek dayOfWeekVal = DayOfWeek.valueOf(dayOfWeek);
        MonthOfYear monthVal = MonthOfYear.valueOf(month);
        Year yearVal = new Year(Integer.parseInt(year));
        int dayOfMonthVal = Integer.parseInt(dayOfMonth);

        if (dayOfMonthVal < 0 || dayOfMonthVal > monthVal.getNumDaysInMonth(yearVal)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        Month desiredMonth = new Month(monthVal, yearVal);
        Day givenDay = new Day(dayOfWeekVal, dayOfMonthVal);
        boolean isValidDay = desiredMonth.getDay(dayOfMonthVal).equals(givenDay);

        if (!isValidDay) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }

        return new Date(givenDay, monthVal, yearVal);
    }

    String asString() {
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

    @Override
    public String toString() {
        return String.format("%s %s %s", day, month, year);
    }
}
