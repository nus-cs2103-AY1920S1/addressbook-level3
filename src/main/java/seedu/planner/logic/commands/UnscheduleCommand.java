package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
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

    public static final String MESSAGE_UNSCHEDULE_TIME_SUCCESS = "Activity %d unscheduled from Day %d";

    private final Index activityIndexToUnschedule;
    private final Index dayIndex;

    /**
     * @param activityIndex of the {@code ActivityWithTime} in the {@code Day} to edit
     * @param dayIndex      of the {@code Day} in the {@code Itinerary} to edit
     */
    public UnscheduleCommand(Index activityIndex, Index dayIndex) {
        requireAllNonNull(activityIndex, dayIndex);
        this.activityIndexToUnschedule = activityIndex;
        this.dayIndex = dayIndex;
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

        if (activityIndexToUnschedule.getZeroBased() >= activitiesInDay.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        model.unscheduleActivity(dayToEdit, activityIndexToUnschedule);

        model.updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_TIME_SUCCESS, activityIndexToUnschedule.getOneBased(),
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
