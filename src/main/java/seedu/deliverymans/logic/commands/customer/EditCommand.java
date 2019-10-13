package seedu.deliverymans.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

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
import seedu.deliverymans.model.addressbook.tag.Tag;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;

/**
 * Edits the details of an existing customer in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the customer identified "
            + "by the index number used in the displayed customer database. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_CUSTOMER_SUCCESS = "Edited Customer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the customer database.";

    private final Index index;
    private final EditCustomerDescriptor editCustomerDescriptor;

    /**
     * @param index                  of the customer in the filtered customer list to edit
     * @param editCustomerDescriptor details to edit the customer with
     */
    public EditCommand(Index index, EditCustomerDescriptor editCustomerDescriptor) {
        requireNonNull(index);
        requireNonNull(editCustomerDescriptor);

        this.index = index;
        this.editCustomerDescriptor = new EditCustomerDescriptor(editCustomerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToEdit = lastShownList.get(index.getZeroBased());
        Customer editedCustomer = createEditedCustomer(customerToEdit, editCustomerDescriptor);

        if (!customerToEdit.isSameCustomer(editedCustomer) && model.hasCustomer(editedCustomer)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.setCustomer(customerToEdit, editedCustomer);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(String.format(MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer));
    }

    /**
     * Creates and returns a {@code Customer} with the details of {@code customerToEdit}
     * edited with {@code editCustomerDescriptor}.
     */
    private static Customer createEditedCustomer(Customer customerToEdit, EditCustomerDescriptor editCustomerDescriptor) {
        assert customerToEdit != null;

        Name updatedName = editCustomerDescriptor.getName().orElse(customerToEdit.getName());
        Phone updatedPhone = editCustomerDescriptor.getPhone().orElse(customerToEdit.getPhone());
        Set<Tag> updatedTags = editCustomerDescriptor.getTags().orElse(customerToEdit.getTags());

        return new Customer(updatedName, updatedPhone, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editCustomerDescriptor.equals(e.editCustomerDescriptor);
    }

    /**
     * Stores the details to edit the customer with. Each non-empty field value will replace the corresponding field
     * value of the customer.
     */
    public static class EditCustomerDescriptor {
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        public EditCustomerDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCustomerDescriptor(EditCustomerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
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
            if (!(other instanceof EditCustomerDescriptor)) {
                return false;
            }

            // state check
            EditCustomerDescriptor e = (EditCustomerDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags());
        }
    }
}
