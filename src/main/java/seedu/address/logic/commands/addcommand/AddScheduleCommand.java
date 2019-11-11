package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
import seedu.address.model.order.Status;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule to SML.
 */
public class AddScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to an existing order in the SML "
            + "by the index number of the order to be scheduled in the displayed order list. Add \"" + PREFIX_ALLOW
            + "\" to confirm any clashing schedules.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "YYYY.MM.DD "
            + PREFIX_TIME + "HH.MM "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_ALLOW + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2019.12.12 "
            + PREFIX_TIME + "17.30 "
            + PREFIX_VENUE + "Changi Airport T3 "
            + PREFIX_TAG + "freebie "
            + PREFIX_ALLOW;

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in SML.";

    private final Schedule toAdd;
    private final Index orderIndex;
    private final boolean canClash;

    /**
     * Creates an AddScheduleCommand to add the specified {@code Schedule}
     */
    public AddScheduleCommand(Schedule schedule, Index index, boolean canClash) {
        requireNonNull(schedule);
        requireNonNull(index);
        this.toAdd = schedule;
        this.orderIndex = index;
        this.canClash = canClash;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);

        List<Order> orderList = model.getFilteredOrderList();

        // check if model already has this schedule
        // or if the order index is invalid
        if (model.hasSchedule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        } else if (orderIndex.getZeroBased() >= orderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        // Get the list of conflicts from model
        List<Schedule> conflicts = model.getConflictingSchedules(toAdd);
        StringBuilder sb = new StringBuilder();

        // if there are conflicts present and user did not put -allow flag, throw exception
        if (!conflicts.isEmpty() && !canClash) {
            sb.append("\nHere are the list of conflicting schedules:\n");
            conflicts.stream()
                    .flatMap(x -> orderList.stream()
                            .filter(y -> y.getSchedule().isPresent())
                            .filter(y -> y.getSchedule().get().isSameAs(x)))
                    .sorted(Comparator.comparing(a -> a.getSchedule().get().getCalendar()))
                    .forEach(y -> sb.append(String.format("%s: Order %d\n", y.getSchedule().get().getCalendarString(),
                            orderList.indexOf(y) + 1)));

            throw new CommandException(Messages.MESSAGE_SCHEDULE_CONFLICT + sb.toString());
        }

        // Check if the order status is valid - only UNSCHEDULED is valid
        Order orderToSchedule = orderList.get(orderIndex.getZeroBased());
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

        return new CommandResult(String.format(MESSAGE_SUCCESS + sb.toString(), toAdd), UiChange.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && orderIndex.equals(((AddScheduleCommand) other).orderIndex)
                && canClash == ((AddScheduleCommand) other).canClash
                // not checking ID
                && toAdd.getCalendar().equals(((AddScheduleCommand) other).toAdd.getCalendar())
                && toAdd.getVenue().equals(((AddScheduleCommand) other).toAdd.getVenue())
                && toAdd.getTags().equals(((AddScheduleCommand) other).toAdd.getTags()));
    }

}
