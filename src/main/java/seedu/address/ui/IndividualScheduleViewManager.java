package seedu.address.ui;

import java.time.LocalDate;

import seedu.address.model.display.detailwindow.MonthSchedule;

/**
 * Class to handle schedule views of individuals.
 */
public class IndividualScheduleViewManager implements ScheduleViewManager {
    private MonthSchedule monthSchedule;
    private String color;
    private ScheduleView scheduleView;
    private int weekNumber;

    public IndividualScheduleViewManager(MonthSchedule monthSchedule, String color) {
        this.monthSchedule = monthSchedule;
        this.color = color;
        this.weekNumber = 0;
        initScheduleView();
    }

    private void initScheduleView() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateToShow = currentDate.plusDays(weekNumber * 7);
        this.scheduleView = new ScheduleView(monthSchedule.getWeekScheduleOf(weekNumber), dateToShow);
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
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }
}
