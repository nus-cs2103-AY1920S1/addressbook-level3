package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_TRANSACTION_METHOD;
import static seedu.address.model.finance.Model.PREDICATE_SHOW_ALL_LOG_ENTRIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logEntry.Address;
import seedu.address.model.finance.logEntry.Email;
import seedu.address.model.finance.logEntry.Name;
import seedu.address.model.finance.logEntry.LogEntry;
import seedu.address.model.finance.logEntry.Phone;
import seedu.address.model.finance.tag.Tag;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DAY + "NAME] "
            + "[" + PREFIX_ITEM + "PHONE] "
            + "[" + PREFIX_CATEGORY + "EMAIL] "
            + "[" + PREFIX_PLACE + "ADDRESS] "
            + "[" + PREFIX_TRANSACTION_METHOD + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + "91234567 "
            + PREFIX_CATEGORY + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<LogEntry> lastShownList = model.getFilteredLogEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        LogEntry logEntryToEdit = lastShownList.get(index.getZeroBased());
        LogEntry editedLogEntry = createEditedPerson(logEntryToEdit, editPersonDescriptor);

        if (!logEntryToEdit.isSamePerson(editedLogEntry) && model.hasLogEntry(editedLogEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setLogEntry(logEntryToEdit, editedLogEntry);
        model.updateFilteredLogEntryList(PREDICATE_SHOW_ALL_LOG_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedLogEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static LogEntry createEditedPerson(LogEntry logEntryToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert logEntryToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(logEntryToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(logEntryToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(logEntryToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(logEntryToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(logEntryToEdit.getTags());

        return new LogEntry(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
