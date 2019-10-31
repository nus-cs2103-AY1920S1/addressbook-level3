package seedu.address.logic.commands.removecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Mentor} in Alfred.
 */
public class RemoveParticipantCommand extends RemoveCommand {
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The Team ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The Participant ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_REMOVE_PARTICIPANT_SUCCESS =
            "Removed Participant %1$s(%2$s) from Team %3$s(%4$s) ";
    public static final String MESSAGE_TEAM_DOES_NOT_HAVE_PARTICIPANT = "Team does not have this Participant.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "participant"
            + ": Removes the specified participant by ID from a Team with Team-ID\n"
            + "Format: " + COMMAND_WORD + " participant [participant ID] [team ID]\n"
            + "For example: " + COMMAND_WORD + " participant P-1 T-1";

    public RemoveParticipantCommand(Id participantId, Id teamId) {
        super(participantId, teamId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Participant participantToBeRemoved;
        Team assignedTeam;
        try {
            participantToBeRemoved = model.getParticipant(this.entityId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        try {
            assignedTeam = model.getTeam(this.teamId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        try {
            model.removeParticipantFromTeam(teamId, participantToBeRemoved);
            model.updateHistory(this);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_TEAM_DOES_NOT_HAVE_PARTICIPANT);
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_PARTICIPANT_SUCCESS,
                participantToBeRemoved.getName(), participantToBeRemoved.getId(),
                assignedTeam.getName(), assignedTeam.getId()), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveMentorCommand // instanceof handles nulls
                && entityId.equals(((RemoveMentorCommand) other).entityId)
                && teamId.equals(((RemoveMentorCommand) other).teamId));
    }
}
