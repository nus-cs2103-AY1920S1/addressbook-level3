package seedu.address.ui;

import seedu.address.model.display.detailwindow.FreeSchedule;
import seedu.address.model.display.detailwindow.MonthSchedule;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.group.GroupName;

import java.util.ArrayList;

public class GroupScheduleViewManager {
    private ArrayList<MonthSchedule> monthSchedules;
    private ArrayList<String> colors;
    private GroupName groupName;
    private ArrayList<FreeSchedule> freeSchedules;

    public GroupScheduleViewManager(ArrayList<MonthSchedule> monthSchedules, ArrayList<String> colors,
                                    GroupName groupName, ArrayList<FreeSchedule> freeSchedules) {
        this.monthSchedules = monthSchedules;
        this.colors = colors;
        this.groupName = groupName;
        this.freeSchedules = freeSchedules;
    }

    public ScheduleView getScheduleView(int weekNumber) {
        ArrayList<WeekSchedule> weekSchedules = MonthSchedule.getWeekSchedulesForWeek(monthSchedules, weekNumber);
        ScheduleView scheduleView = new ScheduleView(weekSchedules, colors, groupName, freeSchedules.get(weekNumber));
        return scheduleView;
    }
}
