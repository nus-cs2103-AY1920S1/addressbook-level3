package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.ui.ListResourceType;

/**
 * Completes a schedule and removes it from the schedule list.
 */
public class ScheduleCompleteCommand extends ScheduleCommand {

    public static final String MESSAGE_SUCCESS = "Schedule %1$s completed and added to exercise tracker";

    private Index index;

    public ScheduleCompleteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkValidIndex(model);
        completeSchedule(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Integer.toString(index.getOneBased())),
                ListResourceType.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCompleteCommand // instanceof handles nulls
                && index.equals(((ScheduleCompleteCommand) other).index));
    }

    private void completeSchedule(Model model) {
        Schedule toComplete = model.getFilteredScheduleList().get(index.getZeroBased());
        model.completeSchedule(toComplete);
    }

    private void checkValidIndex(Model model) throws CommandException {
        if (IndexUtil.isIndexOutOfBounds(index, model.getFilteredScheduleList())) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }
    }
}
