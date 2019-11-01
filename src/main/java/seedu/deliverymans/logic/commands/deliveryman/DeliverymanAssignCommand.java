package seedu.deliverymans.logic.commands.deliveryman;

import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Assigns an order to a deliveryman
 */
public class DeliverymanAssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns an order to a deliveryman.\n";

    public static final String MESSAGE_ASSIGN_ORDER_SUCCESS = "Order has been assigned to deliveryman: %1$s";

    @Override
    public CommandResult execute(Model model, Logic logic) throws CommandException {
        return null;
    }
}
