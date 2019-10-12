package seedu.deliverymans.logic.commands.deliveryman;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * (To be added)
 */
public class StatusCommand extends Command {
    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_SHOW_STATUS_SUCCESS = "Showed status of delivery #";

    public StatusCommand(String arguments) {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
