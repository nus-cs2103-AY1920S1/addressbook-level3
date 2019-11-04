package seedu.deliverymans.logic.commands.universal;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.model.Model;

/**
 * Lists all orders in the system to the user.
 */
public class ListOrderCommand extends Command {
    public static final String COMMAND_WORD = "-list_orders";

    public static final String MESSAGE_SUCCESS = "Listed all orders";

    @Override
    public CommandResult execute(Model model, Logic logic) {
        logic.setContext(Context.GLOBAL);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
