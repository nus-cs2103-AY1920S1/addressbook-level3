package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Shows detailed view of the {@link Team} at specified ID.
 */
public class ViewTeamCommand extends ViewCommand {

    public static final String COMMAND_WORD = "view team";
    public static final String MESSAGE_SUCCESS = "Showed specified team";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX =
            "The team index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows details of the team with specified ID. "
            + "Parameters: team ID\n"
            + "Example: " + COMMAND_WORD + " T-1";

    public ViewTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToView;
        try {
            teamToView = model.getTeam(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        viewEntity(teamToView);

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
