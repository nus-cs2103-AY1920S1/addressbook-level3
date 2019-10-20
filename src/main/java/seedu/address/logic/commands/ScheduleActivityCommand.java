package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.day.time.DurationInHalfHour;
import seedu.address.model.day.time.TimeInHalfHour;

/**
 * Schedules an activity to a day.
 */
public class ScheduleActivityCommand extends ScheduleCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Schedule the activity identified "
            + "by the index number used in the displayed activity list "
            + "to a day.\n"
            + "Parameters:"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_DAY + "DAY_INDEX "
            + "Example: " + COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1 "
            + PREFIX_START_TIME + "1100 "
            + PREFIX_DURATION + "30 "
            + PREFIX_DAY + "2 ";

    public static final String MESSAGE_SCHEDULE_ACTIVITY_SUCCESS = "Activity scheduled to day %d";
    public static final String MESSAGE_DUPLICATE_DAY = "This day already exists in the planner.";

    private final Index activityIndex;
    private final TimeInHalfHour startTime;
    private final DurationInHalfHour duration;
    private final Index dayIndex;

    /**
     * Creates an AddActivityCommand to add the specified {@Activity}
     */
    public ScheduleActivityCommand(Index activityIndex, TimeInHalfHour startTime, DurationInHalfHour duration,
                                   Index dayIndex) {
        requireNonNull(activityIndex);
        requireNonNull(startTime);
        requireNonNull(duration);
        requireNonNull(dayIndex);
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.duration = duration;
        this.dayIndex = dayIndex;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownDays = model.getFilteredDayList();
        List<Activity> lastShownActivities = model.getFilteredActivityList();

        if (dayIndex.getZeroBased() >= lastShownDays.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }
        if (activityIndex.getZeroBased() >= lastShownActivities.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        Activity activityToSchedule = lastShownActivities.get(activityIndex.getZeroBased());
        ActivityWithTime activityWithTimeToAdd = new ActivityWithTime(activityToSchedule, startTime, duration);

        Day editedDay = createScheduledActivityDay(dayToEdit, activityWithTimeToAdd);
        List<Day> editedDays = new ArrayList<>(lastShownDays);
        editedDays.set(dayIndex.getZeroBased(), editedDay);

        if (!dayToEdit.isSameDay(editedDay) && model.hasDay(editedDay)) {
            throw new CommandException(MESSAGE_DUPLICATE_DAY);
        }

        model.setDays(editedDays);
        model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(String.format(MESSAGE_SCHEDULE_ACTIVITY_SUCCESS, dayIndex.getOneBased()));
    }

    private Day createScheduledActivityDay(Day dayToEdit, ActivityWithTime toAdd) {
        List<ActivityWithTime> activityList = dayToEdit.getActivitiesWithTime();
        activityList.add(toAdd);
        return new Day(activityList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleActivityCommand // instanceof handles nulls
                && this.activityIndex.equals(((ScheduleActivityCommand) other).activityIndex)
                && this.startTime.equals((((ScheduleActivityCommand) other).startTime))
                && this.duration.equals(((ScheduleActivityCommand) other).duration));
    }
}
