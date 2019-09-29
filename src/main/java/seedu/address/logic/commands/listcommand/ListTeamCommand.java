package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListTeamCommand extends ListCommand {

    /* Possible Fields? */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // see ListIssueCommand

        return new CommandResult("");
    }

}
