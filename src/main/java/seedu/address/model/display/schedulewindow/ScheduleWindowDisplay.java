package seedu.address.model.display.schedulewindow;

import java.util.ArrayList;

import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * Main window display model.
 */
public class ScheduleWindowDisplay {

    private ArrayList<MonthSchedule> monthSchedules;
    private ScheduleWindowDisplayType scheduleWindowDisplayType;
    private GroupDisplay groupDisplay;

    private ArrayList<FreeSchedule> freeSchedules;

    /**
     * Constructor to display details for groups.
     * @param monthSchedules Month schedule of every member of the group.
     * @param scheduleWindowDisplayType The type to be displayed.
     * @param groupDisplay Group information to be displayed.
     */
    public ScheduleWindowDisplay(ArrayList<MonthSchedule> monthSchedules, ScheduleWindowDisplayType scheduleWindowDisplayType,
                                 GroupDisplay groupDisplay) {
        this.scheduleWindowDisplayType = scheduleWindowDisplayType;
        this.monthSchedules = monthSchedules;
        this.groupDisplay = groupDisplay;

        this.freeSchedules = new ArrayList<>();
        for (int i = 0 ; i < monthSchedules.size(); i++) {
            ArrayList<WeekSchedule> weekSchedules = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                weekSchedules.add(monthSchedules.get(i).getWeekScheduleOf(j));
            }
            freeSchedules.add(new FreeSchedule(weekSchedules));
        }
    }

    /**
     * Constructor for viewing individual information
     * @param monthSchedules Contains 1 WeekSchedule Object.
     * @param scheduleWindowDisplayType Most likely to be PERSON.
     */
    public ScheduleWindowDisplay(ArrayList<MonthSchedule> monthSchedules, ScheduleWindowDisplayType scheduleWindowDisplayType) {
        this.scheduleWindowDisplayType = scheduleWindowDisplayType;
        this.monthSchedules = monthSchedules;
        this.groupDisplay = null;

        this.freeSchedules = new ArrayList<>();
        for (int i = 0 ; i < monthSchedules.size(); i++) {
            ArrayList<WeekSchedule> weekSchedules = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                weekSchedules.add(monthSchedules.get(i).getWeekScheduleOf(j));
            }
            freeSchedules.add(new FreeSchedule(weekSchedules));
        }
    }

    public ScheduleWindowDisplay() {
        this.monthSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = ScheduleWindowDisplayType.DEFAULT;
        this.groupDisplay = null;

        this.freeSchedules = null;
    }

    public ScheduleWindowDisplay(ScheduleWindowDisplayType type) {
        this.monthSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = type;
        this.groupDisplay = null;

        this.freeSchedules = null;
    }

    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return scheduleWindowDisplayType;
    }

    public ArrayList<MonthSchedule> getMonthSchedules() {
        return monthSchedules;
    }

    public GroupDisplay getGroupDisplay() {
        return groupDisplay;
    }

    public ArrayList<FreeSchedule> getFreeSchedules() {
        return freeSchedules;
    }
}
