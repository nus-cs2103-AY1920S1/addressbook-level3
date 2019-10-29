package seedu.address.model.display.detailwindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * Main window display model.
 */
public class DetailWindowDisplay {

    private GroupDisplay groupDisplay;

    private ArrayList<PersonSchedule> personSchedules;
    private FreeSchedule freeSchedule;

    private DetailWindowDisplayType detailWindowDisplayType;

    public DetailWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                               DetailWindowDisplayType detailWindowDisplayType) {
        this(personSchedules, null, null, detailWindowDisplayType);
    }

    public DetailWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                               FreeSchedule freeSchedule,
                               GroupDisplay groupDisplay,
                               DetailWindowDisplayType detailWindowDisplayType) {

        this.personSchedules = personSchedules;
        this.freeSchedule = freeSchedule;
        this.groupDisplay = groupDisplay;
        this.detailWindowDisplayType = detailWindowDisplayType;
    }

    public DetailWindowDisplay() {
        this.personSchedules = new ArrayList<>();
        this.detailWindowDisplayType = DetailWindowDisplayType.DEFAULT;
        this.groupDisplay = null;

        this.freeSchedule = null;
    }

    public DetailWindowDisplay(DetailWindowDisplayType type) {
        this.personSchedules = new ArrayList<>();
        this.detailWindowDisplayType = type;
        this.groupDisplay = null;

        this.freeSchedule = null;
    }

    public DetailWindowDisplayType getDetailWindowDisplayType() {
        return detailWindowDisplayType;
    }

    public ArrayList<PersonSchedule> getPersonSchedules() {
        return personSchedules;
    }

    public GroupDisplay getGroupDisplay() {
        return groupDisplay;
    }

    public FreeSchedule getFreeSchedule() {
        return freeSchedule;
    }

    /**
     * For debugging purposes only.
     */
    public String freeScheduleToString() {
        String s = "";

        HashMap<DayOfWeek, ArrayList<FreeTimeslot>> free = freeSchedule.getFreeSchedule();
        for (int i = 0; i < 7; i++) {
            ArrayList<FreeTimeslot> freeTimeslots = free.get(DayOfWeek.of(i + 1));
            s += DayOfWeek.of(i + 1).toString() + "\n";
            for (int j = 0; j < freeTimeslots.size(); j++) {
                s += freeTimeslots.get(j).toString() + " === ";
            }
            s += "\n";

        }
        return s;
    }

}
