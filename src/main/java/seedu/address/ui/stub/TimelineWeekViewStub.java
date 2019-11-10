package seedu.address.ui.stub;

import java.text.DateFormatSymbols;

import seedu.address.model.CalendarDate;
import seedu.address.ui.panel.calendar.TimelineWeekView;

//@@author Kyzure
/**
 * Represents a stub for testing purpose for TimelineWeekView.
 *
 * @see TimelineWeekView
 */
class TimelineWeekViewStub {

    private CalendarDate calendarDate;

    public TimelineWeekViewStub() {}

    public TimelineWeekViewStub(CalendarDate calendarDate) {
        this.calendarDate = calendarDate;
    }

    /**
     * Returns the list of dates of the given week. Main purpose is for testing.
     *
     * @return A list of dates.
     */
    public CalendarDate[] addWeek() {
        CalendarDate[] calendarDates = new CalendarDate[7];
        int weekIndex = calendarDate.getWeekIndex();
        while (weekIndex > 1) {
            calendarDate = calendarDate.previousDay();
            weekIndex--;
        }
        for (int i = 0; i < calendarDates.length; i++) {
            calendarDates[i] = calendarDate;
            calendarDate = calendarDate.nextDay();
        }
        return calendarDates;
    }

    public Integer getWeek(CalendarDate calendarDate) {
        CalendarDate currentDate = calendarDate.firstDayOfTheMonth();
        Integer weekIndex = currentDate.getWeekIndex();
        currentDate = currentDate.previousDays(weekIndex - 1);
        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                if (calendarDate.equals(currentDate)) {
                    return week + 1;
                }
                currentDate = currentDate.nextDay();
            }
        }
        // Not suppose to reach here.
        return null;
    }

    public String getEnglishWeekDate(Integer week, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return "Week " + week + " of " + monthStr + " " + year;
    }
}
