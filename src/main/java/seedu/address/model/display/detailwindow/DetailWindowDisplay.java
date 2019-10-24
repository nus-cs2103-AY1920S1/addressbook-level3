package seedu.address.model.display.detailwindow;

import java.util.ArrayList;

import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * Main window display model.
 */
public class DetailWindowDisplay {

    private ArrayList<MonthSchedule> monthSchedules;
    private DetailWindowDisplayType detailWindowDisplayType;
    private GroupDisplay groupDisplay;

    private ArrayList<FreeSchedule> freeSchedules;

    /**
     * Constructor to display details for groups.
     * @param monthSchedules Month schedule of every member of the group.
     * @param detailWindowDisplayType The type to be displayed.
     * @param groupDisplay Group information to be displayed.
     */
    public DetailWindowDisplay(ArrayList<MonthSchedule> monthSchedules, DetailWindowDisplayType detailWindowDisplayType,
                               GroupDisplay groupDisplay) {
        this.detailWindowDisplayType = detailWindowDisplayType;
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
     * @param detailWindowDisplayType Most likely to be PERSON.
     */
    public DetailWindowDisplay(ArrayList<MonthSchedule> monthSchedules, DetailWindowDisplayType detailWindowDisplayType) {
        this.detailWindowDisplayType = detailWindowDisplayType;
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

    public DetailWindowDisplay() {
        this.monthSchedules = new ArrayList<>();
        this.detailWindowDisplayType = DetailWindowDisplayType.DEFAULT;
        this.groupDisplay = null;

        this.freeSchedules = null;
    }

    public DetailWindowDisplay(DetailWindowDisplayType type) {
        this.monthSchedules = new ArrayList<>();
        this.detailWindowDisplayType = type;
        this.groupDisplay = null;

        this.freeSchedules = null;
    }

    public DetailWindowDisplayType getDetailWindowDisplayType() {
        return detailWindowDisplayType;
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
