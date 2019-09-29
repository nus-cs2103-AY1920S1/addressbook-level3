package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class EditParticipantCommand extends EditCommand {

    /* Possible Fields */
    public static final String MESSAGE_EDIT_PARTICIPANT_SUCCESS = "Edited Participant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided."; // to be used in Parser
    public static final String MESSAGE_DUPLICATE_PARTICIPANT = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The participant index provided is invalid";

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

        // Model should check if there exists duplicates in list
        /*
         * i.e
         * if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
         *     throw new CommandException(MESSAGE_DUPLICATE_PERSON);
         * }
         */
        if (model.updateParticipant(this.id, editedParticipant)) {
//        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PARTICIPANT_SUCCESS, editedParticipant.toString()));
        }
        return new CommandResult(MESSAGE_DUPLICATE_PARTICIPANT);
    }

    private Participant createEditedParticipant(Participant participantToEdit,
                                                EditParticipantDescriptor editParticipantDescriptor) {
        assert participantToEdit != null;

        Name updatedName = editParticipantDescriptor.getName().orElse(participantToEdit.getName());
        Id updatedId = editParticipantDescriptor.getId().orElse(participantToEdit.getId());
        Email updatedEmail = editParticipantDescriptor.getEmail().orElse(participantToEdit.getEmail());
        Phone updatedPhone = editParticipantDescriptor.getPhone().orElse(participantToEdit.getPhone());

        // Reorder parameters as necessary
        return new Participant(updatedName, updatedEmail, updatedPhone, updatedId);
    }

    public static class EditParticipantDescriptor extends EditEntityDescriptor {

        private Email email;
        private Phone phone;

        public EditParticipantDescriptor() {}

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
                    && CollectionUtil.isAnyNonNull(this.email, this.phone);
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
