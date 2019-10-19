package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Order command
 */
public class AddOrderCommand extends Command {
    public static final String COMMAND_WORD = "add_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an order to the manager. "
            + "Parameters: "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER]\n"
            + "[" + PREFIX_RESTAURANT + "RESTAURANT]\n"
            + "[" + PREFIX_FOOD + "FOOD]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Sam "
            + PREFIX_RESTAURANT + "KFC "
            + PREFIX_FOOD + "3pc Chicken Meal"
            + PREFIX_FOOD + "Family feast";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This order already exists";

    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Name customerName = toAdd.getCustomer();
        Name restaurantName = toAdd.getRestaurant();
        model.getFilteredRestaurantList().contains(customerName);
        model.getFilteredRestaurantList().contains(restaurantName);
        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }
}
