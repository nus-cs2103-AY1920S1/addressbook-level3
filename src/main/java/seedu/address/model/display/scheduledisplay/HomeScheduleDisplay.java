package seedu.address.model.display.scheduledisplay;

import java.util.ArrayList;

import seedu.address.model.display.timeslots.PersonSchedule;

/**
 * Schedule of the User on the Home page.
 */
public class HomeScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleState state = ScheduleState.HOME;

    public HomeScheduleDisplay(ArrayList<PersonSchedule> personSchedules) {
        super(personSchedules);
    }

    @Override
    public ScheduleState getState() {
        return state;
    }

}
