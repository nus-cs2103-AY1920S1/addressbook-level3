package seedu.address.calendar.model;

import java.util.stream.Stream;

/**
 * Simplified and static calendar
 */
public class CalendarSimplified extends Calendar {

    Month month = new Month(MonthOfYear.OCTOBER, 2019);

    // todo: use Java built-in function to get current date and time
    public Stream<Day> getDays() {
        return (new Month(MonthOfYear.OCTOBER, 2019)).getDaysInMonth();
    }

    public Month getMonth() {
        return month;
    }

    // todo: upgrade this method LOL
    public void updateMonth(String str) {
        MonthOfYear newMonth = MonthOfYear.valueOf(str);
        month = new Month(newMonth, 2019);
    }
}