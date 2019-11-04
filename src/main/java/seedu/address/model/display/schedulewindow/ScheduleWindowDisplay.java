package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.detailwindow.PersonSchedule;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;

/**
 * Main window display model.
 */
public class ScheduleWindowDisplay {
    private ArrayList<PersonSchedule> personSchedules;
    private ScheduleWindowDisplayType scheduleWindowDisplayType;
    private GroupDisplay groupDisplay;

    private ArrayList<FreeSchedule> freeScheduleWeeks;

    public ScheduleWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                                 ScheduleWindowDisplayType detailWindowDisplayType) {
        this(personSchedules, null, null, detailWindowDisplayType);
    }

    public ScheduleWindowDisplay(ArrayList<PersonSchedule> personSchedules,
                               ArrayList<FreeSchedule> freeScheduleWeeks, GroupDisplay groupDisplay,
                                 ScheduleWindowDisplayType scheduleWindowDisplayType) {

        this.personSchedules = personSchedules;
        this.freeScheduleWeeks = freeScheduleWeeks;
        this.groupDisplay = groupDisplay;
        this.scheduleWindowDisplayType = scheduleWindowDisplayType;
    }

    public ScheduleWindowDisplay() {
        this.personSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = ScheduleWindowDisplayType.DEFAULT;
        this.groupDisplay = null;

        this.freeScheduleWeeks = null;
    }

    public ScheduleWindowDisplay(ScheduleWindowDisplayType type) {
        this.personSchedules = new ArrayList<>();
        this.scheduleWindowDisplayType = type;
        this.groupDisplay = null;

        this.freeScheduleWeeks = null;
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

    public ArrayList<FreeSchedule> getFreeSchedule() {
        return freeScheduleWeeks;
    }

    public ArrayList<PersonDisplay> getPersonDisplays() {
        ArrayList<PersonDisplay> personDisplays = new ArrayList<>();
        for (PersonSchedule p : personSchedules) {
            personDisplays.add(p.getPersonDisplay());
        }
        return personDisplays;
    }

    /**
     * For debugging purposes only.
     */
    public String freeScheduleToString() {
        String s = "";

        //Show just the free schedule for Week 0 only.
        HashMap<DayOfWeek, ArrayList<FreeTimeslot>> free = freeScheduleWeeks.get(0).getFreeSchedule();
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
