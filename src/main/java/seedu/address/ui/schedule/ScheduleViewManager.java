package seedu.address.ui.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.person.Name;
import seedu.address.ui.schedule.exceptions.InvalidScheduleViewException;

/**
 * Abstract class to control schedule view in the UI.
 */
public abstract class ScheduleViewManager {

    protected static final Logger LOGGER = LogsCenter.getLogger(ScheduleViewManager.class);
    protected ScheduleWindowDisplayType type;
    protected ScheduleView scheduleView;
    protected int weekNumber;
    protected LocalDate currentDate;

    public static ScheduleViewManager getInstanceOf(ScheduleWindowDisplay scheduleWindowDisplay)
            throws InvalidScheduleViewException {
        ScheduleWindowDisplayType displayType = scheduleWindowDisplay.getScheduleWindowDisplayType();

        if (!isValidSchedules(scheduleWindowDisplay.getPersonSchedules())) {
            LOGGER.severe("Schedule given is invalid.");
            throw new InvalidScheduleViewException("The schedule has clashes between events!");
        }

        switch(displayType) {
        case PERSON:
            //There is only 1 schedule in the scheduleWindowDisplay
            if (scheduleWindowDisplay.getPersonSchedules().size() != 1) {
                throw new InvalidScheduleViewException("Error! Multiple schedules in a person.");
            }

            return new IndividualScheduleViewManager(scheduleWindowDisplay.getPersonSchedules().get(0));
        case GROUP:
            return new GroupScheduleViewManager(scheduleWindowDisplay
                    .getPersonSchedules(),
                    scheduleWindowDisplay.getGroupDisplay().getGroupName(),
                    scheduleWindowDisplay.getFreeSchedule());
        default:
            break;
        }
        return null;
    }

    /**
     * Checks to see if the given schedules are valid.
     * @param personSchedules List of schedules given.
     * @return boolean.
     */
    private static boolean isValidSchedules(List<PersonSchedule> personSchedules) {
        boolean isValid = true;
        for (PersonSchedule personSchedule : personSchedules) {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j <= 7; j++) {
                    ArrayList<PersonTimeslot> timeSlots = personSchedule
                            .getScheduleDisplay().getScheduleForWeek(i).get(DayOfWeek.of(j));
                    LocalTime curr = LocalTime.of(ScheduleView.START_TIME, 0);
                    for (PersonTimeslot timeSlot : timeSlots) {
                        if (timeSlot.getStartTime().isBefore(curr)) {
                            isValid = false;
                            break;
                        }
                        curr = timeSlot.getEndTime();
                    }
                }
            }
        }
        return isValid;
    }

    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return type;
    };

    public abstract ScheduleView getScheduleView();
    public abstract void scrollNext();
    public abstract void toggleNext();
    public abstract void filterPersonsFromSchedule(List<Name> persons);
    public abstract ScheduleView getScheduleViewCopy();
}
