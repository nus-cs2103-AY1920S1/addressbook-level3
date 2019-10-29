package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.detailwindow.PersonSchedule;
import seedu.address.model.display.sidepanel.GroupDisplay;

/**
 * Main window display model.
 */
public class ScheduleWindowDisplay {

    private ArrayList<PersonSchedule> personSchedules;
    private ScheduleWindowDisplayType scheduleWindowDisplayType;
    private GroupDisplay groupDisplay;

    private FreeSchedule freeSchedule;

    public ScheduleWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                                 ScheduleWindowDisplayType detailWindowDisplayType) {
        this(personSchedules, null, null, detailWindowDisplayType);
    }

    public ScheduleWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                               FreeSchedule freeSchedule, GroupDisplay groupDisplay,
                                 ScheduleWindowDisplayType scheduleWindowDisplayType) {

        this.personSchedules = personSchedules;
        this.freeSchedule = freeSchedule;
        this.groupDisplay = groupDisplay;
        this.scheduleWindowDisplayType = scheduleWindowDisplayType;
    }

    public ScheduleWindowDisplay() {
        this.personSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = ScheduleWindowDisplayType.DEFAULT;
        this.groupDisplay = null;

        this.freeSchedule = null;
    }

    public ScheduleWindowDisplay(ScheduleWindowDisplayType type) {
        this.personSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = type;
        this.groupDisplay = null;

        this.freeSchedule = null;
    }

    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return scheduleWindowDisplayType;
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
                s += " === " + freeTimeslots.get(j).toString();
            }
            s += "\n";

        }
        return s;
    }
}
