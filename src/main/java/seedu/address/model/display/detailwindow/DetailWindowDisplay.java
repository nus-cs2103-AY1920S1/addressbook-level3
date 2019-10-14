package seedu.address.model.display.detailwindow;

import java.util.ArrayList;

/**
 * Main window display model.
 */
public class DetailWindowDisplay {

    private ArrayList<WeekSchedule> weekSchedules;
    private DetailWindowDisplayType detailWindowDisplayType;

    public DetailWindowDisplay(ArrayList<WeekSchedule> weekSchedules, DetailWindowDisplayType detailWindowDisplayType) {
        this.detailWindowDisplayType = detailWindowDisplayType;
        this.weekSchedules = weekSchedules;
    }

    public DetailWindowDisplay() {
        this.weekSchedules = new ArrayList<>();
        this.detailWindowDisplayType = DetailWindowDisplayType.EMPTY;
    }

    public DetailWindowDisplayType getDetailWindowDisplayType() {
        return detailWindowDisplayType;
    }

    public ArrayList<WeekSchedule> getWeekSchedules() {
        return weekSchedules;
    }
}
