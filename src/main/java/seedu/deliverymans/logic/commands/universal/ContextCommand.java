package seedu.deliverymans.logic.commands.universal;

import seedu.address.model.Model;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.parser.universal.Context;

public class ContextCommand extends Command {
    private Context context;
    private static final String MESSAGE_SUCCESS = "Context changed to: %1$s";

    public ContextCommand(Context context) {
        this.context = context;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, context.toLowerCaseString()));
    }
}
