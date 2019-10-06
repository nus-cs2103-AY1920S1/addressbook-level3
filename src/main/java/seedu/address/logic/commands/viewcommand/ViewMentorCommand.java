package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;

/**
 * Shows detailed view of the {@link Mentor} at specified ID.
 */
public class ViewMentorCommand extends ViewCommand {

    /* Possible Fields? */

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // See ViewIssueCommand

        return new CommandResult("");
    }

}
