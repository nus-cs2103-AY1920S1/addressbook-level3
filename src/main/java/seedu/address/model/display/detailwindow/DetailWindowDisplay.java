package seedu.address.model.display.detailwindow;

import java.util.ArrayList;

import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * Main window display model.
 */
public class DetailWindowDisplay {

    private ArrayList<WeekSchedule> weekSchedules;
    private DetailWindowDisplayType detailWindowDisplayType;
    private GroupDisplay groupDisplay;

    public DetailWindowDisplay(ArrayList<WeekSchedule> weekSchedules, DetailWindowDisplayType detailWindowDisplayType,
                               GroupDisplay groupDisplay) {
        this.detailWindowDisplayType = detailWindowDisplayType;
        this.weekSchedules = weekSchedules;
        this.groupDisplay = groupDisplay;
    }

    public DetailWindowDisplay(ArrayList<WeekSchedule> weekSchedules, DetailWindowDisplayType detailWindowDisplayType) {
        this.detailWindowDisplayType = detailWindowDisplayType;
        this.weekSchedules = weekSchedules;
        this.groupDisplay = null;
    }

    public DetailWindowDisplay() {
        this.weekSchedules = new ArrayList<>();
        this.detailWindowDisplayType = DetailWindowDisplayType.EMPTY;
        this.groupDisplay = null;
    }

    public DetailWindowDisplayType getDetailWindowDisplayType() {
        return detailWindowDisplayType;
    }

    public ArrayList<WeekSchedule> getWeekSchedules() {
        return weekSchedules;
    }

    public GroupDisplay getGroupDisplay() {
        return this.groupDisplay;
    }
}
