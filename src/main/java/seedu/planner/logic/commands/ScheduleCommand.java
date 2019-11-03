package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.CommandUtil;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;

/**
 * Schedules an activity to a day.
 * @@author oscarsu97
 */
public class ScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(COMMAND_WORD + " ",
            ": Schedule the activity identified "
            + "by the index number used in the displayed activity list "
            + "to a day.\n",
            COMMAND_WORD + " "
            + "ACTIVITY_INDEX (must be a positive integer) "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_DAY + "DAY_INDEX ",
            COMMAND_WORD + " 1 "
            + PREFIX_START_TIME + "1100 "
            + PREFIX_DAY + "2 ");

    public static final String MESSAGE_SCHEDULE_ACTIVITY_SUCCESS = "Activity scheduled to day %d";
    public static final String MESSAGE_END_TIME_EXCEEDS_LAST_DAY = "Activity will end after the end of the itinerary.";

    private final Index activityIndex;
    private final LocalTime startTime;
    private final Index dayIndex;

    /**
     * Creates an ScheduleCommand to schedule the specified {@Activity} into the day.
     */
    public ScheduleCommand(Index activityIndex, LocalTime startTime, Index dayIndex) {
        requireAllNonNull(activityIndex, startTime, dayIndex);
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.dayIndex = dayIndex;
    }

    public Index getActivityIndex() {
        return activityIndex;
    }

    public Index getDayIndex() {
        return dayIndex;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownDays = model.getFilteredItinerary();
        List<Activity> lastShownActivities = model.getFilteredActivityList();

        if (dayIndex.getZeroBased() >= lastShownDays.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }
        if (activityIndex.getZeroBased() >= lastShownActivities.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        Activity activityToSchedule = lastShownActivities.get(activityIndex.getZeroBased());
        LocalDateTime lastDateTimeOfItinerary = model.getLastDateTime();
        LocalDateTime endDateTimeOfActivity = CommandUtil.calculateEndDateTime(model.getStartDate(),
                dayIndex, startTime, activityToSchedule.getDuration());
        if (endDateTimeOfActivity.isAfter(lastDateTimeOfItinerary)) {
            throw new CommandException(MESSAGE_END_TIME_EXCEEDS_LAST_DAY);
        }

        ActivityWithTime activityWithTimeToAdd = new ActivityWithTime(activityToSchedule,
                startTime.atDate(model.getStartDate().plusDays(dayIndex.getZeroBased())));

        model.scheduleActivity(dayToEdit, activityWithTimeToAdd);

        model.updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(String.format(MESSAGE_SCHEDULE_ACTIVITY_SUCCESS, dayIndex.getOneBased()),
                new UiFocus[]{UiFocus.AGENDA});
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && this.activityIndex.equals(((ScheduleCommand) other).activityIndex)
                && this.startTime.equals((((ScheduleCommand) other).startTime)));
    }
}
