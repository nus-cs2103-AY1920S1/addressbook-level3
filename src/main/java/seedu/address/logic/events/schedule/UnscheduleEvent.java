package seedu.address.logic.events.schedule;

import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.events.Event;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.model.Model;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;

/**
 * An event representing a 'unschedule' command.
 */
public class UnscheduleEvent implements Event {
    private final Index activityIndex;
    private final LocalTime startTime;
    private final Index dayIndex;

    public UnscheduleEvent(Index activityIndex, Index dayIndex, Model model) throws EventException {
        this.activityIndex = activityIndex;
        this.dayIndex = dayIndex;
        this.startTime = generateActivityStartTime(model);
    }

    public UndoableCommand undo() {
        return new ScheduleCommand(activityIndex, startTime, dayIndex);
    }

    public UndoableCommand redo() {
        return new UnscheduleCommand(activityIndex, dayIndex);
    }

    /**
     * A method to obtain the start time of the activity to be unscheduled from a particular day.
     * @param model Current model of the application
     * @return the start time of the activity to be unscheduled.
     * @throws EventException
     */
    private LocalTime generateActivityStartTime(Model model) throws EventException {
        List<Day> lastShownDays = model.getFilteredItinerary();
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        List<ActivityWithTime> activitiesInDay = dayToEdit.getListOfActivityWithTime();

        if (activityIndex.getZeroBased() >= activitiesInDay.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        ActivityWithTime activityToUnschedule = activitiesInDay.get(activityIndex.getZeroBased());
        return activityToUnschedule.getStartTime();
    }

}
