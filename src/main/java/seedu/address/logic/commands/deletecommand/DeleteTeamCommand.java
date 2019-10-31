package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Team} in Alfred and all the {@code Participant}s in it.
 */
public class DeleteTeamCommand extends DeleteCommand {

    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team ID provided is invalid or"
            + "does not exist.";
    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted Team: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " team"
            + ": Deletes a team by ID shown in the list of teams.\n"
            + "Format: " + COMMAND_WORD + " team ID\n"
            + "Example: " + COMMAND_WORD + " team T-1";

    public DeleteTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToBeDeleted;
        try {
            teamToBeDeleted = model.deleteTeam(this.id);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS,
                teamToBeDeleted.toString()), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTeamCommand // instanceof handles nulls
                && id.equals(((DeleteTeamCommand) other).id));
    }
}
