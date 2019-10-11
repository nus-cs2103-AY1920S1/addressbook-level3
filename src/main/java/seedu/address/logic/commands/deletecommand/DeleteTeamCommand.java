package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Team} in Alfred.
 */
public class DeleteTeamCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete team";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team ID provided is invalid";
    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the team identified by the ID used in the displayed team list.\n"
            + "Parameters: team ID\n"
            + "Example: " + COMMAND_WORD + " T-1";

    public DeleteTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToBeDeleted;
        try {
            teamToBeDeleted = model.deleteTeam(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS,
                teamToBeDeleted.toString()));
    }

}
