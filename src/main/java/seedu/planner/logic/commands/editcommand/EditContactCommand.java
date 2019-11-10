package seedu.planner.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.util.CommandUtil.findIndexOfContact;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.CollectionUtil;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditContactCommand extends EditCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            ": Edits the details of the contact identified "
                    + "by the index number used in the displayed contact list. "
                    + "Existing values will be overwritten by the input values.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + "INDEX(must be a positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_PHONE + "PHONE] "
                    + "[" + PREFIX_EMAIL + "EMAIL] "
                    + "[" + PREFIX_ADDRESS + "ADDRESS] "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 3 "
                    + PREFIX_PHONE + "91234567 "
                    + PREFIX_EMAIL + "johndoe@example.com"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>",
            new ArrayList<>(),
            new ArrayList<>(),
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_PHONE.toString(), PREFIX_EMAIL.toString(),
                    PREFIX_ADDRESS.toString()),
            Arrays.asList(PREFIX_TAG.toString())
    );

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";

    private final Index index;
    private final EditContactDescriptor editContactDescriptor;
    private final Contact contact;
    private final boolean isUndoRedo;

    /**
     * @param index of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor, boolean isUndoRedo) {
        requireAllNonNull(index, editContactDescriptor);
        this.index = index;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
        contact = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo or generate EditAccommodationEvent
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor, Contact contact) {
        requireAllNonNull(index, contact);
        this.index = index;
        this.editContactDescriptor = editContactDescriptor;
        this.contact = contact;
        this.isUndoRedo = true;
    }

    public Index getIndex() {
        return index;
    }

    public EditContactDescriptor getEditContactDescriptor() {
        return editContactDescriptor;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        Index contactToEditIndex = findIndexOfContact(model, contactToEdit);
        Contact editedContact;
        editedContact = (contact == null) ? createEditedContact(contactToEdit, editContactDescriptor) : contact;

        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        if (contact == null && !isUndoRedo) {
            //Not due to undo/redo method of EditContactEvent
            EditContactCommand newCommand = new EditContactCommand(index, editContactDescriptor, contactToEdit);
            updateEventStack(newCommand, model);
        }
        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        Index editedContactIndex = findIndexOfContact(model, editedContact);

        return new CommandResult(
            String.format(MESSAGE_EDIT_CONTACT_SUCCESS, editedContact),
            new ResultInformation[] {
                new ResultInformation(
                    contactToEdit,
                    contactToEditIndex,
                    "Edited Contact from:"
                ),
                new ResultInformation(
                    editedContact,
                    editedContactIndex,
                    "To:"
                )
            },
            new UiFocus[] {UiFocus.CONTACT, UiFocus.INFO}
        );
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail().orElse(null));
        Address updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress().orElse(null));
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        return new Contact(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        // state check
        EditContactCommand e = (EditContactCommand) other;
        return index.equals(e.index)
                && editContactDescriptor.equals(e.editContactDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditContactDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
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
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            // state check
            EditContactDescriptor e = (EditContactDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
