package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ACTIVITY_NOT_PRESENT_IN_DAY;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Unschedules an activity from the day by time.
 */
public class UnscheduleActivityCommand extends UnscheduleCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Unschedules all instances of an activity on a certain day. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY\n"
            + "Example: " + COMMAND_WORD
            + " " + SECOND_COMMAND_WORD
            + " 2 "
            + PREFIX_DAY + "3";

    public static final String MESSAGE_UNSCHEDULE_TIME_SUCCESS = "Activity %d unscheduled from Day %d";

    private final Index activityIndexToUnschedule;
    private final Index dayIndex;

    /**
     * @param activityIndex of the contacts in the filtered contacts list to edit
     * @param dayIndex      of the contacts in the filtered contacts list to edit
     */
    public UnscheduleActivityCommand(Index activityIndex, Index dayIndex) {
        requireAllNonNull(activityIndex, dayIndex);
        this.activityIndexToUnschedule = activityIndex;
        this.dayIndex = dayIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownDays = model.getFilteredDayList();
        List<Activity> lastShownActivities = model.getFilteredActivityList();

        if (activityIndexToUnschedule.getZeroBased() >= lastShownActivities.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToUnschedule = lastShownActivities.get(activityIndexToUnschedule.getZeroBased());
        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        Day editedDay = createUnscheduledActivityDay(dayToEdit, activityToUnschedule);
        List<Day> editedDays = new ArrayList<>(lastShownDays);
        editedDays.set(dayIndex.getZeroBased(), editedDay);

        model.setDays(editedDays);
        model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_TIME_SUCCESS, activityIndexToUnschedule.getOneBased(),
                dayIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnscheduleActivityCommand // instanceof handles nulls
                && this.dayIndex.equals(((UnscheduleActivityCommand) other).dayIndex)
                && this.activityIndexToUnschedule.equals(((
                UnscheduleActivityCommand) other).activityIndexToUnschedule));
    }

    /**
     * Creates a new day without the activity that is unscheduled.
     *
     * @param dayToEdit            of the contacts in the filtered contacts list to edit
     * @param activityToUnschedule of the contacts in the filtered contacts list to edit
     */
    private Day createUnscheduledActivityDay(Day dayToEdit, Activity activityToUnschedule) throws CommandException {
        List<ActivityWithTime> activitiesWithTime = dayToEdit.getListOfActivityWithTime();
        List<ActivityWithTime> copiedActivitiesWithTime = new ArrayList<>(activitiesWithTime);
        boolean removedAtLeastOne = false;
        for (ActivityWithTime a: activitiesWithTime) {
            if (a.getActivity().equals(activityToUnschedule)) {
                copiedActivitiesWithTime.remove(a);
                removedAtLeastOne = true;
            }
        }
        if (removedAtLeastOne) {
            return new Day(copiedActivitiesWithTime);
        } else {
            throw new CommandException(MESSAGE_ACTIVITY_NOT_PRESENT_IN_DAY);
        }
    }
}
