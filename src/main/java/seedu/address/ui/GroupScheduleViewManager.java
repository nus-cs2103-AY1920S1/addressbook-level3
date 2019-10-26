package seedu.address.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.detailwindow.FreeSchedule;
import seedu.address.model.display.detailwindow.MonthSchedule;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.group.GroupName;

/**
 * Class to handle schedule view of groups
 */
public class GroupScheduleViewManager implements ScheduleViewManager {

    private ArrayList<MonthSchedule> monthSchedules;
    private ArrayList<String> colors;
    private GroupName groupName;
    private ArrayList<FreeSchedule> freeSchedules;
    private int weekNumber;
    private ScheduleView scheduleView;

    public GroupScheduleViewManager(ArrayList<MonthSchedule> monthSchedules, ArrayList<String> colors,
                                    GroupName groupName, ArrayList<FreeSchedule> freeSchedules) {
        this.monthSchedules = monthSchedules;
        this.colors = colors;
        this.groupName = groupName;
        this.freeSchedules = freeSchedules;
        this.weekNumber = 0;
        initScheduleView();
    }

    /**
     * Method to initialise or reinitialise group ScheduleView object to be displayed in the UI.
     * Group schedules show free time.
     */
    private void initScheduleView() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateToShow = currentDate.plusDays(weekNumber * 7);
        List<WeekSchedule> weekSchedules = MonthSchedule.getWeekSchedulesForWeek(monthSchedules, weekNumber);
        this.scheduleView = new ScheduleView(weekSchedules, colors,
                groupName.toString(), dateToShow);
        this.scheduleView.showFreeTime(freeSchedules.get(weekNumber));
    }

    @Override
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }

    @Override
    public ScheduleView getScheduleView() {
        return this.scheduleView;
    }

    @Override
    public void scrollNext() {
        this.scheduleView.scrollNext();
    }
}
