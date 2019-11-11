package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.LinkedList;
import java.util.List;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Assigns an order identified using its displayed index from the address book.
 */
public class AssignOrderCommand extends Command {

    public static final String COMMAND_WORD = "-assign_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the order identified by the order name used in the displayed order list.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "ORDERNAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Order 1";

    private static final String MESSAGE_ASSIGN_ORDER_SUCCESS = "Assigned Order: %1$s";
    private static final LinkedList<Prefix> prefixesList = new LinkedList<>(List.of(PREFIX_NAME));

    private final Name targetOrder;

    public AssignOrderCommand(Name targetOrder) {
        this.targetOrder = targetOrder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Order orderToAssign = model.getOrder(targetOrder);

        if (orderToAssign == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_NAME);
        }

        if (orderToAssign.isCompleted() || !(orderToAssign.getDeliveryman().toString().equals(("Unassigned")))) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_TO_ASSIGN);
        }

        Name newDeliveryman = model.getOneAvailableDeliveryman();

        // Assign new deliveryman to specified order
        Order order = new Order.OrderBuilder().setOrderName(orderToAssign.getOrderName())
                .setCustomer(orderToAssign.getCustomer()).setRestaurant(orderToAssign.getRestaurant())
                .setFood(orderToAssign.getFoodList()).setDeliveryman(newDeliveryman).completeOrder();

        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        model.setOrder(orderToAssign, order);

        return new CommandResult(String.format(MESSAGE_ASSIGN_ORDER_SUCCESS, order));
    }

    public static LinkedList<Prefix> getPrefixesList() {
        return prefixesList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignOrderCommand // instanceof handles nulls
                && targetOrder.equals(((AssignOrderCommand) other).targetOrder)); // state check
    }
}
