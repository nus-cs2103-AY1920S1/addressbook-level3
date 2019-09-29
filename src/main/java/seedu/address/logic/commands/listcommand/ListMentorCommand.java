package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListMentorCommand extends ListCommand {

    /* Possible Fields? */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // See ListIssueCommand

        return new CommandResult("");
    }

}
