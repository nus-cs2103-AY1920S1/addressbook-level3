package seedu.address.logic.commands.assigncommand;

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
public class AssignMentorCommand extends AssignCommand {
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The Team ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_ASSIGN_MENTOR_SUCCESS = "Assigned Mentor %1$s(%2$s) to Team %3$s(%4$s) ";
    public static final String MESSAGE_TEAM_HAS_MENTOR = "Team already has a mentor.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " mentor"
            + ": Assigns the specified mentor by ID to a Team with Team-ID\n"
            + "Format: " + COMMAND_WORD + " mentor [mentor ID] [team ID]\n"
            + "For example: " + COMMAND_WORD + " mentor M-1 T-1";


    public AssignMentorCommand(Id mentorId, Id teamId) {
        super(mentorId, teamId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Mentor mentorToBeAdded;
        Team assignedTeam;
        try {
            mentorToBeAdded = model.getMentor(this.entityId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }

        try {
            assignedTeam = model.getTeam(this.teamId);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        try {
            model.addMentorToTeam(teamId, mentorToBeAdded);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_TEAM_HAS_MENTOR);
        }

        return new CommandResult(String.format(MESSAGE_ASSIGN_MENTOR_SUCCESS,
                mentorToBeAdded.getName(), mentorToBeAdded.getId(),
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
