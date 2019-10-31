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

public class UnscheduleCommandEvent implements Event {
    private final Index activityIndex;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Index dayIndex;

    public UnscheduleCommandEvent(Index activityIndex, Index dayIndex, Model model) throws EventException {
        this.activityIndex = activityIndex;
        this.dayIndex = dayIndex;
        this.startTime = generateActivityStartTime(model);
        this.endTime = generateActivityEndTime(model);
    }

    public UndoableCommand undo() {
        return new ScheduleCommand(activityIndex, startTime, endTime, dayIndex);
    }

    public UndoableCommand redo() {
        return new UnscheduleCommand(activityIndex, dayIndex);
    }

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

    private LocalTime generateActivityEndTime(Model model) throws EventException {
        List<Day> lastShownDays = model.getFilteredItinerary();
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        List<ActivityWithTime> activitiesInDay = dayToEdit.getListOfActivityWithTime();

        if (activityIndex.getZeroBased() >= activitiesInDay.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        ActivityWithTime activityToUnschedule = activitiesInDay.get(activityIndex.getZeroBased());
        return activityToUnschedule.getEndTime();
    }
}