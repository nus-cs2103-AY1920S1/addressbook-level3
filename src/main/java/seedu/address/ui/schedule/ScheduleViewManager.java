package seedu.address.ui.schedule;

import java.util.List;

import seedu.address.model.display.scheduledisplay.GroupScheduleDisplay;
import seedu.address.model.display.scheduledisplay.PersonScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;

/**
 * Interface to control schedule view in the UI.
 */
public interface ScheduleViewManager {

    public static ScheduleViewManager getInstanceOf(ScheduleDisplay scheduleDisplay) {
        ScheduleState displayType = scheduleDisplay.getState();
        switch(displayType) {
        case PERSON:
            PersonScheduleDisplay personScheduleDisplay = (PersonScheduleDisplay) scheduleDisplay;
            //There is only 1 schedule in the scheduleWindowDisplay
            return new IndividualScheduleViewManager(personScheduleDisplay.getPersonSchedules()
                    .get(0));
        case GROUP:
            GroupScheduleDisplay groupScheduleDisplay = (GroupScheduleDisplay) scheduleDisplay;
            return new GroupScheduleViewManager(scheduleDisplay
                    .getPersonSchedules(),
                    groupScheduleDisplay.getGroupDisplay().getGroupName(),
                    groupScheduleDisplay.getFreeSchedule());
        default:
            break;
        }
        return null;
    }
    public ScheduleView getScheduleView();
    public void scrollNext();
    public void toggleNext();
    public void filterPersonsFromSchedule(List<Name> persons);
    public ScheduleState getScheduleWindowDisplayType();
    public ScheduleView getScheduleViewCopy();
}
