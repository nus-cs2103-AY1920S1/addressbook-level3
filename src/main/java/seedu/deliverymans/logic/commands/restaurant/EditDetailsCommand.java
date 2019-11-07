package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.deliverymans.commons.util.CollectionUtil;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Rating;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Edits details of the restaurant in editing mode.
 */
public class EditDetailsCommand extends Command {
    public static final String COMMAND_WORD = "editdetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the restaurant "
            + "in editing mode currently. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "KFC "
            + PREFIX_LOCATION + "Woodlands";

    public static final String MESSAGE_EDIT_RESTAURANT_SUCCESS = "Edited Restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the "
            + "restaurant database.";

    private final EditRestaurantDescriptor editRestaurantDescriptor;

    /**
     * @param editRestaurantDescriptor details to edit the restaurant with
     */
    public EditDetailsCommand(EditRestaurantDescriptor editRestaurantDescriptor) {
        requireNonNull(editRestaurantDescriptor);

        this.editRestaurantDescriptor = new EditRestaurantDescriptor(editRestaurantDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Restaurant restaurantToEdit = model.getEditingRestaurantList().get(0);
        Restaurant editedRestaurant = createEditedRestaurant(restaurantToEdit, editRestaurantDescriptor);
        if (!restaurantToEdit.isSameRestaurant(editedRestaurant) && model.hasRestaurant(editedRestaurant)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }
        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.setEditingRestaurant(editedRestaurant);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.updateEditingRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);

        List<Order> orders = model.getFilteredOrderList().stream()
                .filter(order -> order.getRestaurant().equals(restaurantToEdit.getName()))
                .collect(Collectors.toList());
        for (Order order : orders) {
            Order newOrder = new Order.OrderBuilder().setOrderName(order.getOrderName())
                    .setCustomer(order.getCustomer())
                    .setRestaurant(editedRestaurant.getName())
                    .setDeliveryman(order.getDeliveryman())
                    .setFood(order.getFoodList())
                    .setCompleted(order.isCompleted())
                    .completeOrder();
            model.setOrder(order, newOrder);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant));
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createEditedRestaurant(Restaurant restaurantToEdit,
                                                     EditRestaurantDescriptor editRestaurantDescriptor) {
        assert restaurantToEdit != null;

        Name updatedName = editRestaurantDescriptor.getName().orElse(restaurantToEdit.getName());
        Location updatedLocation = editRestaurantDescriptor.getLocation().orElse(restaurantToEdit.getLocation());
        Rating originalRating = restaurantToEdit.getRating();
        Set<Tag> updatedTags = editRestaurantDescriptor.getTags().orElse(restaurantToEdit.getTags());
        List<Food> originalMenu = restaurantToEdit.getMenu();
        int originalQuantityOrdered = restaurantToEdit.getQuantityOrdered();

        return new Restaurant(updatedName, updatedLocation, originalRating, updatedTags, originalMenu,
                originalQuantityOrdered);
    }

    /**
     * Stores the details to edit the restaurant with. Each non-empty field value will replace the
     * corresponding field value of the restaurant.
     */
    public static class EditRestaurantDescriptor {
        private Name name;
        private Location location;
        private Set<Tag> tags;

        public EditRestaurantDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRestaurantDescriptor(EditRestaurantDescriptor toCopy) {
            setName(toCopy.name);
            setLocation(toCopy.location);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, location, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRestaurantDescriptor)) {
                return false;
            }

            // state check
            EditRestaurantDescriptor e = (EditRestaurantDescriptor) other;

            return getName().equals(e.getName())
                    && getLocation().equals(e.getLocation())
                    && getTags().equals(e.getTags());
        }
    }
}
