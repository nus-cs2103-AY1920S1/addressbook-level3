package seedu.deliverymans.logic.commands.deliveryman;

import seedu.address.model.Model;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * (to be added)
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_ASSIGN_ORDER_SUCCESS = "Order has been assigned to deliveryman: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
