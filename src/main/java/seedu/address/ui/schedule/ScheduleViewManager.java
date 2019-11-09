package seedu.address.ui.schedule;

import java.util.List;

import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.person.Name;

/**
 * Interface to control schedule view in the UI.
 */
public interface ScheduleViewManager {
    public static ScheduleViewManager getInstanceOf(ScheduleWindowDisplay scheduleWindowDisplay) {
        ScheduleWindowDisplayType displayType = scheduleWindowDisplay.getScheduleWindowDisplayType();
        switch(displayType) {
        case PERSON:
            //There is only 1 schedule in the scheduleWindowDisplay
            return new IndividualScheduleViewManager(scheduleWindowDisplay.getPersonSchedules()
                    .get(0));
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
    public ScheduleView getScheduleView();
    public void scrollNext();
    public void toggleNext();
    public void filterPersonsFromSchedule(List<Name> persons);
    public ScheduleWindowDisplayType getScheduleWindowDisplayType();
    public ScheduleView getScheduleViewCopy();
}
