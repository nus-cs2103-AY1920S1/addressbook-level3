package seedu.planner.logic.commands.schedulecommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;

/**
 * Unschedules an activity from the day by time.
 */
public class UnscheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unschedule";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(COMMAND_WORD,
            "Unschedules all instances of an activity on a certain day.",
            COMMAND_WORD
            + " ACTIVITY_INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY\n",
            COMMAND_WORD
            + " 2 "
            + PREFIX_DAY + "3");

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            "<INDEX>",
            Arrays.asList(PREFIX_DAY.toString()),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    );

    public static final String MESSAGE_UNSCHEDULE_SUCCESS = "Activity %d unscheduled from Day %d";

    private Index activityIndexToUnschedule;
    private final Index dayIndex;
    private final ActivityWithTime activityToUnschedule;
    private final boolean isUndoRedo;

    /**
     * @param activityIndex of the {@code ActivityWithTime} in the {@code Day} to edit
     * @param dayIndex      of the {@code Day} in the {@code Itinerary} to edit
     */
    public UnscheduleCommand(Index activityIndex, Index dayIndex, boolean isUndoRedo) {
        requireAllNonNull(activityIndex, dayIndex);
        this.activityIndexToUnschedule = activityIndex;
        this.dayIndex = dayIndex;
        this.activityToUnschedule = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo ScheduleEvent
    public UnscheduleCommand(ActivityWithTime activityToUnschedule, Index dayIndex) {
        requireAllNonNull(activityToUnschedule, dayIndex);
        this.activityToUnschedule = activityToUnschedule;
        this.dayIndex = dayIndex;
        this.activityIndexToUnschedule = null;
        this.isUndoRedo = true;
    }

    public Index getActivityIndexToUnschedule() {
        return activityIndexToUnschedule;
    }

    public Index getDayIndex() {
        return dayIndex;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Day> lastShownDays = model.getFilteredItinerary();
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        List<ActivityWithTime> activitiesInDay = dayToEdit.getListOfActivityWithTime();

        if (activityIndexToUnschedule == null) {
            activityIndexToUnschedule = dayToEdit.getIndex(activityToUnschedule);
        }

        if (activityIndexToUnschedule.getZeroBased() >= activitiesInDay.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_WITH_TIME_INDEX);
        }

        if (!isUndoRedo) {
            //Not due to undo method of ScheduleEvent or redo method of UnscheduleEvent
            updateEventStack(this, model);
        }
        model.unscheduleActivity(dayToEdit, activityIndexToUnschedule);
        model.updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);

        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_SUCCESS, activityIndexToUnschedule.getOneBased(),
                dayIndex.getOneBased()), new UiFocus[]{UiFocus.AGENDA});
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnscheduleCommand // instanceof handles nulls
                && this.dayIndex.equals(((UnscheduleCommand) other).dayIndex)
                && this.activityIndexToUnschedule.equals(((
                UnscheduleCommand) other).activityIndexToUnschedule));
    }
}
