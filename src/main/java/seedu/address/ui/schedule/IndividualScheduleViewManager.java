package seedu.address.ui.schedule;

import java.time.LocalDate;
import java.util.List;

import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.PersonDisplay;

/**
 * Class to handle schedule views of individuals. Schedule of individuals do not show free time.
 */
public class IndividualScheduleViewManager implements ScheduleViewManager {
    private MonthSchedule monthSchedule;
    private String color;
    private ScheduleView scheduleView;
    private PersonDisplay personDisplay;
    private int weekNumber;
    private LocalDate currentDate;

    public IndividualScheduleViewManager(MonthSchedule monthSchedule,
                                         PersonDisplay personDisplay, String color) {
        this.personDisplay = personDisplay;
        this.monthSchedule = monthSchedule;
        this.color = color;
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
        this.scheduleView = new ScheduleView(List.of(monthSchedule.getScheduleForWeek(weekNumber)),
                List.of(color), personDisplay.getName().fullName, dateToShow);
        this.scheduleView.generatePersonSchedule();
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
        ScheduleView copy = new ScheduleView(List.of(monthSchedule.getScheduleForWeek(weekNumber)),
                List.of(color), personDisplay.getName().fullName, currentDate.plusDays(7 * weekNumber));
        copy.generateSchedule();
        return copy;
    }

    @Override
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }

    @Override
    public List<String> getColors() {
        return List.of(color);
    }

    @Override
    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return ScheduleWindowDisplayType.PERSON;
    }
}
