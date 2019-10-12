package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule to SML.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to an existing order in the SML "
            + "by the index number of the order to be scheduled in the displayed order list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CALENDAR + "YYYY.MM.DD.HH.MM "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CALENDAR + "2019.12.12.17.30 "
            + PREFIX_VENUE + "Changi Airport T3 "
            + PREFIX_TAG + "freebie";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in SML.";

    private final Schedule toAdd;
    private final Index orderIndex;

    /**
     * Creates an AddScheduleCommand to add the specified {@code Schedule}
     */
    public AddScheduleCommand(Schedule schedule, Index index) {
        requireNonNull(schedule);
        toAdd = schedule;
        orderIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Order> lastShownList = model.getFilteredOrderList();

        if (model.hasSchedule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        } else if (orderIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToSchedule = lastShownList.get(orderIndex.getZeroBased());
        switch (orderToSchedule.getStatus()) {
        case SCHEDULED:
            throw new CommandException(Messages.MESSAGE_ORDER_SCHEDULED);
        case COMPLETED:
            throw new CommandException(Messages.MESSAGE_ORDER_COMPLETED);
        case CANCELLED:
            throw new CommandException(Messages.MESSAGE_ORDER_CANCELLED);
        default:
            // do nothing
        }

        Order scheduledOrder = new Order(orderToSchedule.getId(), orderToSchedule.getCustomer(),
                orderToSchedule.getPhone(), orderToSchedule.getPrice(), Status.SCHEDULED, Optional.of(toAdd),
                orderToSchedule.getTags());

        model.setOrder(orderToSchedule, scheduledOrder);
        model.addSchedule(toAdd);

        // For testing without order
        /*
        if (model.hasSchedule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }
        model.addSchedule(toAdd);
        */

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), UiChange.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && toAdd.equals(((AddScheduleCommand) other).toAdd));
    }

}
