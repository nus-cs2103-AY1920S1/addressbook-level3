package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOMMODATIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing accommodation in the itinerary.
 */
public class EditAccommodationCommand extends EditCommand {
    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD
            + ": Edits the details of the accommodation identified "
            + "by the index number used in the displayed accommodation list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_ACCOMMODATION_SUCCESS = "Edited Accommodation: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "This accommodation already exists in the itinerary.";

    private final Index index;
    private final EditAccommodationDescriptor editAccommodationDescriptor;

    /**
     * @param index of the accommodation in the filtered accommodation list to edit
     */
    public EditAccommodationCommand(Index index, EditAccommodationDescriptor editAccommodationDescriptor) {
        requireNonNull(index);
        requireNonNull(editAccommodationDescriptor);
        this.index = index;
        this.editAccommodationDescriptor = editAccommodationDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Accommodation> lastShownList = model.getFilteredAccommodationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
        }

        Accommodation accommodationToEdit = lastShownList.get(index.getZeroBased());
        Accommodation editedAccommodation = createEditedAccommodation(accommodationToEdit, editAccommodationDescriptor);

        if (!accommodationToEdit.isSameAccommodation(editedAccommodation)
                && model.hasAccommodation(editedAccommodation)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION);
        }

        model.setAccommodation(accommodationToEdit, editedAccommodation);
        model.updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_ACCOMMODATION_SUCCESS, editedAccommodation));
    }

    /**
     * Creates and returns a {@code Accommodation} with the details of {@code accommodationToEdit}
     * edited with {@code editAccommodationDescriptor}.
     */
    private static Accommodation createEditedAccommodation(Accommodation accommodationToEdit,
                                                      EditAccommodationDescriptor editAccommodationDescriptor) {
        assert accommodationToEdit != null;

        Name updatedName = editAccommodationDescriptor.getName().orElse(accommodationToEdit.getName());
        Address updatedAddress = editAccommodationDescriptor.getAddress().orElse(accommodationToEdit.getAddress());
        Contact updatedContact = editAccommodationDescriptor.getPhone().isPresent()
                ? new Contact(updatedName, editAccommodationDescriptor.getPhone().get(), null, null, new HashSet<>())
                : accommodationToEdit.getContact().isPresent()
                ? accommodationToEdit.getContact().get()
                : null;
        Set<Tag> updatedTags = editAccommodationDescriptor.getTags().orElse(accommodationToEdit.getTags());

        return new Accommodation(updatedName, updatedAddress, updatedContact, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditAccommodationCommand)) {
            return false;
        }

        // state check
        EditAccommodationCommand e = (EditAccommodationCommand) other;
        return other == this
                || other instanceof EditAccommodationCommand
                && index.equals(e.index)
                && editAccommodationDescriptor.equals(e.editAccommodationDescriptor);
    }

    /**
     * Stores the details to edit the accommodation with. Each non-empty field value will replace the
     * corresponding field value of the accommodation.
     */
    public static class EditAccommodationDescriptor {
        private Name name;
        private Address address;
        private Phone phone;
        private Set<Tag> tags;

        public EditAccommodationDescriptor() {}

        public EditAccommodationDescriptor(EditAccommodationDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, phone, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

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
            if (!(other instanceof EditAccommodationDescriptor)) {
                return false;
            }

            // state check
            EditAccommodationDescriptor e = (EditAccommodationDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
