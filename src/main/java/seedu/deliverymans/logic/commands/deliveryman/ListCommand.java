package seedu.deliverymans.logic.commands.deliveryman;

import seedu.address.model.Model;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * (to be added)
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_LIST_SUCCESS = "Listed all deliverymen";
    public ListCommand(String arguments) {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
