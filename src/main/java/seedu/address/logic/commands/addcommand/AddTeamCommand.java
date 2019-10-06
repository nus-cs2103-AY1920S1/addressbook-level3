package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;

/**
 * Adds a {@link Team} to Alfred.
 */
public class AddTeamCommand extends AddCommand {

    /* Possible Fields: */
    private Team team;

    public AddTeamCommand(Team team) {
        requireNonNull(team);
        this.team = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // See AddIssueCommand

        return new CommandResult("");
    }

}
