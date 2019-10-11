package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jarvis.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.CollectionUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.person.Address;
import seedu.jarvis.model.address.person.Email;
import seedu.jarvis.model.address.person.Name;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.address.person.Phone;
import seedu.jarvis.model.address.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditAddressCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
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

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_INVERSE_SUCCESS_EDIT = "Reverted edit.";

    public static final String MESSAGE_INVERSE_PERSON_NOT_FOUND =
            "Unable to edit person, person not found in the address book.";

    public static final String MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_PERSON =
            "There is a conflict in reverting edits made to person as there is an existing person with similar details";

    public static final boolean HAS_INVERSE = true;

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person originalPerson;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditAddressCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Edits a {@code Person} in address book with a new set of description from {@code EditPersonDescriptor}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful edit.
     * @throws CommandException If the targetIndex is out of range of the number of persons in the address book in
     * zero-based indexing, or if the new edited description is already of an existing {@code Person} in address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        // checks if index is out of bounds.
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        originalPerson = lastShownList.get(index.getZeroBased());
        Person createdEditedPerson = createEditedPerson(originalPerson, editPersonDescriptor);

        // checks if edited person does not conflict with another existing person that is not the original person.
        if (!originalPerson.isSamePerson(createdEditedPerson) && model.hasPerson(createdEditedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        editedPerson = createdEditedPerson;

        model.setPerson(originalPerson, createdEditedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Reverts back the edits made to {@code Person} in address book by the command's execution.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful revert of {@code Person} if the revert is possible, or a
     * {@code CommandResult} that the edited person is no longer in the address book, or a {@code CommandResult} that
     * there will be a conflict with an existing {@code Person} in the address book if the revert is made.
     * @throws CommandException If the person to be reverted is not found in the address book, or if reverting the edits
     * to the person will result in a conflict with another person in the address book.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        // checks if person to be reverted is in address book.
        if (!model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_INVERSE_PERSON_NOT_FOUND);
        }

        // checks if reverting the person will be in conflict with another existing person in the address book.
        if (!originalPerson.isSamePerson(editedPerson) && model.hasPerson(originalPerson)) {
            throw new CommandException(MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_PERSON);
        }

        model.setPerson(editedPerson, originalPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_INVERSE_SUCCESS_EDIT);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAddressCommand)) {
            return false;
        }

        // state check
        EditAddressCommand e = (EditAddressCommand) other;
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
