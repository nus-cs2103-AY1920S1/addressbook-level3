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
 * Completes an order identified using it's displayed index from the seller manager.
 */
public class CompleteOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_ORDER_SUCCESS = "Completed Order: %1$s";

    private final Index targetIndex;

    public CompleteOrderCommand(Index targetIndex) {
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

        Order orderToComplete = lastShownList.get(targetIndex.getZeroBased());
        UUID id = orderToComplete.getId();
        Customer customer = orderToComplete.getCustomer();
        Phone phone = orderToComplete.getPhone();
        Price price = orderToComplete.getPrice();
        Optional<Schedule> schedule = orderToComplete.getSchedule();
        Set<Tag> tags = orderToComplete.getTags();
        Order completedOrder = new Order(id, customer, phone, price, Status.COMPLETED, schedule, tags);

        model.addArchivedOrder(completedOrder);

        if (model.hasPhone(phone)) {
            model.deletePhone(phone);
        }

        if (model.hasOrder(orderToComplete)) {
            model.deleteOrder(orderToComplete);
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_ORDER_SUCCESS, orderToComplete),
                UiChange.ARCHIVED_ORDER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteOrderCommand // instanceof handles nulls
                && targetIndex.equals(((CompleteOrderCommand) other).targetIndex)); // state check
    }
}
