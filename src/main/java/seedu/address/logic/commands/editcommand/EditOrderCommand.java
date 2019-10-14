package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDER;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.UUID;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {

    public static final String COMMAND_WORD = "edit-o";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CUSTOMER + "2 "
            + PREFIX_PHONE + "4 "
            + PREFIX_PRICE + "$1500 ";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the seller manager.";

    private final Index orderIndex;
    private final Optional<Index> customerIndex;
    private final Optional<Index> phoneIndex;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param orderIndex of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index orderIndex, Optional<Index> customerIndex, Optional<Index> phoneIndex, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(orderIndex);
        requireNonNull(editOrderDescriptor);

        this.orderIndex = orderIndex;
        this.customerIndex = customerIndex;
        this.phoneIndex = phoneIndex;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (orderIndex.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownOrderList.get(orderIndex.getZeroBased());

        List<Customer> lastShownCustomerList = model.getFilteredCustomerList();

        if (customerIndex.isPresent()) {

            if (customerIndex.get().getZeroBased() >= lastShownCustomerList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
            }

            Customer editedCustomer = lastShownCustomerList.get(customerIndex.get().getZeroBased());

            editOrderDescriptor.setCustomer(editedCustomer);
        }

        if (phoneIndex.isPresent()) {
            List<Phone> lastShownPhoneList = model.getFilteredPhoneList();

            if (phoneIndex.get().getZeroBased() >= lastShownPhoneList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
            }

            Phone editedPhone = lastShownPhoneList.get(phoneIndex.get().getZeroBased());

            editOrderDescriptor.setPhone(editedPhone);
        }

        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDER);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedOrder), UiChange.ORDER);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit,
                                           EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        UUID id = orderToEdit.getId();
        Customer customer = editOrderDescriptor.getCustomer().orElse(orderToEdit.getCustomer());
        Phone phone = editOrderDescriptor.getPhone().orElse(orderToEdit.getPhone());
        Price price = editOrderDescriptor.getPrice().orElse(orderToEdit.getPrice());
        Status status = editOrderDescriptor.getStatus().orElse(orderToEdit.getStatus());
        Optional<Schedule> schedule = orderToEdit.getSchedule();
        Set<Tag> updatedTags = editOrderDescriptor.getTags().orElse(orderToEdit.getTags());

        return new Order(id, customer, phone, price, status, schedule, updatedTags);
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
        return orderIndex.equals(e.orderIndex)
                && customerIndex.equals(e.customerIndex)
                && phoneIndex.equals(e.phoneIndex)
                && editOrderDescriptor.equals(e.editOrderDescriptor);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private UUID id;
        private Customer customer;
        private Phone phone;
        private Price price;
        private Status status;
        private Optional<Schedule> schedule;
        private Set<Tag> tags;

        public EditOrderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setId(toCopy.id);
            setCustomer(toCopy.customer);
            setPhone(toCopy.phone);
            setPrice(toCopy.price);
            setStatus(toCopy.status);
            setSchedule(toCopy.schedule);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(customer, phone, price, status, schedule, tags);
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public void Optional<UUID> getId() {
            return Optional.ofNullable(id);
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Optional<Customer> getCustomer() {
            return Optional.ofNullable(customer);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setSchedule(Optional<Schedule> schedule) {
            this.schedule = schedule;
        }

        public Optional<Schedule> getSchedule() {
            return this.schedule;
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
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            // state check
            EditOrderDescriptor e = (EditOrderDescriptor) other;

            return getCustomer().equals(e.getCustomer())
                    && getPhone().equals(e.getPhone())
                    && getPrice().equals(e.getPrice())
                    && getStatus().equals(e.getStatus())
                    && getSchedule().equals(e.getSchedule())
                    && getTags().equals(e.getTags());
        }


    }
}
