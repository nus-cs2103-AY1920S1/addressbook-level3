package seedu.address.model.display.schedule;

import java.util.ArrayList;

import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.person.exceptions.InvalidTimeslotException;

public class GroupScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleWindowDisplayType state = ScheduleWindowDisplayType.GROUP;

    private GroupDisplay groupDisplay;
    private ArrayList<FreeSchedule> freeSchedules;

    public GroupScheduleDisplay(ArrayList<PersonSchedule> personSchedules,
                                ArrayList<FreeSchedule> freeSchedules,
                                GroupDisplay groupDisplay) {
        super(personSchedules);
        this.freeSchedules = freeSchedules;
        this.groupDisplay = groupDisplay;
    }

    public GroupDisplay getGroupDisplay() {
        return groupDisplay;
    }

    public ArrayList<FreeSchedule> getFreeSchedule() {
        return freeSchedules;
    }

    public FreeTimeslot getFreeTimeslot(int week, int id) throws InvalidTimeslotException {
        return freeSchedules.get(week).getFreeTimeslot(id);
    }

    @Override
    public ScheduleWindowDisplayType getState() {
        return state;
    }

}
