package seedu.address.logic.commands.removecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Mentor} in Alfred.
 */
public class RemoveMentorCommand extends RemoveCommand {
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The Team ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_REMOVE_MENTOR_SUCCESS = "Removed Mentor %1$s(%2$s) from Team %3$s(%4$s) ";
    public static final String MESSAGE_TEAM_DOES_NOT_HAVE_MENTOR = "Team does not have an existing mentor.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " mentor"
            + ": Removes the specified mentor by ID from a Team with Team-ID\n"
            + "Format: " + COMMAND_WORD + " mentor [mentor ID] [team ID]\n"
            + "For example: " + COMMAND_WORD + " mentor M-1 T-1";


    public RemoveMentorCommand(Id mentorId, Id teamId) {
        super(mentorId, teamId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Mentor mentorToBeRemoved;
        Team assignedTeam;
        try {
            mentorToBeRemoved = model.getMentor(this.entityId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }

        try {
            assignedTeam = model.getTeam(this.teamId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        try {
            model.removeMentorFromTeam(teamId, mentorToBeRemoved);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_TEAM_DOES_NOT_HAVE_MENTOR);
        }

        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(String.format(MESSAGE_REMOVE_MENTOR_SUCCESS,
                mentorToBeRemoved.getName(), mentorToBeRemoved.getId(),
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
