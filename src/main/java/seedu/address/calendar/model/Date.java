package seedu.address.calendar.model;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

public class Date {
    private Day day;
    private MonthOfYear month;
    private Year year;

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

    @Override
    public String toString() {
        return String.format("%s %s %s", day, month, year);
    }
}
