package seedu.deliverymans.logic.commands.customer;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.order.Order;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class CustomerDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer: %1$s";

    private final Index targetIndex;

    public CustomerDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Checks for the {@code Order} to be deleted when {@code Customer} is deleted and setting {@code Deliveryman}
     * to available for deleted {@code Orders}.
     */
    private void deleteCustomerOrders(Customer customer, Model model) {
        ObservableList<Order> orders = model.getFilteredOrderList();
        List<Order> ordersToDelete = new ArrayList<>();
        for (Order order : orders) {
            if (customer.getUserName().equals(order.getCustomer())) {
                ordersToDelete.add(order);
            }
        }
        int size = ordersToDelete.size();
        for (int i = 0; i < size; i++) {
            model.updateDeliverymanStatusAfterChangesToOrder(ordersToDelete.get(i).getDeliveryman());
            model.deleteOrder(ordersToDelete.get(i));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Customer customerToDelete = lastShownList.get(targetIndex.getZeroBased());
        deleteCustomerOrders(customerToDelete, model);
        model.deleteCustomer(customerToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((CustomerDeleteCommand) other).targetIndex)); // state check
    }
}
