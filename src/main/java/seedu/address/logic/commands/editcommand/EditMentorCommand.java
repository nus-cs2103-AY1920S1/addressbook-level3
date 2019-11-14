package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.SubjectName;

/**
 * Edits a {@link Mentor} in Alfred.
 */
public class EditMentorCommand extends EditCommand {

    public static final String MESSAGE_EDIT_MENTOR_SUCCESS = "Edited Mentor: %1$s";
    public static final String MESSAGE_NO_FIELD_TO_CHANGE = "The fields inputted are not changing any of"
            + " Mentor %s's fields.";
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX =
            "The mentor index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " mentor"
            + ": Edits the details of the mentor by ID.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Format: " + "edit mentor [mentor ID]"
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_PHONE + "PHONE] "
            + "[" + CliSyntax.PREFIX_EMAIL + "EMAIL] "
            + "[" + CliSyntax.PREFIX_ORGANISATION + "ORGANIZATION] "
            + "[" + CliSyntax.PREFIX_SUBJECT_NAME + "SUBJECT_NAME]\n"
            + "Example: " + COMMAND_WORD + " M-1 "
            + CliSyntax.PREFIX_PHONE + "+6591119111 "
            + CliSyntax.PREFIX_ORGANISATION + "Gas Mask Supplier Ltd.";

    private EditMentorDescriptor editMentorDescriptor;

    public EditMentorCommand(Id id, EditMentorDescriptor editMentorDescriptor) {
        super(id);
        requireNonNull(editMentorDescriptor);
        this.editMentorDescriptor = editMentorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Mentor mentorToEdit;
        try {
            mentorToEdit = model.getMentor(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }

        if (!this.isAnyFieldChanged(mentorToEdit, this.editMentorDescriptor)) {
            throw new CommandException(String.format(MESSAGE_NO_FIELD_TO_CHANGE, mentorToEdit.getId()));
        }
        Mentor editedMentor = this.createEditedMentor(mentorToEdit,
                this.editMentorDescriptor);

        try {
            model.updateMentor(this.id, editedMentor);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
            return new CommandResult(String.format(MESSAGE_EDIT_MENTOR_SUCCESS, editedMentor.toString()),
                    CommandType.M);
        } catch (AlfredException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and returns a new {@code Mentor} with the details {@code mentorToEdit}
     * edited with {@code editMentorDescriptor}.
     *
     * @param mentorToEdit {@code Mentor} that will be updated.
     * @param editMentorDescriptor Descriptor with the details to edit {@code mentorToEdit}.
     * @return Updated {@code Mentor}.
     */
    private Mentor createEditedMentor(Mentor mentorToEdit,
                                                EditMentorDescriptor editMentorDescriptor) {
        assert mentorToEdit != null;

        Name updatedName = editMentorDescriptor.getName().orElse(mentorToEdit.getName());
        Id id = mentorToEdit.getId();
        Phone updatedPhone = editMentorDescriptor.getPhone().orElse(mentorToEdit.getPhone());
        Email updatedEmail = editMentorDescriptor.getEmail().orElse(mentorToEdit.getEmail());
        Name updatedOrganization = editMentorDescriptor.getOrganization().orElse(mentorToEdit.getOrganization());
        SubjectName updatedSubject = editMentorDescriptor.getSubject().orElse(mentorToEdit.getSubject());

        return new Mentor(updatedName, id, updatedPhone, updatedEmail, updatedOrganization, updatedSubject);
    }

    /**
     * Checks if {@code editMentorDescriptor} is actually changing each field of {@code mentorToEdit} to
     * a different value.
     * i.e. If {@code mentorToEdit} has {@code Name} of "mentor" and {@code editMentorDescriptor} has {@code Name} of
     * "mentor", this method will return false, whereas {@link EditMentorDescriptor#isAnyFieldEdited()}
     * will return true.
     */
    private boolean isAnyFieldChanged(Mentor mentorToEdit, EditMentorDescriptor editMentorDescriptor) {
        if (!editMentorDescriptor.getName().orElse(mentorToEdit.getName()).equals(mentorToEdit.getName())) {
            return true;
        }
        if (!editMentorDescriptor.getPhone().orElse(mentorToEdit.getPhone()).equals(mentorToEdit.getPhone())) {
            return true;
        }
        if (!editMentorDescriptor.getEmail().orElse(mentorToEdit.getEmail()).equals(mentorToEdit.getEmail())) {
            return true;
        }
        if (!editMentorDescriptor.getOrganization().orElse(mentorToEdit.getOrganization())
                .equals(mentorToEdit.getOrganization())) {
            return true;
        }
        return !editMentorDescriptor.getSubject().orElse(mentorToEdit.getSubject()).equals(mentorToEdit.getSubject());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMentorCommand)) {
            return false;
        }

        // state check
        EditMentorCommand e = (EditMentorCommand) other;
        return id.equals(e.id)
                && editMentorDescriptor.equals(e.editMentorDescriptor);
    }


    /**
     * Stores the details to edit the {@code Mentor} with. Each non-empty field value will replace the
     * corresponding field value of the {@code Mentor}.
     */
    public static class EditMentorDescriptor extends EditEntityDescriptor {

        private Phone phone;
        private Email email;
        private Name organization;
        private SubjectName subject;

        public EditMentorDescriptor() {}

        public EditMentorDescriptor(EditMentorDescriptor toCopy) {
            super(toCopy);
            this.setPhone(toCopy.phone);
            this.setEmail(toCopy.email);
            this.setOrganization(toCopy.organization);
            this.setSubject(toCopy.subject);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return super.isAnyFieldEdited()
                    || CollectionUtil.isAnyNonNull(this.email, this.phone, this.organization, this.subject);
        }

        /* ======== Getters ======== */

        public Optional<Email> getEmail() {
            return Optional.ofNullable(this.email);
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(this.phone);
        }

        public Optional<Name> getOrganization() {
            return Optional.ofNullable(organization);
        }

        public Optional<SubjectName> getSubject() {
            return Optional.ofNullable(subject);
        }

        /* ======== Setters ======== */

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public void setOrganization(Name organization) {
            this.organization = organization;
        }

        public void setSubject(SubjectName subject) {
            this.subject = subject;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMentorDescriptor)) {
                return false;
            }

            // state check
            EditMentorDescriptor e = (EditMentorDescriptor) other;
            return super.equals(other)
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getOrganization().equals(e.getOrganization())
                    && getSubject().equals(e.getSubject());
        }
    }
}
