package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
//import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.schedule.Schedule;

/**
 * Deletes a schedule identified using it's order's displayed index in the SML.
 */
public class DeleteScheduleCommand extends Command {

    public static final String COMMAND_WORD = "delete-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the schedule identified by the order's index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted Schedule: %1$s";
    public static final String MESSAGE_ORDER_DOES_NOT_EXIST = "This order does not exists in SML.";
    public static final String MESSAGE_ORDER_UNSCHEDULED = "This order is unscheduled in SML.";
    public static final String MESSAGE_ORDER_CANCELLED = "This order is already cancelled in SML.";
    public static final String MESSAGE_ORDER_COMPLETED = "This order is already completed in SML.";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_ORDER_DOES_NOT_EXIST);
        }

        Order orderToUnschedule = lastShownList.get(targetIndex.getZeroBased());
        switch (orderToUnschedule.getStatus()) {
        case UNSCHEDULED:
            throw new CommandException(MESSAGE_ORDER_UNSCHEDULED);
        case COMPLETED:
            throw new CommandException(MESSAGE_ORDER_COMPLETED);
        case CANCELLED:
            throw new CommandException(MESSAGE_ORDER_CANCELLED);
        default:
            // do nothing
        }

        // Change null to optional.empty
        Schedule toDelete = orderToUnschedule.getSchedule();
        Order unscheduledOrder = new Order(orderToUnschedule.getCustomer(), orderToUnschedule.getPhone(),
                orderToUnschedule.getPrice(), Status.UNSCHEDULED, null, orderToUnschedule.getTags());
        model.setOrder(orderToUnschedule, unscheduledOrder);

        if (model.hasSchedule(toDelete)) {
            model.deleteSchedule(toDelete);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, toDelete), UiChange.CUSTOMER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }

}
