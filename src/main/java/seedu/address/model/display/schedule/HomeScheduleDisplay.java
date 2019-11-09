package seedu.address.model.display.schedule;

import java.util.ArrayList;

import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

public class HomeScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleWindowDisplayType state = ScheduleWindowDisplayType.HOME;

    public HomeScheduleDisplay(ArrayList<PersonSchedule> personSchedules) {
        super(personSchedules);
    }

    @Override
    public ScheduleWindowDisplayType getState() {
        return state;
    }

}
