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
import seedu.planner.model.day.Day;
//@@author OneArmyj
/**
 * An event representing a 'unschedule' command.
 */
public class UnscheduleEvent implements Event {
    private final Index activityIndex;
    private final LocalTime startTime;
    private final Index dayIndex;
    private final Index activityUnscheduledIndex;
    private final Logger logger = LogsCenter.getLogger(UnscheduleEvent.class);

    public UnscheduleEvent(Index activityIndex, Index dayIndex, Model model) {
        this.activityIndex = activityIndex;
        this.dayIndex = dayIndex;
        this.startTime = generateActivityStartTime(model);
        this.activityUnscheduledIndex = generateActivityUnscheduledIndex(model);
    }

    /**
     * A method to undo the effects of UnscheduleCommand.
     * @return returns ScheduleCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new ScheduleCommand(activityUnscheduledIndex, startTime, dayIndex, true);
    }

    /**
     * A method to redo the effects of UnscheduleCommand
     * @return returns UnscheduleCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new UnscheduleCommand(activityIndex, dayIndex, true);
    }

    /**
     * A method to obtain the start time of the activity to be unscheduled from a particular day.
     * @param model Current model of the application
     * @return the start time of the activity to be unscheduled.
     */
    private LocalTime generateActivityStartTime(Model model) {
        List<Day> lastShownDays = model.getFilteredItinerary();
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        List<ActivityWithTime> activitiesInDay = dayToEdit.getListOfActivityWithTime();

        ActivityWithTime activityToUnschedule = activitiesInDay.get(activityIndex.getZeroBased());
        return activityToUnschedule.getStartDateTime().toLocalTime();
    }

    /**
     * A method to generate the Index of the activity to be unscheduled base on the Activity list in model.
     * @param model Current model of the application
     * @return The index of the activity based on the Activity list in model.
     */
    private Index generateActivityUnscheduledIndex(Model model) {
        List<Day> lastShownDays = model.getFilteredItinerary();
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());

        Activity activityUnscheduled = dayToEdit.getActivityWithIndex(activityIndex).getActivity();

        List<Activity> lastShownActivities = model.getFilteredActivityList();
        return Index.fromZeroBased(lastShownActivities.indexOf(activityUnscheduled));
    }

    @Override
    public String toString() {
        return "UNSCHEDULE EVENT";
    }
}
