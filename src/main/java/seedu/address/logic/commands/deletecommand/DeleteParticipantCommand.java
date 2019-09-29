package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DeleteParticipantCommand extends DeleteCommand {

    /* Possible Fields? */

    // Later, update this constant to say participant name is invalid
    private static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The participant ID provided is invalid";
    private static final String MESSAGE_DELETE_PARTICIPANT_SUCCESS = "Deleted Person: %1$s";

    public DeleteParticipantCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Participant participantToBeDeleted;
        try {
            participantToBeDeleted = model.deleteParticipant(this.id);
        } catch (AlfredException e) {
            // Model checking if index is invalid?
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PARTICIPANT_SUCCESS,
                                               participantToBeDeleted.toString()));
    }

}
