package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import java.util.Map;

import javafx.collections.ObservableList;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

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
            + "[" + PREFIX_FOOD + "FOOD]\n"
            + "[" + PREFIX_QUANTITY + "QUANTITY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Alex Yeoh "
            + PREFIX_RESTAURANT + "KFC "
            + PREFIX_FOOD + "3 Piece Chicken "
            + PREFIX_QUANTITY + "3 "
            + PREFIX_FOOD + "Chicken Nuggets "
            + PREFIX_QUANTITY + "20";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists";
    public static final String MESSAGE_INVALID_CUSTOMER = "The customer does not exist!";
    public static final String MESSAGE_INVALID_RESTAURANT = "The restaurant does not exist!";
    public static final String MESSAGE_INVALID_FOOD = "The food does not exist in the restaurant's menu!";

    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        Customer customerToAdd = new Customer(toAdd.getCustomer());
        Name customerName = toAdd.getCustomer();
        Name restaurantName = toAdd.getRestaurant();
        Map<Name, Integer> foodList = toAdd.getFood();
        boolean customerFound = false;
        boolean restaurantFound = false;
        for (Customer customer : model.getFilteredCustomerList()) {
            if (customer.isSameCustomer(customerToAdd)) {
                customerFound = true;
                customer.addOrder(toAdd);
            }
        }
        if (!customerFound) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER);
        }
        /*
        if (!model.getFilteredCustomerList().contains(customerName)
                || !model.getFilteredRestaurantList().contains(restaurantName)) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }*/
        for (Restaurant restaurant : model.getFilteredRestaurantList()) {
            if (restaurant.equals(restaurantName)) {
                restaurantFound = true;
                ObservableList<Food> menu = restaurant.getMenu();
                for (Name food : foodList.keySet()) {
                    if (!menu.contains(food)) {
                        throw new CommandException(MESSAGE_INVALID_FOOD);
                    }
                }
                break;
            }
        }
        if (!restaurantFound) {
            throw new CommandException(MESSAGE_INVALID_RESTAURANT);
        }

        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
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
