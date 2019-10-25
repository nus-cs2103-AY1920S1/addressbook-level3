package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;

/**
 * Deletes a {@link Participant} in Alfred.
 */
public class DeleteParticipantCommand extends DeleteCommand {

    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The participant ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_DELETE_PARTICIPANT_SUCCESS = "Deleted Participant: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " participant"
            + ": Deletes a participant by ID shown in the list of participants.\n"
            + "Format: " + COMMAND_WORD + " participant ID\n"
            + "Example: " + COMMAND_WORD + " participant P-1";

    private Name teamName;

    public DeleteParticipantCommand(Id id) {
        super(id);
    }

    public DeleteParticipantCommand(Id id, Name teamName) {
        super(id);
        requireNonNull(teamName);
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.teamName != null) {
            // find Team (or throw Exception)
            // delete from team
            // return CommandResult
        }

        Participant participantToBeDeleted;
        try {
            participantToBeDeleted = model.deleteParticipant(this.id);
            model.updateHistory(this);
        } catch (AlfredException e) {
            // Model checking if index is invalid?
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PARTICIPANT_SUCCESS,
                                               participantToBeDeleted.toString()), CommandType.P);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteParticipantCommand // instanceof handles nulls
                && id.equals(((DeleteParticipantCommand) other).id));
    }
}
