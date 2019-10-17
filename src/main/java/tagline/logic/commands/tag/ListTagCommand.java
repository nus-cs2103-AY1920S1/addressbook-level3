package tagline.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import tagline.logic.commands.CommandResult;
import tagline.model.Model;

/**
 * Lists all tags in the tagline to the user.
 */
public class ListTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tags";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
