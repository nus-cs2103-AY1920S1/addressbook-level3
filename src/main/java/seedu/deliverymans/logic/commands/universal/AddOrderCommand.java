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
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Order command
 */
public class AddOrderCommand extends Command {
    public static final String COMMAND_WORD = "-add_order";

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

    private static final String MESSAGE_SUCCESS_ADD = "New order added: %1$s";
    private static final String MESSAGE_DUPLICATE_ORDER = "This order already exists";
    private static final String MESSAGE_INVALID_CUSTOMER = "The customer does not exist!";
    private static final String MESSAGE_INVALID_RESTAURANT = "The restaurant does not exist!";
    private static final String MESSAGE_INVALID_FOOD = "The food does not exist in the restaurant's menu!";

    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        Name deliverymanToAdd = null;

        // Validity checks for customer, restaurant and foodList
        isValidOrder(toAdd, model);

        // Assigning deliveryman
        try {
            if (toAdd.getDeliveryman().fullName.equalsIgnoreCase("Unassigned")) {
                deliverymanToAdd = model.getOneAvailableDeliveryman();
            }
            if (deliverymanToAdd == null) {
                System.out.println(toAdd.getDeliveryman());
                deliverymanToAdd = toAdd.getDeliveryman();
            }
        } catch (NullPointerException e) {
            deliverymanToAdd = new Name("Unassigned");
        }

        // Instantiating the order
        Order order = new Order.OrderBuilder().setCustomer(toAdd.getCustomer())
                .setRestaurant(toAdd.getRestaurant()).setDeliveryman(deliverymanToAdd)
                .setFood(toAdd.getFoodList()).setCompleted(toAdd.isCompleted()).completeOrder();

        if (model.hasOrder(order)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }
        model.addOrder(order);

        return new CommandResult(String.format(MESSAGE_SUCCESS_ADD, order));
    }

    /**
     * Tofill.
     */
    static void isValidOrder(Order toAdd, Model model) throws CommandException {
        Customer customerToAdd = null;
        Restaurant restaurantToAdd = null;

        // Customer validity check
        for (Customer customer : model.getFilteredCustomerList()) {
            if (customer.getName().equals(toAdd.getCustomer())) {
                customerToAdd = customer;
                break;
            }
        }
        if (customerToAdd == null) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER);
        }

        // Restaurant validity check
        for (Restaurant restaurant : model.getFilteredRestaurantList()) {
            if (restaurant.getName().equals(toAdd.getRestaurant())) {
                restaurantToAdd = restaurant;
                break;
            }
        }
        if (restaurantToAdd == null) {
            throw new CommandException(MESSAGE_INVALID_RESTAURANT);
        }

        // Food validity check
        Map<Name, Integer> foodNameList = toAdd.getFoodList();
        for (Name foodName : foodNameList.keySet()) {
            boolean validFood = false;
            for (Food food : restaurantToAdd.getMenu()) {
                if (food.getName().equals(foodName)) {
                    validFood = true;
                    break;
                }
            }
            if (!validFood) {
                throw new CommandException(MESSAGE_INVALID_FOOD);
            }
        }

        customerToAdd.addOrder(toAdd, restaurantToAdd.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }

}
