package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ACTIVITY_NOT_PRESENT_IN_DAY;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;

/**
 * Unschedules an activity from the day by time.
 */
public class UnscheduleTimeCommand extends UnscheduleCommand {

    public static final String SECOND_COMMAND_WORD = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Unschedules an activity by a time occupied by the activity on a certain day. "
            + "Parameters: TIME (in 24-hour clock format) "
            + PREFIX_DAY + "DAY\n"
            + "Example: " + COMMAND_WORD
            + " " + SECOND_COMMAND_WORD
            + " 0900 "
            + PREFIX_DAY + "3";

    public static final String MESSAGE_UNSCHEDULE_TIME_SUCCESS = "Activity unscheduled: %1$s";
    public static final String MESSAGE_ACTIVITY_DOES_NOT_EXIST = "Activity does not exist at given time.";

    private final Index dayIndex;
    private final LocalTime time;

    /**
     * @param dayIndex of the contacts in the filtered contacts list to edit
     * @param time details to edit the contacts with
     */
    public UnscheduleTimeCommand(LocalTime time, Index dayIndex) {
        requireAllNonNull(dayIndex, time);
        this.dayIndex = dayIndex;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownDays = model.getFilteredDayList();

        if (dayIndex.getZeroBased() >= lastShownDays.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }

        Day dayToEdit = lastShownDays.get(dayIndex.getZeroBased());
        Day editedDay = createUnscheduledActivityDay(dayToEdit, this.time);
        List<Day> editedDays = new ArrayList<>(lastShownDays);
        editedDays.set(dayIndex.getZeroBased(), editedDay);

        model.setDays(editedDays);
        model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_TIME_SUCCESS, editedDay));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnscheduleTimeCommand // instanceof handles nulls
                && this.dayIndex.equals(((UnscheduleTimeCommand) other).dayIndex)
                && this.time.equals(((UnscheduleTimeCommand) other).time));
    }

    /**
     * Creates a new day without the activity that is unscheduled.
     * @param dayToEdit of the contacts in the filtered contacts list to edit
     * @param time of the contacts in the filtered contacts list to edit
     */
    private Day createUnscheduledActivityDay(Day dayToEdit, LocalTime time) throws CommandException {
        List<ActivityWithTime> activitiesWithTime = dayToEdit.getListOfActivityWithTime();
        Optional<ActivityWithTime> activityAtTime = dayToEdit.getActivityWithTime(time);
        if (activityAtTime.isPresent()) {
            ActivityWithTime activityToRemove = activityAtTime.get();
            if (!activitiesWithTime.contains(activityToRemove)) {
                throw new CommandException(MESSAGE_ACTIVITY_NOT_PRESENT_IN_DAY);
            }
            activitiesWithTime.remove(activityToRemove);
            return new Day(activitiesWithTime);
        } else {
            throw new CommandException(MESSAGE_ACTIVITY_DOES_NOT_EXIST);
        }

    }
}
