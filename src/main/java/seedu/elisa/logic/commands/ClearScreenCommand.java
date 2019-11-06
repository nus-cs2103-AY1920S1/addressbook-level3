package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Clears the chat box.
 */
public class ClearScreenCommand extends Command {

    public static final String COMMAND_WORD = "clearscreen";
    public static final String MESSAGE_SUCCESS = "Commands has been cleared! (About time, isn't it?)";

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new ClearScreenCommandResult(MESSAGE_SUCCESS);
    }
}
