package seedu.deliverymans.logic.commands.universal;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;

/**
 * (to be added)
 */
public class ContextCommand extends Command {
    private static final String MESSAGE_SUCCESS = "Context changed to: %1$s";
    private Context context;

    public ContextCommand(Context context) {
        this.context = context;
    }

    @Override
    public CommandResult execute(Model model) {
        model.setContext(context);
        return new CommandResult(String.format(MESSAGE_SUCCESS, context.toLowerCaseString()));
    }
}
