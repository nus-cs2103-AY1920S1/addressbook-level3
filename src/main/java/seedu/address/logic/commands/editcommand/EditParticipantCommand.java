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
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;

/**
 * Edits a {@link Participant} in Alfred.
 */
public class EditParticipantCommand extends EditCommand {

    public static final String MESSAGE_EDIT_PARTICIPANT_SUCCESS = "Edited Participant: %1$s";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX =
            "The participant index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " participant"
            + ": Edits the details of the participant by ID.\n "
            + "Existing values will be overwritten by the input values.\n"
            + "Format: " + "edit participant [participant ID]"
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_PHONE + "PHONE] "
            + "[" + CliSyntax.PREFIX_EMAIL + "EMAIL]\n"
            + "Example: " + COMMAND_WORD + " P-1 "
            + CliSyntax.PREFIX_PHONE + "+659456 9789 "
            + CliSyntax.PREFIX_EMAIL + "alfred@butler.com";

    private EditParticipantDescriptor editParticipantDescriptor;

    public EditParticipantCommand(Id id, EditParticipantDescriptor editParticipantDescriptor) {
        super(id);
        requireNonNull(editParticipantDescriptor);
        this.editParticipantDescriptor = editParticipantDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Participant participantToEdit;
        try {
            participantToEdit = model.getParticipant(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }
        Participant editedParticipant = this.createEditedParticipant(participantToEdit,
                this.editParticipantDescriptor);

        try {
            model.updateParticipant(this.id, editedParticipant);
            model.updateHistory(this);
            return new CommandResult(String.format(MESSAGE_EDIT_PARTICIPANT_SUCCESS,
                    editedParticipant.toString()), CommandType.P);
        } catch (AlfredException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditParticipantCommand)) {
            return false;
        }

        // state check
        EditParticipantCommand e = (EditParticipantCommand) other;
        return id.equals(e.id)
                && editParticipantDescriptor.equals(e.editParticipantDescriptor);
    }

    /**
     * Creates and returns a new {@code Participant} with the details {@code participantToEdit}
     * edited with {@code editParticipantDescriptor}.
     *
     * @param participantToEdit         {@code Participant} that will be updated.
     * @param editParticipantDescriptor Descriptor with the details to edit {@code participantToEdit}.
     * @return Updated {@code Participant}.
     */
    private Participant createEditedParticipant(Participant participantToEdit,
                                                EditParticipantDescriptor editParticipantDescriptor) {
        assert participantToEdit != null;

        Name updatedName = editParticipantDescriptor.getName().orElse(participantToEdit.getName());
        Id id = participantToEdit.getId();
        Email updatedEmail = editParticipantDescriptor.getEmail().orElse(participantToEdit.getEmail());
        Phone updatedPhone = editParticipantDescriptor.getPhone().orElse(participantToEdit.getPhone());

        // Reorder parameters as necessary
        return new Participant(updatedName, id, updatedEmail, updatedPhone);
    }

    /**
     * Stores the details to edit the {@code Participant} with. Each non-empty field value will replace the
     * corresponding field value of the {@code Participant}.
     */
    public static class EditParticipantDescriptor extends EditEntityDescriptor {

        private Email email;
        private Phone phone;

        public EditParticipantDescriptor() {
        }

        public EditParticipantDescriptor(EditParticipantDescriptor toCopy) {
            super(toCopy);
            this.setEmail(toCopy.email);
            this.setPhone(toCopy.phone);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return super.isAnyFieldEdited()
                    || CollectionUtil.isAnyNonNull(this.email, this.phone);
        }

        /* ======== Getters ======== */

        public Optional<Email> getEmail() {
            return Optional.ofNullable(this.email);
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(this.phone);
        }

        /* ======== Setters ======== */

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditParticipantDescriptor)) {
                return false;
            }

            // state check
            EditParticipantDescriptor e = (EditParticipantDescriptor) other;
            return super.equals(other)
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail());
        }
    }
}
