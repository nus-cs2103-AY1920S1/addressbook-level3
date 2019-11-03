package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing contact in SplitWiser.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person or activity "
            + "currently in view.\n"
            + "Existing values will be overwritten by the input values, and irrelevant parameters will be ignored.\n"
            + "Parameters (editing contact): "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Parameters (editing activity) "
            + "[" + PREFIX_TITLE + "TITLE] \n"
            + "Examples: \n" + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + COMMAND_WORD + " " + PREFIX_TITLE + "Fun @ Chalet";

    public static final String MESSAGE_EDIT_SUCCESS = "Edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_WRONG_CONTEXT = "You can only edit when viewing a contact or activity.";

    private final EditPersonDescriptor editPersonDescriptor;
    private final EditActivityDescriptor editActivityDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     * @param editActivityDescriptor details to edit the person with
     */
    public EditCommand(EditPersonDescriptor editPersonDescriptor, EditActivityDescriptor editActivityDescriptor) {
        requireAllNonNull(editPersonDescriptor, editActivityDescriptor);

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.editActivityDescriptor = new EditActivityDescriptor(editActivityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getContext().getType() == ContextType.VIEW_CONTACT) {
            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new CommandException(MESSAGE_USAGE);
            }

            Person personToEdit = model.getContext().getContact().get();
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

            boolean isNameDuplicated = editPersonDescriptor.getName().isPresent()
                    && model.findPersonByName(editPersonDescriptor.getName().get().toString()).isPresent();

            if (isNameDuplicated) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(personToEdit, editedPerson);
            Context newContext = new Context(editedPerson);
            model.setContext(newContext);
            return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editedPerson), newContext);
        } else if (model.getContext().getType() == ContextType.VIEW_ACTIVITY) {
            if (!editActivityDescriptor.isAnyFieldEdited()) {
                throw new CommandException(MESSAGE_USAGE);
            }

            Activity activityToEdit = model.getContext().getActivity().get();

            Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);

            model.setActivity(activityToEdit, editedActivity);
            Context newContext = new Context(editedActivity);
            model.setContext(newContext);
            return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editedActivity), newContext);
        } else {
            throw new CommandException(MESSAGE_WRONG_CONTEXT);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(personToEdit);

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(personToEdit.getPrimaryKey(), updatedName, updatedPhone,
                updatedEmail, updatedAddress, updatedTags);
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(activityToEdit);

        Title updatedTitle = editActivityDescriptor.getTitle().orElse(activityToEdit.getTitle());
        return new Activity(activityToEdit, updatedTitle);
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
        return editPersonDescriptor.equals(e.editPersonDescriptor)
                && editActivityDescriptor.equals(e.editActivityDescriptor);
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditActivityDescriptor {
        private Title title;

        public EditActivityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            setTitle(toCopy.title);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityDescriptor e = (EditActivityDescriptor) other;

            return getTitle().equals(e.getTitle());
        }

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
