package seedu.address.ui.stub;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.CalendarDate;

class TimelineWeekViewTest {

    @Test
    void addWeekTest() {
        assertDoesNotThrow(() -> {
            TimelineWeekViewStub timelineWeekView =
                    new TimelineWeekViewStub(CalendarDate.fromDayMonthYearString("11/11/2019"));
            CalendarDate[] calendarDates = {
                    CalendarDate.fromDayMonthYearString("11/11/2019"),
                    CalendarDate.fromDayMonthYearString("12/11/2019"),
                    CalendarDate.fromDayMonthYearString("13/11/2019"),
                    CalendarDate.fromDayMonthYearString("14/11/2019"),
                    CalendarDate.fromDayMonthYearString("15/11/2019"),
                    CalendarDate.fromDayMonthYearString("16/11/2019"),
                    CalendarDate.fromDayMonthYearString("17/11/2019")
            };
            CalendarDate[] timelineCalendarDates = timelineWeekView.addWeek();

            assertEquals(timelineCalendarDates.length, 7);

            for (int i = 0; i < timelineCalendarDates.length; i++) {
                assertEquals(calendarDates[i], timelineCalendarDates[i]);
            }

        });
    }

    @Test
    void getWeekTest() {
        assertDoesNotThrow(() -> {
            TimelineWeekViewStub timelineWeekView = new TimelineWeekViewStub();
            assertEquals(
                timelineWeekView.getWeek(CalendarDate.fromDayMonthYearString("11/11/2019")),
                3);
        });
    }

    @Test
    void getEnglishWeekDateTest() {
        TimelineWeekViewStub timelineWeekView = new TimelineWeekViewStub();
        assertEquals(
            timelineWeekView.getEnglishWeekDate(3, 11, 2019),
            "Week 3 of November 2019");
    }
}
