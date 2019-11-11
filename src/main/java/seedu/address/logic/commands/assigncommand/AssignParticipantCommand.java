package seedu.address.logic.commands.assigncommand;

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
public class AssignParticipantCommand extends AssignCommand {
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The Team ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX = "The Participant ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_ASSIGN_PARTICIPANT_SUCCESS =
            "Assigned Participant %1$s(%2$s) to Team %3$s(%4$s) ";
    public static final String MESSAGE_PARTICIPANT_ALREADY_ASSIGNED = "Participant already in the Team.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "participant"
            + ": Assigns the specified participant by ID to a Team with Team-ID\n"
            + "Format: " + COMMAND_WORD + " participant [participant ID] [team ID]\n"
            + "For example: " + COMMAND_WORD + " participant P-1 T-1";

    public AssignParticipantCommand(Id participantId, Id teamId) {
        super(participantId, teamId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Participant participantToBeAdded;
        Team assignedTeam;
        try {
            participantToBeAdded = model.getParticipant(this.entityId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        try {
            assignedTeam = model.getTeam(this.teamId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        try {
            model.addParticipantToTeam(teamId, participantToBeAdded);
        } catch (AlfredException e) {
            throw new CommandException(e.getMessage());
        }

        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(String.format(MESSAGE_ASSIGN_PARTICIPANT_SUCCESS,
                participantToBeAdded.getName(), participantToBeAdded.getId(),
                assignedTeam.getName(), assignedTeam.getId()), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignMentorCommand // instanceof handles nulls
                && entityId.equals(((AssignMentorCommand) other).entityId)
                && teamId.equals(((AssignMentorCommand) other).teamId));
    }
}
