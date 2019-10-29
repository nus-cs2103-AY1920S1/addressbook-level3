package seedu.address.calendar.model;

import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.date.MonthOfYear;

import java.util.List;
import java.util.stream.Stream;

public class Month {
    private MonthOfYear monthOfYear;
    private List<Day> days;
    private Year year;

    public Month(MonthOfYear monthOfYear, Year year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        days = DateUtil.getDaysOfMonth(monthOfYear, year);
    }

    public Month(MonthOfYear monthOfYear, Year year, List<Day> days) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.days = days;
    }

    public Year getYear() {
        return year;
    }

    public MonthOfYear getMonthOfYear() {
        return monthOfYear;
    }

    public Stream<Day> getDaysInMonth() {
        return days.stream();
    }

    public Day getFirstDayOfMonth() {
        return days.get(0);
    }

    static Month copy(Month monthToCopy) {
        List<Day> copiedDays = List.copyOf(monthToCopy.days);
        return new Month(monthToCopy.monthOfYear, monthToCopy.year, copiedDays);
    }
}
