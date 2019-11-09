package seedu.address.ui.schedule;

import java.time.LocalDate;
import java.util.List;

import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.person.Name;

/**
 * Class to handle schedule views of individuals. Schedule of individuals do not show free time.
 */
public class IndividualScheduleViewManager implements ScheduleViewManager {
    private PersonSchedule personSchedule;
    private ScheduleView scheduleView;
    private int weekNumber;
    private LocalDate currentDate;

    public IndividualScheduleViewManager(PersonSchedule personSchedule) {
        this.personSchedule = personSchedule;
        this.weekNumber = 0;
        this.currentDate = LocalDate.now();
        initScheduleView();
    }

    /**
     * Method to initialise or reinitialise individual ScheduleView object to be displayed in the UI.
     * Individual schedules do not show free time.
     */
    private void initScheduleView() {
        LocalDate dateToShow = currentDate.plusDays(weekNumber * 7);
        this.scheduleView = new ScheduleView(List.of(personSchedule
                .getScheduleDisplay().get(weekNumber)),
                personSchedule.getPersonDisplay().getName().fullName, dateToShow);
        this.scheduleView.generateSchedule();
    }

    @Override
    public ScheduleView getScheduleView() {
        return this.scheduleView;
    }

    @Override
    public void scrollNext() {
        this.scheduleView.scrollNext();
    }

    @Override
    public ScheduleView getScheduleViewCopy() {
        ScheduleView copy = new ScheduleView(List.of(personSchedule
                .getScheduleDisplay().get(weekNumber)),
                personSchedule.getPersonDisplay().getName().fullName, currentDate.plusDays(7 * weekNumber));
        copy.generateSchedule();
        return copy;
    }

    @Override
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }

    @Override
    public void filterPersonsFromSchedule(List<Name> persons) {
        //Cannot filter persons from individual schedule.
    }

    @Override
    public ScheduleState getScheduleWindowDisplayType() {
        return ScheduleState.PERSON;
    }
}
