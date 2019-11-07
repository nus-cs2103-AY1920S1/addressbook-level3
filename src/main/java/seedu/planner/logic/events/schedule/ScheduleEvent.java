package seedu.planner.logic.events.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.commands.util.CommandUtil;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;


import static seedu.planner.logic.commands.schedulecommand.ScheduleCommand.MESSAGE_END_TIME_EXCEEDS_LAST_DAY;

/**
 * An event representing a 'schedule' command.
 */
public class ScheduleEvent implements Event {
    private final ActivityWithTime activityScheduled;
    private final LocalTime startTime;
    private final Index dayIndex;
    private final Index activityIndex;

    public ScheduleEvent(Index activityIndex, LocalTime startTime, Index dayIndex, Model model) throws EventException {
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.dayIndex = dayIndex;
        activityScheduled = generateActivityScheduled(model);
    }

    public UndoableCommand undo() {
        return new UnscheduleCommand(activityScheduled, dayIndex);
    }

    public UndoableCommand redo() {
        return new ScheduleCommand(activityIndex, startTime, dayIndex);
    }

    private ActivityWithTime generateActivityScheduled(Model model) throws EventException {
        List<Day> lastShownDays = model.getFilteredItinerary();
        List<Activity> lastShownActivities = model.getFilteredActivityList();

        if (dayIndex.getZeroBased() >= lastShownDays.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }
        if (activityIndex.getZeroBased() >= lastShownActivities.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToSchedule = lastShownActivities.get(activityIndex.getZeroBased());
        LocalDateTime lastDateTimeOfItinerary = model.getLastDateTime();
        LocalDateTime endDateTimeOfActivity = CommandUtil.calculateEndDateTime(model.getStartDate(),
                dayIndex, startTime, activityToSchedule.getDuration());
        if (endDateTimeOfActivity.isAfter(lastDateTimeOfItinerary)) {
            throw new EventException(MESSAGE_END_TIME_EXCEEDS_LAST_DAY);
        }

        return new ActivityWithTime(activityToSchedule, startTime.atDate(model.getStartDate().
                plusDays(dayIndex.getZeroBased())));

    }
}
