package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;

/**
 * Updates the details of an existing person in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_PASSWORD + "PASSWORD] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final UpdatePersonDescriptor updatePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to update
     * @param updatePersonDescriptor details to update the person with
     */
    public UpdateCommand(Index index, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(updatePersonDescriptor);

        this.index = index;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        Person updatedPerson = createUpdatedPerson(personToUpdate, updatePersonDescriptor);

        if (!personToUpdate.isSamePerson(updatedPerson) && model.hasPerson(updatedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, updatedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * updated with {@code updatePersonDescriptor}.
     */
    private static Person createUpdatedPerson(Person personToUpdate, UpdatePersonDescriptor updatePersonDescriptor) {
        assert personToUpdate != null;

        Name updatedName = updatePersonDescriptor.getName().orElse(personToUpdate.getName());
        Phone updatedPhone = updatePersonDescriptor.getPhone().orElse(personToUpdate.getPhone());
        Email updatedEmail = updatePersonDescriptor.getEmail().orElse(personToUpdate.getEmail());
        Username updatedUsername = updatePersonDescriptor.getUsername().orElse(personToUpdate.getUsername());
        Password updatedPassword = updatePersonDescriptor.getPassword().orElse(personToUpdate.getPassword());
        Set<Tag> updatedTags = updatePersonDescriptor.getTags().orElse(personToUpdate.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedTags, updatedUsername, updatedPassword);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand e = (UpdateCommand) other;
        return index.equals(e.index)
                && updatePersonDescriptor.equals(e.updatePersonDescriptor);
    }

    /**
     * Stores the details to update the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdatePersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Username username;
        private Password password;
        private Set<Tag> tags;

        public UpdatePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdatePersonDescriptor(UpdatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setUsername(toCopy.username);
            setPassword(toCopy.password);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(name, phone, email, username, password, tags);
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

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
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
            if (!(other instanceof UpdatePersonDescriptor)) {
                return false;
            }

            // state check
            UpdatePersonDescriptor e = (UpdatePersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getUsername().equals(e.getUsername())
                    && getPassword().equals(e.getPassword())
                    && getEmail().equals(e.getEmail())
                    && getTags().equals(e.getTags());
        }
    }
}
