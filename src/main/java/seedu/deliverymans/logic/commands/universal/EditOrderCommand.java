package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_DELIVERYMAN;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.commons.util.CollectionUtil;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.order.Order;

/**
 * Edits the details of an existing order.
 */
public class EditOrderCommand extends Command {
    public static final String COMMAND_WORD = "edit_order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edit an order already present in the manager. "
            + "Parameters: "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER]\n"
            + "[" + PREFIX_RESTAURANT + "RESTAURANT]\n"
            + "[" + PREFIX_DELIVERYMAN + "DELIVERYMAN]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Sam "
            + PREFIX_RESTAURANT + "KFC "
            + PREFIX_DELIVERYMAN + "Deliveryman #1337";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists.";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index               of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedOrder));
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        Name updatedOrderName = editOrderDescriptor.getOrderName().orElse(orderToEdit.getOrderName());
        Name updatedCustomer = editOrderDescriptor.getCustomer().orElse(orderToEdit.getCustomer());
        Name updatedRestaurant = editOrderDescriptor.getRestaurant().orElse(orderToEdit.getRestaurant());
        Name updatedDeliveryman = editOrderDescriptor.getDeliveryman().orElse(orderToEdit.getDeliveryman());
        boolean updatedIsCompleted = editOrderDescriptor.getCompleted().orElse(orderToEdit.isCompleted());
        Set<Tag> updatedFood = editOrderDescriptor.getFoods().orElse(orderToEdit.getFood());

        Order order = new Order(updatedOrderName, updatedCustomer, updatedRestaurant, updatedFood);
        order.setDeliveryman(updatedDeliveryman);
        order.addFood(updatedFood);
        if (updatedIsCompleted) {
            order.completeOrder();
        }
        return order;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        // state check
        EditOrderCommand e = (EditOrderCommand) other;
        return index == e.index
                && editOrderDescriptor.equals(e.editOrderDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private Name orderName;
        private Name customer;
        private Name restaurant;
        private Name deliveryman;
        private Boolean isCompleted;
        private Set<Tag> foods;

        public EditOrderDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setOrderName(toCopy.orderName);
            setCustomer(toCopy.customer);
            setRestaurant(toCopy.restaurant);
            setDeliveryman(toCopy.deliveryman);
            setCompleted(toCopy.isCompleted);
            setFoods(toCopy.foods);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(orderName, customer, restaurant, deliveryman, foods);
        }

        public void setOrderName(Name name) {
            this.orderName = name;
        }

        public Optional<Name> getOrderName() {
            return Optional.ofNullable(orderName);
        }

        public void setCustomer(Name customer) {
            this.customer = customer;
        }

        public Optional<Name> getCustomer() {
            return Optional.ofNullable(customer);
        }

        public void setRestaurant(Name restaurant) {
            this.restaurant = restaurant;
        }

        public Optional<Name> getRestaurant() {
            return Optional.ofNullable(restaurant);
        }

        public void setDeliveryman(Name deliveryman) {
            this.deliveryman = deliveryman;
        }

        public Optional<Name> getDeliveryman() {
            return Optional.ofNullable(deliveryman);
        }

        public void setCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Optional<Boolean> getCompleted() {
            return Optional.ofNullable(isCompleted);
        }

        /**
         * Sets {@code tags} to this object's {@code food}.
         * A defensive copy of {@code food} is used internally.
         */
        public void setFoods(Set<Tag> foods) {
            this.foods = (foods != null) ? new HashSet<>(foods) : null;
        }

        /**
         * Returns an unmodifiable food set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code food} is null.
         */
        public Optional<Set<Tag>> getFoods() {
            return (foods != null) ? Optional.of(Collections.unmodifiableSet(foods)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            // state check
            EditOrderDescriptor e = (EditOrderDescriptor) other;

            return getOrderName().equals(e.getOrderName())
                    && getCustomer().equals(e.getCustomer())
                    && getRestaurant().equals(e.getRestaurant())
                    && getDeliveryman().equals(e.getDeliveryman())
                    && (getCompleted() == e.getCompleted())
                    && getFoods().equals(e.getFoods());
        }
    }
}
