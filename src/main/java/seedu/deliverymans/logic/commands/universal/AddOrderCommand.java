package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

import java.util.Map;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.deliveryman.exceptions.NoMoreAvailableDeliverymanException;
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

    public static final String MESSAGE_SUCCESS_ADD = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists";
    public static final String MESSAGE_DELIVERYMAN_UNAVAILABLE = "The deliveryman is unavailable!";
    public static final String MESSAGE_INVALID_CUSTOMER = "The customer does not exist!";
    public static final String MESSAGE_INVALID_DELIVERYMAN = "The deliveryman does not exist!";
    public static final String MESSAGE_INVALID_RESTAURANT = "The restaurant does not exist!";
    public static final String MESSAGE_INVALID_FOOD = "The food does not exist in the restaurant's menu!";

    private final Order toAdd;
    private final Order orderToRemove;
    private final boolean isAddOrder; // true -> new add order, false -> edit order

    public AddOrderCommand(Order toAdd, boolean isAddOrder) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.orderToRemove = null;
        this.isAddOrder = isAddOrder;
    }

    AddOrderCommand(Order toAdd, Order orderToRemove, boolean isAddOrder) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.orderToRemove = orderToRemove;
        this.isAddOrder = isAddOrder;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        Name customerToAdd = null;
        Name restaurantToAdd = null;
        Name deliverymanToAdd = null;
        Restaurant res = null;

        // Customer validity check
        for (Customer customer : model.getFilteredCustomerList()) {
            if (customer.getName().equals(toAdd.getCustomer())) {
                customerToAdd = toAdd.getCustomer();
                break;
            }
        }
        if (customerToAdd == null) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER);
        }

        // Restaurant validity check
        for (Restaurant restaurant : model.getFilteredRestaurantList()) {
            if (restaurant.getName().equals(toAdd.getRestaurant())) {
                restaurantToAdd = toAdd.getRestaurant();
                res = restaurant;
                break;
            }
        }
        if (restaurantToAdd == null) {
            throw new CommandException(MESSAGE_INVALID_RESTAURANT);
        }

        // Deliveryman validity check
        /*
        if (toAdd.getDeliveryman().fullName.equals("Unassigned")) {
            deliverymanToAdd = model.getOneAvailableDeliveryman();
            if (deliverymanToAdd == null) {
                deliverymanToAdd = toAdd.getDeliveryman();
            }
        } else {
            for (Deliveryman deliveryman : model.getFilteredDeliverymenList()) {
                if (deliveryman.getName().equals(toAdd.getDeliveryman())) {
                    if (!deliveryman.getStatus().getDescription().equals("UNAVAILABLE")) {
                        deliverymanToAdd = deliveryman.getName();
                    } else {
                        throw new CommandException(MESSAGE_DELIVERYMAN_UNAVAILABLE);
                    }
                    break;
                }
            }
            if (deliverymanToAdd == null) {
                throw new CommandException(MESSAGE_INVALID_DELIVERYMAN);
            }
        }
        */

        // Alternative: Add command wont work if no available deliveryman
        try {
            deliverymanToAdd = model.getOneAvailableDeliveryman();
            toAdd.setDeliveryman(deliverymanToAdd);
        } catch (NoMoreAvailableDeliverymanException nmade) {
           throw new NoMoreAvailableDeliverymanException(); // remove if you want order to be continued to be added
        }

        // Food validity check
        Map<Name, Integer> foodNameList = toAdd.getFoodList();
        for (Name foodName : foodNameList.keySet()) {
            boolean validFood = false;
            for (Food food : res.getMenu()) {
                if (food.getName().equals(foodName)) {
                    validFood = true;
                    break;
                }
            }
            if (!validFood) {
                throw new CommandException(MESSAGE_INVALID_FOOD);
            }
        }

        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        // Adding of order into the model and printing of success message depending on adding/editing order
        if (isAddOrder) { // Add order
            model.addOrder(toAdd);
        } else { // Edit order
            model.setOrder(orderToRemove, toAdd);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS_ADD, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }

}
