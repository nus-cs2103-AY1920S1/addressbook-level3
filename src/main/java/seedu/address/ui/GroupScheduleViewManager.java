package seedu.address.ui;

import seedu.address.model.display.detailwindow.FreeSchedule;
import seedu.address.model.display.detailwindow.MonthSchedule;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.schedule.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;

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
     * Method to initialise or reinitialise ScheduleView object to be displayed in the UI.
     */
    private void initScheduleView() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateToShow = currentDate.plusDays(weekNumber * 7);
        ArrayList<WeekSchedule> weekSchedules = MonthSchedule.getWeekSchedulesForWeek(monthSchedules, weekNumber);
        this.scheduleView = new ScheduleView(weekSchedules, colors,
                groupName, freeSchedules.get(weekNumber), dateToShow);
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
