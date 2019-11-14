package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Shows detailed view of the {@link Team} at specified ID.
 */
public class ViewTeamCommand extends ViewCommand {

    public static final String COMMAND_WORD = "view team";
    public static final String MESSAGE_SUCCESS = "Showing team with ID: %s"; // %s -> Id
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX =
            "The team index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " team"
            + ": shows details of the team with specified ID. \n"
            + "Format: view team [team ID] \n"
            + "For example: " + COMMAND_WORD + " team T-1";

    public ViewTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToView;
        try {
            teamToView = model.getTeam(this.id);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        model.viewEntity(teamToView);
        this.displayDetailedEntity(teamToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.id), CommandType.T);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTeamCommand // instanceof handles nulls
                && id.equals(((ViewTeamCommand) other).id));
    }
}
