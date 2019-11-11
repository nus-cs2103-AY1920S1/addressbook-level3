package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing driver in the address book.
 */
public class EditDriverCommand extends Command {
    public static final String COMMAND_WORD = "editD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the driver identified "
            + "by the index number used in the displayed driver list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_DRIVER_SUCCESS = "Edited Driver: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DRIVER = "This driver already exists in the address book.";

    private final int driverId;
    private final EditDriverCommand.EditDriverDescriptor editDriverDescriptor;

    /**
     * @param driverId unique id of the driver.
     * @param editDriverDescriptor details to edit the driver with
     */
    public EditDriverCommand(int driverId, EditDriverCommand.EditDriverDescriptor editDriverDescriptor) {
        requireAllNonNull(driverId, editDriverDescriptor);

        this.driverId = driverId;
        this.editDriverDescriptor = new EditDriverCommand.EditDriverDescriptor(editDriverDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasDriver(driverId)) {
            throw new CommandException(Driver.MESSAGE_INVALID_ID);
        }

        Driver driverToEdit = model.getDriver(driverId);
        Driver editedDriver = createEditedDriver(driverToEdit, editDriverDescriptor);

        if (!driverToEdit.isSameDriver(editedDriver) && model.hasDriver(editedDriver)) {
            throw new CommandException(MESSAGE_DUPLICATE_DRIVER);
        }

        model.setDriver(driverToEdit, editedDriver);
        model.refreshAllFilteredList();

        return new CommandResult(String.format(MESSAGE_EDIT_DRIVER_SUCCESS, editedDriver));
    }

    /**
     * Creates and returns a {@code Driver} with the details of {@code driverToEdit}
     * edited with {@code editDriverDescriptor}.
     */
    private static Driver createEditedDriver(Driver driverToEdit, EditDriverDescriptor editDriverDescriptor) {
        assert driverToEdit != null;

        Name updatedName = editDriverDescriptor.getName().orElse(driverToEdit.getName());
        Phone updatedPhone = editDriverDescriptor.getPhone().orElse(driverToEdit.getPhone());
        Email updatedEmail = editDriverDescriptor.getEmail().orElse(driverToEdit.getEmail());
        Address updatedAddress = editDriverDescriptor.getAddress().orElse(driverToEdit.getAddress());
        Set<Tag> updatedTags = editDriverDescriptor.getTags().orElse(driverToEdit.getTags());

        return new Driver(driverToEdit.getId(), updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDriverCommand)) {
            return false;
        }

        // state check
        EditDriverCommand e = (EditDriverCommand) other;
        return driverId == e.driverId
                && editDriverDescriptor.equals(e.editDriverDescriptor);
    }

    /**
     * Stores the details to edit the driver with. Each non-empty field value will replace the
     * corresponding field value of the persgit on.
     */
    public static class EditDriverDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditDriverDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDriverDescriptor(EditDriverCommand.EditDriverDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
            if (!(other instanceof EditDriverCommand.EditDriverDescriptor)) {
                return false;
            }

            // state check
            EditDriverCommand.EditDriverDescriptor e = (EditDriverCommand.EditDriverDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
