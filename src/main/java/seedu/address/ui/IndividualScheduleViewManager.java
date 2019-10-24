package seedu.address.ui;

import seedu.address.model.display.detailwindow.MonthSchedule;

public class IndividualScheduleViewManager {
    private MonthSchedule monthSchedule;
    private String color;
    public IndividualScheduleViewManager(MonthSchedule monthSchedule, String color) {
        this.monthSchedule = monthSchedule;
        this.color = color;
    }

    public ScheduleView getScheduleView(int weekNum) {
        return new ScheduleView(monthSchedule.getWeekScheduleOf(weekNum));
    }
}
