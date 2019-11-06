package seedu.address.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.group.GroupName;

/**
 * Class to handle schedule view of groups
 */
public class GroupScheduleViewManager implements ScheduleViewManager {

    private List<MonthSchedule> monthSchedules;
    private ArrayList<String> colors;
    private GroupName groupName;
    private ArrayList<FreeSchedule> freeSchedules;
    private int weekNumber;
    private ScheduleView scheduleView;
    private LocalDate currentDate;

    public GroupScheduleViewManager(List<MonthSchedule> monthSchedules,
                                    ArrayList<String> colors,
                                    GroupName groupName, ArrayList<FreeSchedule> freeSchedules) {
        this.monthSchedules = monthSchedules;
        this.colors = colors;
        this.groupName = groupName;
        this.freeSchedules = freeSchedules;
        this.weekNumber = 0;
        this.currentDate = LocalDate.now();
        initScheduleView();
    }

    /**
     * Method to initialise or reinitialise group ScheduleView object to be displayed in the UI.
     * Group schedules show free time.
     */
    private void initScheduleView() {
        LocalDate dateToShow = currentDate.plusDays(7 * weekNumber);
        this.scheduleView = new ScheduleView(MonthSchedule.getWeekSchedulesOf(monthSchedules, weekNumber), colors,
                groupName.toString(), dateToShow);
        //Required to set the free time schedule first before generating the schedule.
        this.scheduleView.setFreeTime(freeSchedules.get(weekNumber));
        this.scheduleView.generateSchedule();
    }

    @Override
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }

    @Override
    public List<String> getColors() {
        return colors;
    }

    @Override
    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return ScheduleWindowDisplayType.GROUP;
    }

    @Override
    public ScheduleView getScheduleViewCopy() {
        ScheduleView copy = new ScheduleView(MonthSchedule.getWeekSchedulesOf(monthSchedules, weekNumber),
                colors, groupName.toString(), currentDate.plusDays(7 * weekNumber));
        copy.setFreeTime(freeSchedules.get(weekNumber));
        copy.generateSchedule();
        return copy;
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
