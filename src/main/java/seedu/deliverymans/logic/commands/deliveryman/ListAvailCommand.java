package seedu.deliverymans.logic.commands.deliveryman;

import seedu.address.model.Model;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * (to be added)
 */
public class ListAvailCommand extends Command {
    public static final String COMMAND_WORD = "lista";

    public static final String MESSAGE_LIST_AVAIL_SUCCESS = "Listed all currently available deliverymen";

    public ListAvailCommand(String arguments) {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
