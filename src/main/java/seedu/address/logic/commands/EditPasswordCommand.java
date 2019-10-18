package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

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
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing password in the password book.
 */
public class EditPasswordCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the password identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_USERNAME + "USER] "
            + "[" + PREFIX_PASSWORDVALUE + "PASSWORD] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: Hello World";

    public static final String MESSAGE_EDIT_PASSWORD_SUCCESS = "Edited Password: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PASSWORD = "This person already exists in the password book.";

    private final Index index;
    private final EditPasswordDescriptor editPasswordDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPasswordDescriptor details to edit the person with
     */
    public EditPasswordCommand(Index index, EditPasswordDescriptor editPasswordDescriptor) {
        requireNonNull(index);
        requireNonNull(editPasswordDescriptor);

        this.index = index;
        this.editPasswordDescriptor = new EditPasswordDescriptor(editPasswordDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Password> lastShownList = model.getFilteredPasswordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD_DISPLAYED_INDEX);
        }

        Password passwordToEdit = lastShownList.get(index.getZeroBased());
        Password editedPassword = createEditedPassword(passwordToEdit, editPasswordDescriptor);

        if (!passwordToEdit.isSamePassword(editedPassword) && model.hasPassword(editedPassword)) {
            throw new CommandException(MESSAGE_DUPLICATE_PASSWORD);
        }

        model.setPassword(passwordToEdit, editedPassword);
        model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
        return new CommandResult(String.format(MESSAGE_EDIT_PASSWORD_SUCCESS, editedPassword));
    }

    /**
     * Creates and returns a {@code Password} with the details of {@code passwordToEdit}
     * edited with {@code editPasswordDescriptor}.
     */
    private static Password createEditedPassword(Password passwordToEdit,
                                                 EditPasswordDescriptor editPasswordDescriptor) {
        assert passwordToEdit != null;

        Description updatedDescription = editPasswordDescriptor
                .getDescription().orElse(passwordToEdit.getDescription());
        Username updatedUsername = editPasswordDescriptor
                .getUsername().orElse(passwordToEdit.getUsername());
        PasswordValue updatedPasswordValue = editPasswordDescriptor
                .getPasswordValue().orElse(passwordToEdit.getPasswordValue());
        Set<Tag> updatedTags = editPasswordDescriptor
                .getTags().orElse(passwordToEdit.getTags());

        return new Password(updatedDescription, updatedUsername, updatedPasswordValue, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPasswordCommand)) {
            return false;
        }

        // state check
        EditPasswordCommand e = (EditPasswordCommand) other;
        return index.equals(e.index)
                && editPasswordDescriptor.equals(e.editPasswordDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPasswordDescriptor {
        private Description description;
        private Username username;
        private PasswordValue passwordValue;
        private Set<Tag> tags;

        public EditPasswordDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPasswordDescriptor(EditPasswordDescriptor toCopy) {
            setDescription(toCopy.description);
            setUsername(toCopy.username);
            setPasswordValue(toCopy.passwordValue);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, username, passwordValue, tags);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPasswordValue(PasswordValue passwordValue) {
            this.passwordValue = passwordValue;
        }

        public Optional<PasswordValue> getPasswordValue() {
            return Optional.ofNullable(passwordValue);
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
            if (!(other instanceof EditPasswordDescriptor)) {
                return false;
            }

            // state check
            EditPasswordDescriptor e = (EditPasswordDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getUsername().equals(e.getUsername())
                    && getPasswordValue().equals(e.getPasswordValue())
                    && getTags().equals(e.getTags());
        }
    }
}
