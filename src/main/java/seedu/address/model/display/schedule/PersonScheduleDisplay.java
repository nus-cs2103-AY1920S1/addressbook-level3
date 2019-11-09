package seedu.address.model.display.schedule;

import java.util.ArrayList;

import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

public class PersonScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleWindowDisplayType state = ScheduleWindowDisplayType.PERSON;

    public PersonScheduleDisplay(ArrayList<PersonSchedule> personSchedules) {
        super(personSchedules);
    }

    @Override
    public ScheduleWindowDisplayType getState() {
        return state;
    }
}
