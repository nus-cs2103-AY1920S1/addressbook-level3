package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;

/**
 * Deletes a schedule identified using it's order's displayed index in the SML.
 */
public class DeleteScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the schedule identified by the order's index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted Schedule: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);

        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToUnschedule = lastShownList.get(targetIndex.getZeroBased());
        switch (orderToUnschedule.getStatus()) {
        case UNSCHEDULED:
            throw new CommandException(Messages.MESSAGE_ORDER_UNSCHEDULED);
        case COMPLETED:
            throw new CommandException(Messages.MESSAGE_ORDER_COMPLETED);
        case CANCELLED:
            throw new CommandException(Messages.MESSAGE_ORDER_CANCELLED);
        default:
            // do nothing
        }

        Schedule toDelete = orderToUnschedule.getSchedule().get();
        model.deleteSchedule(toDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, toDelete), UiChange.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }

}
