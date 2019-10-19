package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Schedule;

/**
 * Completes a schedule and removes it from the schedule list.
 */
public class ScheduleCompleteCommand extends ScheduleCommand {

    public static final String MESSAGE_SUCCESS = "Schedule %1$s completed and added to exercise tracker";

    private Index index;

    public ScheduleCompleteCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkValidIndex(model);
        completeSchedule(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Integer.toString(index.getOneBased())));
    }

    private void completeSchedule(Model model) {
        Schedule toComplete = model.getFilteredScheduleList().get(index.getZeroBased());
        model.completeSchedule(toComplete);
    }

    private void checkValidIndex(Model model) throws CommandException {
        if (isIndexOutOfBounds(index, model.getFilteredScheduleList())) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }
    }

    private static <T> boolean isIndexOutOfBounds(Index index, List<T> list) {
        return index.getZeroBased() >= list.size();
    }
}
