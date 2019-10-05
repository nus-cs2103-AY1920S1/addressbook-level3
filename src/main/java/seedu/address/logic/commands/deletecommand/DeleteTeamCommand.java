package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Deletes a {@link Team} in Alfred.
 */
public class DeleteTeamCommand extends DeleteCommand {

    /* Possible Fields? */

    public DeleteTeamCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // See DeleteIssueCommand

        return new CommandResult("");
    }

}
