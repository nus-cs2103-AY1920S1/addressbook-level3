package seedu.address.model.display.scheduledisplay;

import java.util.ArrayList;

import seedu.address.model.display.timeslots.PersonSchedule;

/**
 * Schedule of a Person.
 */
public class PersonScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleState state = ScheduleState.PERSON;

    public PersonScheduleDisplay(ArrayList<PersonSchedule> personSchedules) {
        super(personSchedules);
    }

    @Override
    public ScheduleState getState() {
        return state;
    }
}
