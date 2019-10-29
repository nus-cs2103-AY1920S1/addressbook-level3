package seedu.address.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.detailwindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.group.GroupName;

/**
 * Class to handle schedule view of groups
 */
public class GroupScheduleViewManager implements ScheduleViewManager {

    private ArrayList<PersonSchedule> monthSchedules;
    private ArrayList<String> colors;
    private GroupName groupName;
    private FreeSchedule freeSchedule;
    private int weekNumber;
    private ScheduleView scheduleView;

    public GroupScheduleViewManager(ArrayList<PersonSchedule> monthSchedules, ArrayList<String> colors,
                                    GroupName groupName, FreeSchedule freeSchedule) {
        this.monthSchedules = monthSchedules;
        this.colors = colors;
        this.groupName = groupName;
        this.freeSchedule = freeSchedule;
        this.weekNumber = 0;
        initScheduleView();
    }

    /**
     * Method to initialise or reinitialise group ScheduleView object to be displayed in the UI.
     * Group schedules show free time.
     */
    private void initScheduleView() {
        LocalDate currentDate = LocalDate.now();
        //weekNumber * 7
        LocalDate dateToShow = currentDate.plusDays(0);
        this.scheduleView = new ScheduleView(monthSchedules, colors,
                groupName.toString(), dateToShow);
        this.scheduleView.setFreeTime(freeSchedule);
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
    public ScheduleView getScheduleView() {
        return this.scheduleView;
    }

    @Override
    public void scrollNext() {
        this.scheduleView.scrollNext();
    }
}
