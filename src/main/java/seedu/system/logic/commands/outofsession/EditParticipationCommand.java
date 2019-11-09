package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.system.commons.util.CollectionUtil;
import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;

/**
 * Edits the details of an existing person in the system. To be finished in v 2.0.
 */
public class EditParticipationCommand extends Command {

    public static final String COMMAND_WORD = "editPerson";

    public static final CommandType COMMAND_TYPE = CommandType.PARTICIPATION;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the participation identified "
        + "by the index number used in the displayed participation list.";

    public static final String MESSAGE_EDIT_PARTICIPATION_SUCCESS = "Edited participation: %1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_PARTICIPATION =
        "This participation already exists in the address book.";

    /**
     * Private constructor to prevent instantiation.
     */
    private EditParticipationCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_EDIT_PARTICIPATION_SUCCESS), COMMAND_TYPE);
    }

    /**
     * Creates and returns a {@code Participation} with the details of {@code participationToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Participation createEditedParticipation(
        Participation participationToEdit, EditParticipationDescriptor editParticipationDescriptor) {
        assert participationToEdit != null;

        Competition updatedCompetition =
            editParticipationDescriptor.getCompetition().orElse(participationToEdit.getCompetition());
        Person updatedPerson = editParticipationDescriptor.getPerson().orElse(participationToEdit.getPerson());
        return new Participation(updatedPerson, updatedCompetition);
    }

    /**
     * Creates and returns a {@code Participation} with the details of {@code participationToEdit}
     * updated with the new {@code updatedPerson}.
     */
    public static Participation createEditedParticipation(Participation participationToEdit, Person updatedPerson) {
        assert participationToEdit != null;
        assert updatedPerson != null;
        return new Participation(
            updatedPerson,
            participationToEdit.getCompetition(),
            participationToEdit.getAttempts()
        );
    }

    /**
     * Creates and returns a {@code Participation} with the details of {@code participationToEdit}
     * updated with the new {@code updatedCompetition}.
     */
    public static Participation createEditedParticipation(
        Participation participationToEdit,
        Competition updatedCompetition
    ) {
        assert participationToEdit != null;
        assert updatedCompetition != null;
        return new Participation(
            participationToEdit.getPerson(),
            updatedCompetition,
            participationToEdit.getAttempts()
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditParticipationCommand)) {
            return false;
        }

        // state check
        EditParticipationCommand e = (EditParticipationCommand) other;
        return false;
    }

    /**
     * Stores the details to edit the participation with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditParticipationDescriptor {
        private Competition competition;
        private Person person;

        public EditParticipationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditParticipationDescriptor(EditParticipationDescriptor toCopy) {
            setCompetition(toCopy.competition);
            setPerson(toCopy.person);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(competition, person);
        }

        public void setCompetition(Competition competition) {
            this.competition = competition;
        }

        public Optional<Competition> getCompetition() {
            return Optional.ofNullable(competition);
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Optional<Person> getPerson() {
            return Optional.ofNullable(person);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditParticipationDescriptor)) {
                return false;
            }

            // state check
            EditParticipationDescriptor e = (EditParticipationDescriptor) other;

            return getCompetition().equals(e.getCompetition())
                && getPerson().equals(e.getPerson());
        }
    }
}
