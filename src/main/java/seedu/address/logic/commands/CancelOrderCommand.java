package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Cancels an order identified using it's displayed index from the seller manager.
 */
public class CancelOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CANCEL_ORDER_SUCCESS = "Cancelled Order: %1$s";

    private final Index targetIndex;

    public CancelOrderCommand(Index targetIndex) {
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

        Order orderToCancel = lastShownList.get(targetIndex.getZeroBased());
        UUID id = orderToCancel.getId();
        Customer customer = orderToCancel.getCustomer();
        Phone phone = orderToCancel.getPhone();
        Price price = orderToCancel.getPrice();
        Optional<Schedule> schedule = orderToCancel.getSchedule();
        Set<Tag> tags = orderToCancel.getTags();
        Order cancelledOrder = new Order(id, customer, phone, price, Status.CANCELLED, schedule, tags);


        if (model.hasOrder(orderToCancel)) {
            model.deleteOrder(orderToCancel);
        }

        model.addArchivedOrder(cancelledOrder);

        Optional<Schedule> scheduleToCancel = orderToCancel.getSchedule();
        if (scheduleToCancel.isPresent() && model.hasSchedule((scheduleToCancel.get()))) {
            model.deleteSchedule(scheduleToCancel.get());
        }

        return new CommandResult(String.format(MESSAGE_CANCEL_ORDER_SUCCESS, orderToCancel), UiChange.ARCHIVED_ORDER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelOrderCommand // instanceof handles nulls
                && targetIndex.equals(((CancelOrderCommand) other).targetIndex)); // state check
    }
}
