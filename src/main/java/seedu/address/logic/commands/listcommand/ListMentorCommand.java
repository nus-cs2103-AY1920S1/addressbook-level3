package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;

/**
 * Lists every {@link Mentor} in Alfred.
 */
public class ListMentorCommand extends ListCommand {

    /* Possible Fields? */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // See ListIssueCommand

        return new CommandResult("");
    }

}
