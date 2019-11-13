package seedu.planner.logic.events.schedule;

import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
//@@author OneArmyj
/**
 * An event representing a 'schedule' command.
 */
public class ScheduleEvent implements Event {
    private final ActivityWithTime activityScheduled;
    private final LocalTime startTime;
    private final Index dayIndex;
    private final Index activityIndex;
    private final Logger logger = LogsCenter.getLogger(ScheduleEvent.class);

    public ScheduleEvent(Index activityIndex, LocalTime startTime, Index dayIndex, Model model) {
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.dayIndex = dayIndex;
        activityScheduled = generateActivityScheduled(model);
    }

    /**
     * A method to undo the effects of ScheduleCommand.
     * @return returns UnscheduleCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new UnscheduleCommand(activityScheduled, dayIndex);
    }

    /**
     * A method to redo the effects of ScheduleCommand
     * @return returns ScheduleCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new ScheduleCommand(activityIndex, startTime, dayIndex, true);
    }

    /**
     * A method to generate the ActivityWithTime that is scheduled to the day.
     * @param model Current model of the application.
     * @return The activity that is scheduled.
     */
    private ActivityWithTime generateActivityScheduled(Model model) {
        List<Activity> lastShownActivities = model.getFilteredActivityList();
        Activity activityToSchedule = lastShownActivities.get(activityIndex.getZeroBased());

        return new ActivityWithTime(activityToSchedule, startTime.atDate(model.getStartDate()
                .plusDays(dayIndex.getZeroBased())));
    }

    @Override
    public String toString() {
        return "SCHEDULE EVENT";
    }
}
