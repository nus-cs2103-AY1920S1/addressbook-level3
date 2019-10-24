package seedu.address.calendar.model;

import java.util.List;
import java.util.stream.Stream;

public class Month {
    private MonthOfYear monthOfYear;
    private List<Day> days;
    private Year year;
    private int daysInMonth;

    public Month(MonthOfYear monthOfYear, Year year) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.daysInMonth = monthOfYear.getNumDaysInMonth(year);
        days = DateUtil.getDaysOfMonth(monthOfYear, year);
    }

    public Month(MonthOfYear monthOfYear, Year year, List<Day> days) {
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.days = days;
        this.daysInMonth = monthOfYear.getNumDaysInMonth(year);
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
