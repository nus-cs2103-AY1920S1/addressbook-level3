package seedu.address.calendar.parser;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.Day;
import seedu.address.calendar.model.util.MonthOfYear;
import seedu.address.calendar.model.Year;
import seedu.address.logic.parser.exceptions.ParseException;

import java.sql.Date;
import java.util.Optional;

public class DayParser {
    private static final String MESSAGE_INVALID_DAY_RANGE_ERROR = "Invalid day. Day should exist within stated month.";
    private static final String MESSAGE_NON_INT_DAY_ERROR = "Invalid day. Day should ba represented numerically.";

    private int parseDayOfMonth(String dayStr) throws ParseException {
        try {
            int dayOfMonth = Integer.parseInt(dayStr);
            return dayOfMonth;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_NON_INT_DAY_ERROR);
        }
    }

    private Day parse(int dayOfMonth, MonthOfYear monthOfYear, Year year) throws ParseException {
        if (dayOfMonth < 0 || dayOfMonth > DateUtil.getNumDaysInMonth(monthOfYear, year)) {
            throw new ParseException(MESSAGE_INVALID_DAY_RANGE_ERROR);
        }

        Day day = DateUtil.getDay(dayOfMonth, monthOfYear, year);

        return day;
    }

    Optional<Day> parse(Optional<String> dayInput, Optional<MonthOfYear> month, Optional<Year> year) throws ParseException {
        assert dayInput.isEmpty() : "Day should not be empty";

        int dayOfMonth = parseDayOfMonth(dayInput.get());

        MonthOfYear monthOfYear = month.orElseGet(() -> {
            java.util.Calendar currentDate = java.util.Calendar.getInstance();
            int currentMonth = currentDate.get(java.util.Calendar.MONTH);
            return DateUtil.convertJavaMonth(currentMonth);
        });

        Year yearValue = year.orElseGet(() -> {
            java.util.Calendar currentDate = java.util.Calendar.getInstance();
            int currentYear = currentDate.get(java.util.Calendar.YEAR);
            return new Year(currentYear);
        });

        return Optional.of(parse(dayOfMonth, monthOfYear, yearValue));
    }

}
