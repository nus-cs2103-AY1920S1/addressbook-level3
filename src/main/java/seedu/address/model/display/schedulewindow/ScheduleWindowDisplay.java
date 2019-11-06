package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.detailwindow.PersonSchedule;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.InvalidTimeslotException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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

    public FreeTimeslot getFreeTimeslot(int week, int id) throws InvalidTimeslotException {
        return freeScheduleWeeks.get(week).getFreeTimeslot(id);
    }

    public PersonTimeslot getPersonTimeslot(int week, Name name, int day, int id)
            throws PersonNotFoundException, InvalidTimeslotException {

        // week must be 0 - 3
        //ArrayList<PersonSchedule> personSchedules = this.personSchedules.get(week);

        // find the schedule of the person with the given name
        PersonSchedule selectedPersonSchedule = null;
        for (int i = 0; i < personSchedules.size(); i++) {
            if (personSchedules.get(i).getPersonDisplay().getName().equals(name)) {
                selectedPersonSchedule = personSchedules.get(i);
            }
        }

        if (selectedPersonSchedule == null) {
            throw new PersonNotFoundException();
        }

        // select the day of week
        ArrayList<PersonTimeslot> personTimeslots = selectedPersonSchedule.getScheduleDisplay()
                .getScheduleForWeek(week).get(DayOfWeek.of(day));

        // select specific timeslot
        try {
            PersonTimeslot selectedPersonTimeslot = personTimeslots.get(id);
            return selectedPersonTimeslot;
        } catch (IndexOutOfBoundsException iobe) {
            throw new InvalidTimeslotException();
        }

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
